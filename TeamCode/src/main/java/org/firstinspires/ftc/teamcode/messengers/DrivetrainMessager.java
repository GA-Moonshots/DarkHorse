package org.firstinspires.ftc.teamcode.messengers;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.firstinspires.ftc.teamcode.Constants;
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
        double topLeftX = x + (Constants.ROBOT_DISPLAY_X_SIZE * Math.cos(Math.toRadians(theta)) + Constants.ROBOT_DISPLAY_Y_SIZE * Math.sin(Math.toRadians(theta)));
        double topRightX = x + (Constants.ROBOT_DISPLAY_X_SIZE * Math.cos(Math.toRadians(theta)) - Constants.ROBOT_DISPLAY_Y_SIZE * Math.sin(Math.toRadians(theta)));
        double botLeftX = x + (-Constants.ROBOT_DISPLAY_X_SIZE * Math.cos(Math.toRadians(theta)) + Constants.ROBOT_DISPLAY_Y_SIZE * Math.sin(Math.toRadians(theta)));
        double botRightX = x + (Constants.ROBOT_DISPLAY_X_SIZE * Math.cos(Math.toRadians(theta)) + Constants.ROBOT_DISPLAY_Y_SIZE * Math.sin(Math.toRadians(theta)));

        double topLeftY = y + (Constants.ROBOT_DISPLAY_X_SIZE * Math.sin(Math.toRadians(theta)) + Constants.ROBOT_DISPLAY_Y_SIZE * Math.cos(Math.toRadians(theta)));
        double topRightY = y + (Constants.ROBOT_DISPLAY_X_SIZE * Math.sin(Math.toRadians(theta)) - Constants.ROBOT_DISPLAY_Y_SIZE * Math.cos(Math.toRadians(theta)));
        double botLeftY = y + (-Constants.ROBOT_DISPLAY_X_SIZE * Math.sin(Math.toRadians(theta)) + Constants.ROBOT_DISPLAY_Y_SIZE * Math.cos(Math.toRadians(theta)));
        double botRightY = y + (Constants.ROBOT_DISPLAY_X_SIZE * Math.sin(Math.toRadians(theta)) + Constants.ROBOT_DISPLAY_Y_SIZE * Math.cos(Math.toRadians(theta)));

        double[] robotXPoints = {topLeftX, topRightX, botRightX, botLeftX};
        double[] robotYPoints = {topLeftY, topRightY, botRightY, botLeftY};

        packet.fieldOverlay().setFill("blue").strokePolygon(robotXPoints, robotYPoints);
    }
}
