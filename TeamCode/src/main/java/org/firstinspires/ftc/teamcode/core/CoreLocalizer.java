package org.firstinspires.ftc.teamcode.core;

import com.acmerobotics.roadrunner.Pose2d;

public abstract class CoreLocalizer extends CoreSensor {
    protected Pose2d currentPose = new Pose2d(0, 0, 0);

    public Pose2d getPose() {
        if(!hasUpdated) { update(); }
        return currentPose;
    }
    public double getXPosition() {
        if(!hasUpdated) { update(); }
        return currentPose.position.x;
    }
    public double getYPosition() {
        if(!hasUpdated) { update(); }
        return currentPose.position.y;
    }
    public double getHeading() {
        if(!hasUpdated) { update(); }
        return currentPose.heading.toDouble();
    }
    public CoreLocalizer(CoreOpMode opMode) {
        super(opMode);
    }
    public void setStartingPosition(Pose2d pose) {
        currentPose = pose;
    }
}
