package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.exception.RobotCoreException;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.messengers.DrivetrainMessager;
import org.firstinspires.ftc.teamcode.internal.CoreOpMode;
import org.firstinspires.ftc.teamcode.internal.CoreSubsystem;
import org.firstinspires.ftc.teamcode.sensors.IMU;
import org.firstinspires.ftc.teamcode.sensors.Odometry;

public abstract class Drivetrain extends CoreSubsystem {
    protected boolean isFieldCentric = true;
    protected double fieldCentricTarget;

    // Sensors
    protected IMU imu;
    protected Odometry odometry;

    // Messengers
    protected DrivetrainMessager messenger;

    protected boolean isGyroLocked = true;

    public Drivetrain(CoreOpMode opMode) {
        imu = new IMU(opMode);
        fieldCentricTarget = imu.getZAngle();

        try {
            odometry = opMode.getSensor(Odometry.class);
        } catch (RobotCoreException e) {
            // I kinda like this, it directly gives you an error and tells you exactly what happened.
            opMode._intlSensorNotFound("Drivetrain", "Odometry");
        }

        try {
            imu = opMode.getSensor(IMU.class);
        } catch (RobotCoreException e) {
            opMode._intlSensorNotFound("Drivetrain", "IMU");
        }

        messenger = new DrivetrainMessager(opMode);
        if(Constants.DRIVETRAIN_MESSENGER_ENABLED)
            messenger.enable();
        else
            messenger.disable();

        // After everything sets up, add to the opMode updates
        opMode.addMessenger(messenger);
        opMode.addSubsystem(this);
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

    public abstract void drive(double forward, double strafe, double turn);
    public abstract void stop();
}
