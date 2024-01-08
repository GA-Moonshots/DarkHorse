package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.internal.CoreLocalizer;
import org.firstinspires.ftc.teamcode.internal.CoreOpMode;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

@TeleOp(name = "Dark Horse")
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
        if(gamepad1.getADown()) {
            drive.toggleFieldCentric();
        }

        if(gamepad1.getBButton()) {
            drive.setFieldCentricTarget();
        }

        double forward = gamepad1.getLeftStickY();
        double strafe = gamepad1.getLeftStickX();
        double turn = gamepad1.getRightStickX();
        double multi = gamepad1.getLeftButton() ? 0.2 : 1.0;
        drive.drive(forward * multi, strafe * multi, turn * multi);
    }

    @Override
    public void runStop() {
        drive.stop();
    }
}
