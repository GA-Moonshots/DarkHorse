package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.internal.CoreOpMode;
import org.firstinspires.ftc.teamcode.sensors.ThreeWheelOdomentry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

@TeleOp(name = "Dark Horse")
public class Main extends CoreOpMode {
    Drivetrain drive;
    ThreeWheelOdomentry odometry;
    @Override
    public void runInit() {
        odometry = new ThreeWheelOdomentry(this);
        drive = new MecanumDrive(this);
    }

    @Override
    public void runStart() {

    }

    @Override
    public void runUpdate() {
        if(gamepad1.getADown()) {
            drive.toggleFieldCentric();
        }

        if(gamepad1.getYButton()) {
            drive.setFieldCentricTarget();
        }

        if(gamepad1.getDPadHorizontal() < 0 && gamepad1.getDPadVertical() == 0) {

        } else if(gamepad1.getDPadHorizontal() > 0 && gamepad1.getDPadVertical() == 0) {

        } else if(gamepad1.getDPadHorizontal() == 0 && gamepad1.getDPadVertical() > 0) {

        } else {

        }
    }

    @Override
    public void runStop() {
        drive.stop();
    }
}
