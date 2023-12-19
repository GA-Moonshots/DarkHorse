package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.internal.CoreOpMode;

public class MecanumDrive extends Drivetrain {
    public MecanumDrive(CoreOpMode opMode) {
        super(opMode);
    }
    @Override
    public boolean update() {
        // Continue with the last update state
        return true;
    }
}
