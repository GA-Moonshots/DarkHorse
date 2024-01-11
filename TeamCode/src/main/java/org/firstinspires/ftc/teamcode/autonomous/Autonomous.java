package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.teamcode.core.CoreOpMode;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous")
public class Autonomous extends CoreOpMode {
    private Drivetrain drive;

    @Override
    public void runInit() throws InterruptedException {
        drive = new MecanumDrive(this);
    }

    @Override
    public void runStart() throws InterruptedException {
        // Build the trajectory for the rest of the auto
    }
    @Override
    public void runUpdate() throws InterruptedException {

    }

    @Override
    public void runStop() {

    }
}
