package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Trajectory;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.DriveConfig;
import org.firstinspires.ftc.teamcode.core.CoreLocalizer;
import org.firstinspires.ftc.teamcode.core.CoreOpMode;
import org.firstinspires.ftc.teamcode.core.CoreSubsystem;
import org.firstinspires.ftc.teamcode.sensors.localizers.ThreeWheelLocalizer;

public abstract class Drivetrain extends CoreSubsystem {
    protected boolean isFieldCentric = true;
    protected final Telemetry telemetry;
    protected double fieldCentricTarget;

    // Sensors
    protected CoreLocalizer localizer;

    public Drivetrain(CoreOpMode opMode) {
        super(opMode);

        this.telemetry = opMode.telemetry;
        try {
            localizer = opMode.getSensor(ThreeWheelLocalizer.class);
        } catch (RuntimeException e) {
            localizer = new ThreeWheelLocalizer(opMode);
        }
    }

    // INTERNAL STATE COMMANDS
    public void toggleFieldCentric() {
        this.isFieldCentric = !isFieldCentric;
    }

    public void makeFieldCentric() {
        this.isFieldCentric = true;
    }

    public void makeRobotCentric() {
        this.isFieldCentric = false;
    }

    public void setFieldCentricTarget() {
        fieldCentricTarget = localizer.getHeading();
    }



    @Override
    public void update() {
        // Continue with the last update state

        // Add robot to the dashboard field based on the pose
        TelemetryPacket packet = new TelemetryPacket();
        packet.fieldOverlay()
                .setFill("red")
                .fillCircle(localizer.getXPosition(), localizer.getYPosition(), DriveConfig.DISPLAY_SIZE)
                .strokeLine(
                        localizer.getXPosition(), localizer.getYPosition(),
                        localizer.getXPosition() + (DriveConfig.DISPLAY_SIZE * Math.cos(localizer.getHeading())),
                        localizer.getYPosition() + (DriveConfig.DISPLAY_SIZE * Math.sin(localizer.getHeading()))
        );
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
    }

    // Drivetrain implementation specific commands
    public abstract void drive(double forward, double strafe, double turn);
    public abstract void drive(double m1, double m2, double m3, double m4);

    public void stop() {
        drive(0.0d, 0.0d, 0.0d, 0.0d);
    }

    @Override
    public void init() {

    }

    // AUTONOMOUS COMMANDS
    public void autoRunPath(Trajectory trajectory) {

    }
}
