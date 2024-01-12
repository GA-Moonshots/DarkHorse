package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.*;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.DualNum;
import com.acmerobotics.roadrunner.HolonomicController;
import com.acmerobotics.roadrunner.MecanumKinematics;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.MotorFeedforward;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.TimeTrajectory;
import com.acmerobotics.roadrunner.TimeTurn;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TurnConstraints;
import com.acmerobotics.roadrunner.Twist2dDual;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.DownsampledWriter;
import com.acmerobotics.roadrunner.ftc.Encoder;
import com.acmerobotics.roadrunner.ftc.FlightRecorder;
import com.acmerobotics.roadrunner.ftc.LynxFirmware;
import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.PositionVelocityPair;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.Constants;
import org.firstinspires.ftc.teamcode.config.DriveConfig;
import org.firstinspires.ftc.teamcode.core.CoreOpMode;
import org.firstinspires.ftc.teamcode.core.math.Vector;

import java.util.Locale;

public class MecanumDrive extends Drivetrain {

    // MOTORS
    private final DcMotor leftFront;
    private final DcMotor rightFront;
    private final DcMotor leftBack;
    private final DcMotor rightBack;

    public MecanumDrive(CoreOpMode opMode) {
        this(opMode, new Pose2d(0, 0, 0));
    }

    public MecanumDrive(CoreOpMode opMode, Pose2d pose) {
        super(opMode);

        leftFront = opMode.hardwareMap.get(DcMotor.class, Constants.LEFT_FRONT_NAME);
        rightFront = opMode.hardwareMap.get(DcMotor.class, Constants.RIGHT_FRONT_NAME);
        leftBack = opMode.hardwareMap.get(DcMotor.class, Constants.LEFT_BACK_NAME);
        rightBack = opMode.hardwareMap.get(DcMotor.class, Constants.RIGHT_BACK_NAME);
        // According to RoadRunner docs: "Relying on the internal PID for velocity control can prove
        // to be quite frustrating. It is quite the fickle controller sometimes."
        // Additionally, in RUN_WITHOUT_ENCODER mode, we have an additional 4 possible motor encoder
        // slots, as otherwise 7 out of 8 motor encoders would be taken by the drivetrain. Not ideal.
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    @Override
    public void drive(double forward, double strafe, double turn) {
        // Field Centric adjustment
        if (isFieldCentric) {
            Vector2d movement = Vector.rotateVector(new Pose2d(strafe, forward, fieldCentricTarget - localizer.getHeading()));
            // Learn more:
            // https://www.geogebra.org/m/fmegkksm
            forward = movement.y;
            strafe = movement.x;

            telemetry.addData("Mode", "Field Centric");
        } else {
            telemetry.addData("Mode", "Robot Centric");
        }

        // I'm tired of figuring out the input problems so the inputs are still in flight stick mode
        // Meaning forward is reversed
        // The boost values should match the turn
        // Since the drive is a diamond wheel pattern instead of an X, it reverses the strafe.
        double leftFrontPower = -forward +strafe + turn;
        double rightFrontPower = forward + strafe + turn;
        double leftBackPower = -forward - strafe + turn;
        double rightBackPower = forward - strafe + turn;

        double powerScale = DriveConfig.MOTOR_MAX_SPEED * Math.max(1,
                Math.max(
                        Math.max(
                                Math.abs(leftFrontPower),
                                Math.abs(leftBackPower)
                        ),
                        Math.max(
                                Math.abs(rightFrontPower),
                                Math.abs(rightBackPower)
                        )
                )
        );

        leftFrontPower /= powerScale;
        leftBackPower /= powerScale;
        rightBackPower /= powerScale;
        rightFrontPower /= powerScale;


        telemetry.addData("Motors","(%.2f, %.2f, %.2f, %.2f)",
                leftFrontPower, leftBackPower, rightBackPower, rightFrontPower);

        drive(
                leftFrontPower,
                rightFrontPower,
                leftBackPower,
                rightBackPower
        );
    }

    /**
     * Clips and executes given motor speeds
     */
    public void drive(double m1, double m2, double m3, double m4) {
        leftFront.setPower(Range.clip(m1, -DriveConfig.MOTOR_MAX_SPEED, DriveConfig.MOTOR_MAX_SPEED));
        rightFront.setPower(Range.clip(m2, -DriveConfig.MOTOR_MAX_SPEED, DriveConfig.MOTOR_MAX_SPEED));
        leftBack.setPower(Range.clip(m3, -DriveConfig.MOTOR_MAX_SPEED, DriveConfig.MOTOR_MAX_SPEED));
        rightBack.setPower(Range.clip(m4, -DriveConfig.MOTOR_MAX_SPEED, DriveConfig.MOTOR_MAX_SPEED));
    }


}