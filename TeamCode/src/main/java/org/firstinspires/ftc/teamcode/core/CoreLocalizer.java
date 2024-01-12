package org.firstinspires.ftc.teamcode.core;

import com.acmerobotics.roadrunner.Pose2d;

public abstract class CoreLocalizer extends CoreSensor {
    protected Pose2d currentPose;

    public Pose2d getPose() {
        return currentPose;
    }
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
    public void setStartingPosition(Pose2d pose) {
        currentPose = pose;
    }
}
