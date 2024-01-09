package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.teamcode.internal.CoreOpMode;
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

    }
    @Override
    public void runUpdate() throws InterruptedException {

    }

    @Override
    public void runStop() {

    }
}
