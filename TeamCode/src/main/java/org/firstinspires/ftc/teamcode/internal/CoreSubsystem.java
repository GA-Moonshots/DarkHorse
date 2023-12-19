package org.firstinspires.ftc.teamcode.internal;

public abstract class CoreSubsystem {
    // These could potentially be booleans to cleanly send information between opMode and subsystem
    // up the tree instead of just down, but for the moment, these can be voids until I find that
    // functionality is potentially necessary.
    public abstract void init();
    public abstract void update();
}
