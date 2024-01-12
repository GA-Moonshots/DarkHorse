package org.firstinspires.ftc.teamcode.core;

public abstract class CoreSubsystem {
    public CoreSubsystem(CoreOpMode opMode) {
        opMode.addSubsystem(this);
    }
    // These could potentially be booleans or Objects to cleanly send information between opMode and
    // subsystem up the tree instead of just down, but for the moment, these can be voids until I
    // find that functionality is potentially necessary.
    public abstract void init();
    public abstract void update();
}
