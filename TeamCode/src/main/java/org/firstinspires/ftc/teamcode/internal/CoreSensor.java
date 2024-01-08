package org.firstinspires.ftc.teamcode.internal;

public abstract class CoreSensor {
    public CoreSensor(CoreOpMode opMode) {
        this(opMode, "");
    }

    public CoreSensor(CoreOpMode opMode, String name) {
        this.name = name;
        opMode.addSensor(this);
    }

    public abstract void update();
    public final String name;
}
