package org.firstinspires.ftc.teamcode.subsystems;

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

    // Messengers
    protected DrivetrainMessenger messenger;


    public Drivetrain(CoreOpMode opMode) {
        super(opMode);

        messenger = opMode.getMessenger(DrivetrainMessenger.class);
        localizer = opMode.getSensor(ThreeWheelLocalizer.class);

        if(DriveConfig.MESSENGER_ENABLED)
            messenger.enable();
        else
            messenger.disable();


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

    public void setFieldCentricTarget() {fieldCentricTarget = localizer.getHeading();}

    // AUTONOMOUS COMMANDS
    public void autoRunPath() {

    }

    @Override
    public void update() {
        // Continue with the last update state
        messenger.addRobotToFieldOverlay(localizer.getXPosition(), localizer.getYPosition(), localizer.getHeading());
    }

    // Drivetrain implementation specific commands
    public abstract void drive(double forward, double strafe, double turn);
    public abstract void stop();
}
