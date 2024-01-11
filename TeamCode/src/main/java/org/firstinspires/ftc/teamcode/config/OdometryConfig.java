package org.firstinspires.ftc.teamcode.config;

@com.acmerobotics.dashboard.config.Config
public class OdometryConfig extends Config {
    public enum Mode {
        LINEAR,
        CONSTANT_VELOCITY_ARC,
        CONSTANT_ACCELERATION_POLYNOMIAL
    }

    public static volatile double TICKS_PER_INCH = 2000  / (Math.PI * 4.8 * 2.54);
    public static volatile double LEFT_X_POSITION = -3.8d * TICKS_PER_INCH;
    public static volatile double RIGHT_X_POSITION = 3.8d * TICKS_PER_INCH;
    public static volatile double CENTER_Y_POSITION = 0.0d * TICKS_PER_INCH;

    public static volatile Mode RUN_MODE = Mode.CONSTANT_VELOCITY_ARC;

    public static final double X_DISTANCE = Math.abs(LEFT_X_POSITION - RIGHT_X_POSITION);
}
