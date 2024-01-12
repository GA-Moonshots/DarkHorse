package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TrajectoryBuilder;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.CoreLocalizer;
import org.firstinspires.ftc.teamcode.sensors.localizers.ThreeWheelLocalizer;
import org.firstinspires.ftc.teamcode.core.CoreOpMode;
import org.firstinspires.ftc.teamcode.core.CoreSubsystem;

public abstract class Drivetrain extends CoreSubsystem {
    protected boolean isFieldCentric = true;
    protected final Telemetry telemetry;
    protected double fieldCentricTarget;

    // Sensors
    protected CoreLocalizer localizer;

    public Drivetrain(CoreOpMode opMode) {
        super(opMode);

        this.telemetry = opMode.telemetry;
        localizer = opMode.getSensor(ThreeWheelLocalizer.class);
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
        fieldCentricTarget = localizer.getPose().angle.value();
    }



    @Override
    public void update() {
        // Continue with the last update state

        // Add robot to the dashboard field based on the pose
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
