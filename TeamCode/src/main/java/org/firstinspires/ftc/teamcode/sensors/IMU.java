package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.config.Constants;
import org.firstinspires.ftc.teamcode.internal.CoreLocalizer;
import org.firstinspires.ftc.teamcode.internal.CoreOpMode;

public class IMU extends CoreLocalizer {
    public static final AngleUnit ANGLE_UNIT = AngleUnit.DEGREES;

    public com.qualcomm.robotcore.hardware.IMU imu;

    private Orientation storedOrientation;
    private Velocity storedVelocity;
    private Position storedPosition;

    public IMU(CoreOpMode opMode) {
        super(opMode);

        com.qualcomm.robotcore.hardware.IMU.Parameters parameters =
                new com.qualcomm.robotcore.hardware.IMU.Parameters(
                    new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
                ));

        imu = opMode.hardwareMap.get(com.qualcomm.robotcore.hardware.IMU.class, Constants.IMU_NAME);
        imu.initialize(parameters);
    }

    /**
     *
     * @return the X angle of the internal IMU in the control panel.
     */
    public double getXAngle() {
        return storedOrientation.firstAngle;
    }

    /**
     *
     * @return the Y angle of the internal IMU in the control panel.
     */
    public double getYAngle() {
        return storedOrientation.secondAngle;
    }

    /**
     *
     * @return the Z angle of the internal IMU in the control panel.
     */
    public double getZAngle() {
        return storedOrientation.thirdAngle;
    }

    /**
     *
     * @return a double array, ordered XYZ, of the angle.
     */
    public double[] getAngle() {
        double[] out = new double[3];

        out[0] = getXAngle();
        out[1] = getYAngle();
        out[2] = getZAngle();

        return out;
    }

    /**
     *
     * @return The X axis velocity of the control panel.
     */
    public double getXVelocity() {
        try {
            return storedVelocity.xVeloc;
        } catch(Exception e) {
            return 0.0d;
        }
    }

    /**
     *
     * @return The Y axis velocity of the control panel.
     */
    public double getYVelocity() {
        try {
            return storedVelocity.yVeloc;
        } catch(Exception e) {
            return 0.0d;
        }
    }

    /**
     *
     * @return The Z axis velocity of the control panel.
     */
    public double getZVelocity() {
        try {
            return storedVelocity.zVeloc;
        } catch(Exception e) {
            return 0.0d;
        }
    }

    /**
     *
     * @return An ordered XYZ array of the control panel's current velocity.
     */
    public double[] getVelocity() {
        double[] out = new double[3];

        out[0] = getXVelocity();
        out[1] = getYVelocity();
        out[2] = getZVelocity();

        return out;
    }

    public double getXPosition() {
        try {
            return storedPosition.x;
        } catch(Exception e) {
            return 0.0d;
        }
    }

    public double getYPosition() {
        try {
            return storedPosition.y;
        } catch(Exception e) {
            return 0.0d;
        }
    }

    public double getZPosition() {
        try {
            return storedPosition.z;
        } catch(Exception e) {
            return 0.0d;
        }
    }

    public double[] getPosition() {
        double[] out = new double[3];

        out[0] = getXPosition();
        out[1] = getYPosition();
        out[2] = getZPosition();

        return out;
    }

    @Override
    public void update() {
        storedOrientation = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, ANGLE_UNIT);
        try {
            storedVelocity = ((BNO055IMU) imu).getVelocity();
            storedPosition = ((BNO055IMU) imu).getPosition();
        } catch (Exception e) {
            storedVelocity = null;
            storedPosition = null;
        }
    }
}
