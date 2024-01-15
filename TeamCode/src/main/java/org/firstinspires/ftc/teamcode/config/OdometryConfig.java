package org.firstinspires.ftc.teamcode.config;

@com.acmerobotics.dashboard.config.Config
public class OdometryConfig extends Config {

    public static volatile double TICKS_PER_INCH = 2000 / (Math.PI * (4.8 / 2.54));
    public static volatile double LEFT_X_POSITION = -3.8d * TICKS_PER_INCH;
    public static volatile double RIGHT_X_POSITION = 3.8d * TICKS_PER_INCH;
    public static volatile double CENTER_Y_POSITION = 0.0d * TICKS_PER_INCH;
}
