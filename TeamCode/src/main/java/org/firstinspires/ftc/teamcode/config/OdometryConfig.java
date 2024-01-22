package org.firstinspires.ftc.teamcode.config;

@com.acmerobotics.dashboard.config.Config
public class OdometryConfig extends Config {

    public static volatile double INCHES_PER_TICK = 2 * Math.PI * 1.88976 / 2000;
    public static volatile double TICKS_PER_INCH = 1 / INCHES_PER_TICK;
    public static volatile int LEFT_X_POSITION = (int) Math.round(-3.8d / INCHES_PER_TICK);
    // The distance the right parallel encoder is from the center in ticks (X Axis)
    public static volatile int RIGHT_X_POSITION = (int) Math.round(3.8d / INCHES_PER_TICK);
    // The distance the perpendicular encoder is from the center of rotation in ticks (Y Axis)
    public static volatile int CENTER_Y_POSITION = (int) Math.round(6.5d / INCHES_PER_TICK);
}
