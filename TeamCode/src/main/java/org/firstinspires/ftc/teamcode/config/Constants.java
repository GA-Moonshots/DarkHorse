package org.firstinspires.ftc.teamcode.config;

public class Constants {
    // ----- HARDWARE MAP NAMES ------
    // -------- SENSOR NAMES ---------
    public static final String IMU_NAME = "imu";
    public static final String WEBCAM_NAME = "Webcam 1";
    // --------- MOTOR NAMES ---------
    public static final String LEFT_FRONT_NAME = "leftFront";
    public static final String RIGHT_FRONT_NAME = "rightFront";
    public static final String LEFT_BACK_NAME = "leftBack";
    public static final String RIGHT_BACK_NAME = "rightBack";
    // These are not actually separate names, they dictate the port that the encoder is plugged into,
    // aka which motor encoder they replace.
    public static final String LEFT_ODOMETRY_NAME = LEFT_FRONT_NAME;
    public static final String RIGHT_ODOMETRY_NAME = RIGHT_FRONT_NAME;
    public static final String CENTER_ODOMETRY_NAME = LEFT_BACK_NAME;
    // --------- SERVO NAMES ---------
}
