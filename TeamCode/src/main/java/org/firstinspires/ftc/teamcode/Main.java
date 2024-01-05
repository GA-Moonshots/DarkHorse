package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.internal.CoreOpMode;
import org.firstinspires.ftc.teamcode.sensors.Odometry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

@TeleOp(name = "Dark Horse")
public class Main extends CoreOpMode {
    Drivetrain drive;
    Odometry odometry;
    @Override
    public void runInit() {
        odometry = new Odometry(this);
        drive = new MecanumDrive(this);


    }

    @Override
    public void runStart() {

    }

    @Override
    public void runUpdate() {

    }

    @Override
    public void runStop() {
        drive.stop();
    }
}
