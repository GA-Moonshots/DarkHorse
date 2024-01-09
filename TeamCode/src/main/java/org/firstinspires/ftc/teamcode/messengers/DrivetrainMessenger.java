package org.firstinspires.ftc.teamcode.messengers;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.config.DriveConfig;
import org.firstinspires.ftc.teamcode.internal.CoreMessenger;
import org.firstinspires.ftc.teamcode.internal.CoreOpMode;

import java.util.List;

// This class is responsible for sending relevant information to the telemetry output and
// to FtcDashboard (aka sending position, rotation, other odometry to the field overlay on the dashboard
public class DrivetrainMessenger extends CoreMessenger {
    List<Pose2d> history;
    public DrivetrainMessenger(CoreOpMode opMode) {
        super(opMode);
    }

    public void addRobotToFieldOverlay(double x, double y, double theta) {
        TelemetryPacket packet = new TelemetryPacket();

        drawRobot(packet.fieldOverlay(), new Pose2d(x, y, theta));
        drawPoseHistory(packet.fieldOverlay());

        FtcDashboard.getInstance().sendTelemetryPacket(packet);
    }

    public void drawPoseHistory(Canvas canvas) {
        double[] xPoints = new double[history.size()];
        double[] yPoints = new double[history.size()];
        for (int i = 0; i < history.size(); i++) {
            Pose2d pose = history.get(i);
            xPoints[i] = pose.getX();
            yPoints[i] = pose.getY();
        }
        canvas.strokePolyline(xPoints, yPoints);
    }

    public void drawRobot(Canvas canvas, Pose2d pose) {
        canvas.strokeCircle(pose.getX(), pose.getY(), DriveConfig.DISPLAY_SIZE);
        Vector2d v = pose.headingVec().times(DriveConfig.DISPLAY_SIZE);
        double x1 = pose.getX() + v.getX() / 2, y1 = pose.getY() + v.getY() / 2;
        double x2 = pose.getX() + v.getX(), y2 = pose.getY() + v.getY();
        canvas.strokeLine(x1, y1, x2, y2);
    }
}