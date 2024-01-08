package org.firstinspires.ftc.teamcode.messengers;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;

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
            xPoints[i] = pose.position.x;
            yPoints[i] = pose.position.y;
        }
        canvas.strokePolyline(xPoints, yPoints);
    }

    public void drawRobot(Canvas canvas, Pose2d pose) {
        canvas.strokeCircle(pose.position.x, pose.position.y, DriveConfig.DISPLAY_SIZE);
        Vector2d v = pose.heading.vec().times(DriveConfig.DISPLAY_SIZE);
        double x1 = pose.position.x + v.x / 2, y1 = pose.position.y + v.y / 2;
        double x2 = pose.position.x + v.x, y2 = pose.position.y + v.y;
        canvas.strokeLine(x1, y1, x2, y2);
    }
}