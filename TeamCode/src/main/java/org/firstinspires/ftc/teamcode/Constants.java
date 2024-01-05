package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

// Config makes it so that all public, stating NON-FINAL class members are available as configuration
// values on the dashboard. However, according to JLS 17.7, they should be considered volatile to
// allow for live updating. (aka anything non-final should be volatile instead.)
@Config
public class Constants {
    // GENERAL CONFIG
    public static volatile double INPUT_THRESHOLD = 0.1;
    public static volatile double MOTOR_MAX_SPEED = 0.9;

    // ODOMETRY CONFIG
    public static volatile double ODOMETRY_TICKS_PER_INCH = 100d;
    public static volatile double ODOMETRY_LEFT_Y_POSITION = 0.0d * ODOMETRY_TICKS_PER_INCH;
    public static volatile double ODOMETRY_RIGHT_Y_POSITION = 0.5d * ODOMETRY_TICKS_PER_INCH;
    public static volatile double ODOMETRY_CENTER_SENSOR_X_POSITION = 0.0d * ODOMETRY_TICKS_PER_INCH;

    // DRIVETRAIN CONFIG
    public static volatile boolean DRIVETRAIN_MESSENGER_ENABLED = true;
    public static volatile double ROBOT_DISPLAY_X_SIZE = 40;
    public static volatile double ROBOT_DISPLAY_Y_SIZE = 40;


    // ----- HARDWARE MAP NAMES ------
    // -------- SENSOR NAMES ---------
    public static final String IMU_NAME = "imu";
    public static final String WEBCAM_NAME = "Webcam 1";
    // --------- MOTOR NAMES ---------
    public static final String LEFT_FRONT_NAME = "leftFront";
    public static final String RIGHT_FRONT_NAME = "rightFront";
    public static final String LEFT_BACK_NAME = "leftBack";
    public static final String RIGHT_BACK_NAME = "rightBack";
    public static final String LEFT_ODOMETRY_NAME = "par0";
    public static final String RIGHT_ODOMETRY_NAME = "par1";
    public static final String CENTER_ODOMETRY_NAME = "perp";
    // --------- SERVO NAMES ---------
}
