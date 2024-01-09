package org.firstinspires.ftc.teamcode.sensors.localizers;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Encoder;
import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.PositionVelocityPair;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.config.Constants;
import org.firstinspires.ftc.teamcode.config.OdometryConfig;
import org.firstinspires.ftc.teamcode.internal.CoreLocalizer;
import org.firstinspires.ftc.teamcode.internal.CoreMessenger;
import org.firstinspires.ftc.teamcode.internal.CoreOpMode;
import org.firstinspires.ftc.teamcode.internal.math.Vector;
import org.firstinspires.ftc.teamcode.messengers.DrivetrainMessenger;

import kotlin.NotImplementedError;

public class ThreeWheelLocalizer extends CoreLocalizer {
    private final Encoder left, right, center;
    private CoreMessenger messenger;
    private int lastLeftPos, lastRightPos, lastCenterPos;

    public ThreeWheelLocalizer(CoreOpMode opMode) {
        super(opMode);

        left = new OverflowEncoder(new RawEncoder(opMode.hardwareMap.get(DcMotorEx.class, Constants.LEFT_ODOMETRY_NAME)));
        right = new OverflowEncoder(new RawEncoder(opMode.hardwareMap.get(DcMotorEx.class, Constants.RIGHT_ODOMETRY_NAME)));
        center = new OverflowEncoder(new RawEncoder(opMode.hardwareMap.get(DcMotorEx.class, Constants.CENTER_ODOMETRY_NAME)));

        // Preload values
        lastLeftPos = left.getPositionAndVelocity().position;
        lastRightPos = right.getPositionAndVelocity().position;
        lastCenterPos = center.getPositionAndVelocity().position;

        messenger = opMode.getMessenger(DrivetrainMessenger.class);
    }

    @Override
    public void update() {
        switch(OdometryConfig.RUN_MODE) {
            case LINEAR:
                linearUpdate();
            case CONSTANT_VELOCITY_ARC:
                constVelUpdate();
            case CONSTANT_ACCELERATION_POLYNOMIAL:
                constAccelUpdate();
        }

    }

    private void linearUpdate() {
        PositionVelocityPair leftPosVel = left.getPositionAndVelocity();
        PositionVelocityPair rightPosVel = right.getPositionAndVelocity();
        PositionVelocityPair centerPosVel = center.getPositionAndVelocity();

        // left = dx+L*dTheta/2
        // right = dx-L*dTheta/2
        // center = dy +BdTheta

        int leftDelta = leftPosVel.position - lastLeftPos;
        int rightDelta = rightPosVel.position - lastRightPos;
        int centerDelta = centerPosVel.position - lastCenterPos;

        double dx = (rightDelta * OdometryConfig.LEFT_X_POSITION) - (leftDelta * OdometryConfig.RIGHT_X_POSITION) / OdometryConfig.X_DISTANCE;
        double dTheta = (rightDelta - leftDelta) / OdometryConfig.X_DISTANCE;
        double dy = centerDelta - OdometryConfig.CENTER_Y_POSITION * dTheta;

        // Vector Rotation based on new theta
        double theta = currentPose.heading.toDouble() + dTheta;
        Vector2d gradient = Vector.rotateVector(new Pose2d(dx, dy, theta));

        double x = currentPose.position.x + gradient.x;
        double y = currentPose.position.y + gradient.y;

        currentPose = new Pose2d(x, y, theta);
        lastLeftPos = leftPosVel.position;
        lastRightPos = rightPosVel.position;
        lastCenterPos = centerPosVel.position;
    }

    private void constVelUpdate() {
        // THE EXTRA STEP
        // calculate the arc generated by this movement instead of just assuming that there's a
        // really low loop time
        PositionVelocityPair leftPosVel = left.getPositionAndVelocity();
        PositionVelocityPair rightPosVel = right.getPositionAndVelocity();
        PositionVelocityPair centerPosVel = center.getPositionAndVelocity();

        int leftDelta = leftPosVel.position - lastLeftPos;
        int rightDelta = rightPosVel.position - lastRightPos;
        int centerDelta = centerPosVel.position - lastCenterPos;

        double dx = (rightDelta * OdometryConfig.LEFT_X_POSITION) - (leftDelta * OdometryConfig.RIGHT_X_POSITION) / OdometryConfig.X_DISTANCE;
        double dTheta = (rightDelta - leftDelta) / OdometryConfig.X_DISTANCE;
        double dy = centerDelta - OdometryConfig.CENTER_Y_POSITION * dTheta;

        double rf = dx / dTheta;
        double rfdy = rf * Math.sin(dTheta);
        double rfdx = rf - (rf * Math.cos(dTheta));

        double rs = dy / dTheta;
        double rsdy = -(rs - (rs * Math.cos(dTheta)));
        double rsdx = rs * Math.sin(dTheta);

        double rdy = rfdy + rsdy;
        double rdx = rfdx + rsdx;

        double theta = currentPose.heading.toDouble() + dTheta;
        Vector2d gradient = Vector.rotateVector(new Pose2d(rdy, rdx, theta));

        double x = currentPose.position.x + gradient.x;
        double y = currentPose.position.y + gradient.y;

        currentPose = new Pose2d(x, y, theta);
        lastLeftPos = leftPosVel.position;
        lastRightPos = rightPosVel.position;
        lastCenterPos = centerPosVel.position;
    }

    private void constAccelUpdate() {
        throw new NotImplementedError("Constant Acceleration Odometry mode is not yet written since Michael is too scared of that insanity");
    }
}
