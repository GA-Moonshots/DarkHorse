package org.firstinspires.ftc.teamcode.internal;

import com.acmerobotics.roadrunner.Pose2d;

public abstract class CoreLocalizer extends CoreSensor {
    protected Pose2d currentPose;

    public double getXPosition() {
        return currentPose.position.x;
    }

    public double getYPosition() {
        return currentPose.position.y;
    }

    public double getHeading() {
        return currentPose.heading.toDouble();
    }

    public CoreLocalizer(CoreOpMode opMode) {
        super(opMode);
    }
}
