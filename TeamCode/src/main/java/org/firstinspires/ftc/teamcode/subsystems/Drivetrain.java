package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.internal.CoreOpMode;
import org.firstinspires.ftc.teamcode.internal.CoreSubsystem;
import org.firstinspires.ftc.teamcode.sensors.IMU;

public abstract class Drivetrain extends CoreSubsystem {
    protected boolean isFieldCentric = true;
    protected double fieldCentricTarget = 0.0d;

    protected IMU imu;

    protected CoreOpMode opMode;

    public Drivetrain(CoreOpMode opMode) {
        opMode.addSubsystem(this);
        imu = new IMU(opMode);
        fieldCentricTarget = imu.getZAngle();
    }

    public abstract void drive(double forward, double strafe, double turn);
    public abstract void stop();

}
