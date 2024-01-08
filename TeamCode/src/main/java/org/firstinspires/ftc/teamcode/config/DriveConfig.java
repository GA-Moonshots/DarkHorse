package org.firstinspires.ftc.teamcode.config;


@com.acmerobotics.dashboard.config.Config
public class DriveConfig extends Config {
    public static volatile boolean MESSENGER_ENABLED = true;
    public static volatile double DISPLAY_SIZE = 40;
    public static volatile double TICKS_PER_REV = 1;
    public static volatile double MAX_RPM = 1;
    /*
     * These are physical constants that can be determined from your robot (including the track
     * width; it will be tune empirically later although a rough estimate is important). Users are
     * free to chose whichever linear distance unit they would like so long as it is consistently
     * used. The default values were selected with inches in mind. Road runner uses radians for
     * angular distances although most angular parameters are wrapped in Math.toRadians() for
     * convenience. Make sure to exclude any gear ratio included in MOTOR_CONFIG from GEAR_RATIO.
     */
    public static volatile double WHEEL_RADIUS = 2; // in
    public static volatile double GEAR_RATIO = 1; // output (wheel) speed / input (motor) speed

    /*
     * These are the feedforward parameters used to model the drive motor behavior. If you are using
     * the built-in velocity PID, *these values are fine as is*. However, if you do not have drive
     * motor encoders or have elected not to use them for velocity control, these values should be
     * empirically tuned.
     */
    public static volatile double kV = 1.0 / (MAX_RPM * GEAR_RATIO * 2 * Math.PI * WHEEL_RADIUS / 60.0);
    public static volatile double kA = 0;
    public static volatile double kStatic = 0;
}
