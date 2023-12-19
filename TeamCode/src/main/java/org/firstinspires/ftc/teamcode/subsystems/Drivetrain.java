package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.internal.CoreOpMode;
import org.firstinspires.ftc.teamcode.internal.CoreSubsystem;

public abstract class Drivetrain extends CoreSubsystem {
    public Drivetrain(CoreOpMode opMode) {
        opMode.addSubsystem(this);
    }

}
