package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.Trajectory;

import org.firstinspires.ftc.teamcode.config.DriveConfig;
import org.firstinspires.ftc.teamcode.internal.CoreLocalizer;
import org.firstinspires.ftc.teamcode.sensors.localizers.ThreeWheelLocalizer;
import org.firstinspires.ftc.teamcode.messengers.DrivetrainMessenger;
import org.firstinspires.ftc.teamcode.internal.CoreOpMode;
import org.firstinspires.ftc.teamcode.internal.CoreSubsystem;

public abstract class Drivetrain extends CoreSubsystem {
    protected boolean isFieldCentric = true;
    protected double fieldCentricTarget;

    // Sensors
    protected CoreLocalizer localizer;

    public Drivetrain(CoreOpMode opMode) {
        super(opMode);

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

    // AUTONOMOUS COMMANDS
    public void autoRunPath(Trajectory trajectory) {

    }

    @Override
    public void update() {
        // Continue with the last update state
    }

    // Drivetrain implementation specific commands
    public abstract void drive(double forward, double strafe, double turn);
    public abstract void stop();
}
