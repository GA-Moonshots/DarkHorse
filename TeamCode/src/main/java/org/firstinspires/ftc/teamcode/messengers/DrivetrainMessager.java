package org.firstinspires.ftc.teamcode.messengers;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.firstinspires.ftc.teamcode.config.DriveConfig;
import org.firstinspires.ftc.teamcode.internal.CoreMessenger;
import org.firstinspires.ftc.teamcode.internal.CoreOpMode;

// This class is responsible for sending relevant information to the telemetry output and
// to FtcDashboard (aka sending position, rotation, other odometry to the field overlay on the dashboard
public class DrivetrainMessager extends CoreMessenger {
    public DrivetrainMessager(CoreOpMode opMode) {
        super(opMode);
    }

    public void addRobotToFieldOverlay(double x, double y, double theta) {
        TelemetryPacket packet = new TelemetryPacket();
        double topLeftX = x + (DriveConfig.DISPLAY_X_SIZE * Math.cos(Math.toRadians(theta)) + DriveConfig.DISPLAY_Y_SIZE * Math.sin(Math.toRadians(theta)));
        double topRightX = x + (DriveConfig.DISPLAY_X_SIZE * Math.cos(Math.toRadians(theta)) - DriveConfig.DISPLAY_Y_SIZE * Math.sin(Math.toRadians(theta)));
        double botLeftX = x + (-DriveConfig.DISPLAY_X_SIZE * Math.cos(Math.toRadians(theta)) + DriveConfig.DISPLAY_Y_SIZE * Math.sin(Math.toRadians(theta)));
        double botRightX = x + (DriveConfig.DISPLAY_X_SIZE * Math.cos(Math.toRadians(theta)) + DriveConfig.DISPLAY_Y_SIZE * Math.sin(Math.toRadians(theta)));

        double topLeftY = y + (DriveConfig.DISPLAY_X_SIZE * Math.sin(Math.toRadians(theta)) + DriveConfig.DISPLAY_Y_SIZE * Math.cos(Math.toRadians(theta)));
        double topRightY = y + (DriveConfig.DISPLAY_X_SIZE * Math.sin(Math.toRadians(theta)) - DriveConfig.DISPLAY_Y_SIZE * Math.cos(Math.toRadians(theta)));
        double botLeftY = y + (-DriveConfig.DISPLAY_X_SIZE * Math.sin(Math.toRadians(theta)) + DriveConfig.DISPLAY_Y_SIZE * Math.cos(Math.toRadians(theta)));
        double botRightY = y + (DriveConfig.DISPLAY_X_SIZE * Math.sin(Math.toRadians(theta)) + DriveConfig.DISPLAY_Y_SIZE * Math.cos(Math.toRadians(theta)));

        double[] robotXPoints = {topLeftX, topRightX, botRightX, botLeftX};
        double[] robotYPoints = {topLeftY, topRightY, botRightY, botLeftY};

        packet.fieldOverlay().setFill("blue").strokePolygon(robotXPoints, robotYPoints);
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
    }
}
