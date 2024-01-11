package org.firstinspires.ftc.teamcode.internal;

import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.Twist2dDual;

public abstract class CoreLocalizer extends CoreSensor {
    protected Twist2dDual<Time> pose;

    public Twist2dDual<Time> getPose() {
        return pose;
    }

    public abstract void update();

    public CoreLocalizer(CoreOpMode opMode) {
        super(opMode);
    }
}
