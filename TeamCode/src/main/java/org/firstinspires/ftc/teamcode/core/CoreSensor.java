package org.firstinspires.ftc.teamcode.core;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class CoreSensor {
    protected Telemetry telemetry;
    public CoreSensor(CoreOpMode opMode) {
        this(opMode, "");
    }

    public CoreSensor(CoreOpMode opMode, String name) {
        this.name = name;
        this.telemetry = opMode.telemetry;
        opMode.addSensor(this);
    }

    protected boolean hasUpdated = false;
    protected abstract void update();
    public final String name;
    //
    public void _intlPostUpdate() {
        hasUpdated = false;
    }
}
