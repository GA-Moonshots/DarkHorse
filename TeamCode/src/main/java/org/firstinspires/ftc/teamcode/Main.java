package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.internal.CoreOpMode;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

public class Main extends CoreOpMode {
    Drivetrain drive;
    @Override
    public void runInit() {
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
