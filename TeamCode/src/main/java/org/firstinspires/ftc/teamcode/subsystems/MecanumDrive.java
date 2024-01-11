package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

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