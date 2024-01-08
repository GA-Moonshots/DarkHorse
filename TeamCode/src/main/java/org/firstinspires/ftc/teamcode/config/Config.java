package org.firstinspires.ftc.teamcode.config;

// Config makes it so that all public, stating NON-FINAL class members are available as configuration
// values on the dashboard. However, according to JLS 17.7, they should be considered volatile to
// allow for live updating. (aka anything non-final should be volatile instead.)
@com.acmerobotics.dashboard.config.Config
public class Config {
    public static volatile double INPUT_THRESHOLD = 0.1;
    public static volatile double MOTOR_MAX_SPEED = 0.9;
    public static volatile double DISTANCE_THRESHOLD = 1.0;
    public static volatile double ANGLE_THRESHOLD = 3.0;
}
