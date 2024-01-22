package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.CoreLocalizer;
import org.firstinspires.ftc.teamcode.core.CoreOpMode;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;


// TODO: 
// 1. Check double nature of storage in Pose2d
// 2. Rework XY Localizer grid to match the controller input relative to the field.
// 3. Take an input from the runInit method that allows for the localizer to start in the spot that it is actually in

@SuppressWarnings("unused")
@TeleOp(name = "Dark Horse")
public class Main extends CoreOpMode {
    private Drivetrain drive;

    private boolean isSlowMode = true;

    @Override
    public void runInit() {
        drive = new MecanumDrive(this);
        getSensor(CoreLocalizer.class).setStartingPosition(new Pose2d(0, 0, 0));

        while(inInit()) {
            // Hopefully in init we're hitting 50+ UPS, and in update around 40+.
            telemetry.addData("UPS", "%.2f", 1 / getDeltaTime());
            telemetry.addData("GP1LS", "(%.2f, %.2f)", gamepad1.getLeftStickX(), gamepad1.getLeftStickY());
            telemetry.addData("GP1RS", "(%.2f, %.2f)", gamepad1.getRightStickX(), gamepad1.getRightStickY());
        }
    }

    @Override
    public void runStart() {
        resetRuntime();
    }

    @Override
    public void runUpdate() {
        while(isActive()) {
            telemetry.addData("UPS", "%.2f", 1 / getDeltaTime());

            if(gamepad1.getADown()) {
                drive.toggleFieldCentric();
            }

            if(gamepad1.getBButton()) {
                drive.setFieldCentricTarget();
            }

            if(gamepad1.getLeftButtonDown()) {
                this.isSlowMode = !isSlowMode;
            }

            double forward = gamepad1.getLeftStickY();
            double strafe = gamepad1.getLeftStickX();
            double turn = gamepad1.getRightStickX();

            double multi = isSlowMode ? 0.2 : 1.0;
            drive.drive(forward * multi, strafe * multi, turn * multi);
        }
    }

    @Override
    public void runStop() {
        drive.stop();
    }
}
