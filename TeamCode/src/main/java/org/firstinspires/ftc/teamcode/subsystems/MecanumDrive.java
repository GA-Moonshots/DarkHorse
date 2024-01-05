package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.internal.CoreOpMode;

import java.util.Locale;

public class MecanumDrive extends Drivetrain {

    // MOTORS
    private final DcMotor leftFront;
    private final DcMotor rightFront;
    private final DcMotor leftBack;
    private final DcMotor rightBack;

    private boolean isTargetSet = false;
    private double gyroTarget = 0.0d;

    public MecanumDrive(CoreOpMode opMode) {
        super(opMode);

        leftFront = opMode.hardwareMap.get(DcMotor.class, Constants.LEFT_FRONT_NAME);
        rightFront = opMode.hardwareMap.get(DcMotor.class, Constants.RIGHT_FRONT_NAME);
        leftBack = opMode.hardwareMap.get(DcMotor.class, Constants.LEFT_BACK_NAME);
        rightBack = opMode.hardwareMap.get(DcMotor.class, Constants.RIGHT_BACK_NAME);
        // According to RoadRunner docs: "Relying on the internal PID for velocity control can prove
        // to be quite frustrating. It is quite the fickle controller sometimes." Therefore, I am
        // running this without encoders until I get back and test it both ways.
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
            // Learn more:
            // https://www.geogebra.org/m/fmegkksm
            double diff = fieldCentricTarget - imu.getZAngle();
            double temp = forward;
            forward = forward * Math.cos(Math.toRadians(diff)) - strafe * Math.sin(Math.toRadians(diff));
            strafe = temp * Math.sin(Math.toRadians(diff)) + strafe * Math.cos(Math.toRadians(diff));

            messenger.addData("Mode", "Field Centric");
        } else {
            messenger.addData("Mode", "Robot Centric");
        }

        isGyroLocked = turn <= Constants.INPUT_THRESHOLD;
        if(isGyroLocked && !isTargetSet) {
            gyroTarget = imu.getYAngle();
            isTargetSet = true;
        } else if(!isGyroLocked) {
            isTargetSet = false;
        }

        // I'm tired of figuring out the input problems so the inputs are still in flight stick mode
        // Meaning forward is reversed
        // The boost values should match the turn
        // Since the drive is a diamond wheel pattern instead of an X, it reverses the strafe.
        double leftFrontPower = -forward +strafe + turn;
        double rightFrontPower = forward + strafe + turn;
        double leftBackPower = -forward - strafe + turn;
        double rightBackPower = forward - strafe + turn;

        double powerScale = Constants.MOTOR_MAX_SPEED * Math.max(1,
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


        messenger.addData("Motors", String.format(Locale.US, "(%.2f, %.2f, %.2f, %.2f)",
                leftFrontPower, leftBackPower, rightBackPower, rightFrontPower));

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
    protected void drive(double m1, double m2, double m3, double m4) {
        leftFront.setPower(Range.clip(m1, -Constants.MOTOR_MAX_SPEED, Constants.MOTOR_MAX_SPEED));
        rightFront.setPower(Range.clip(m2, -Constants.MOTOR_MAX_SPEED, Constants.MOTOR_MAX_SPEED));
        leftBack.setPower(Range.clip(m3, -Constants.MOTOR_MAX_SPEED, Constants.MOTOR_MAX_SPEED));
        rightBack.setPower(Range.clip(m4, -Constants.MOTOR_MAX_SPEED, Constants.MOTOR_MAX_SPEED));
    }


    @Override
    public void stop() {

    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        // Continue with the last update state
    }
}
