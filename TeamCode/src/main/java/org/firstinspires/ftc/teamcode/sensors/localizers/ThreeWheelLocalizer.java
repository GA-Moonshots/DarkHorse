package org.firstinspires.ftc.teamcode.sensors.localizers;

import org.firstinspires.ftc.teamcode.config.DriveConfig;

import com.acmerobotics.roadrunner.DualNum;
import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.Twist2dDual;
import com.acmerobotics.roadrunner.Vector2dDual;
import com.acmerobotics.roadrunner.ftc.Encoder;
import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.PositionVelocityPair;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.Constants;
import org.firstinspires.ftc.teamcode.config.OdometryConfig;
import org.firstinspires.ftc.teamcode.core.CoreLocalizer;
import org.firstinspires.ftc.teamcode.core.CoreOpMode;
import org.firstinspires.ftc.teamcode.core.math.Vector;

public class ThreeWheelLocalizer extends CoreLocalizer {
    private final Encoder left, right, center;
    private double leftPos, rightPos, centerPos;

    public ThreeWheelLocalizer(CoreOpMode opMode) {
        super(opMode);

        left = new OverflowEncoder(new RawEncoder(opMode.hardwareMap.get(DcMotorEx.class, Constants.LEFT_ODOMETRY_NAME)));
        right = new OverflowEncoder(new RawEncoder(opMode.hardwareMap.get(DcMotorEx.class, Constants.RIGHT_ODOMETRY_NAME)));
        center = new OverflowEncoder(new RawEncoder(opMode.hardwareMap.get(DcMotorEx.class, Constants.CENTER_ODOMETRY_NAME)));
    }

    public void update() {
        PositionVelocityPair leftPosVel = left.getPositionAndVelocity();
        PositionVelocityPair rightPosVel = right.getPositionAndVelocity();
        PositionVelocityPair centerPosVel = center.getPositionAndVelocity();

        // left = dx+L*dTheta/2
        // right = dx-L*dTheta/2
        // center = dy +BdTheta

        double dLeft = leftPosVel.position - leftPos;
        double dRight = rightPosVel.position - rightPos;
        double dCenter = centerPosVel.position - centerPos;

        double dx = (dRight * OdometryConfig.LEFT_X_POSITION) - (dLeft * OdometryConfig.RIGHT_X_POSITION) / OdometryConfig.X_DISTANCE;
        double dTheta = (dRight - dLeft) / OdometryConfig.X_DISTANCE;
        double dy = dCenter - OdometryConfig.CENTER_Y_POSITION * dTheta;

        // Vector Rotation based on new theta
        double theta = currentPose.heading.toDouble() + dTheta;
        Vector2d gradient = Vector.rotateVector(new Pose2d(dx, dy, theta));

        double x = currentPose.position.x + gradient.x;
        double y = currentPose.position.y + gradient.y;

        currentPose = new Pose2d(x, y, theta);
        leftPos = leftPosVel.position;
        rightPos = rightPosVel.position;
        centerPos = centerPosVel.position;
    }


    private void constVelUpdate() {
        // THE EXTRA STEP
        // calculate the arc generated by this movement instead of just assuming that there's a
        // really low loop time
        PositionVelocityPair leftPosVel = left.getPositionAndVelocity();
        PositionVelocityPair rightPosVel = right.getPositionAndVelocity();
        PositionVelocityPair centerPosVel = center.getPositionAndVelocity();


        double dLeft = leftPosVel.position - leftPos;
        double dRight = rightPosVel.position - rightPos;
        double dCenter = centerPosVel.position - centerPos;

        double dx = (dRight * OdometryConfig.LEFT_X_POSITION) - (dLeft * OdometryConfig.RIGHT_X_POSITION) / OdometryConfig.X_DISTANCE;
        double dTheta = (dRight - dLeft) / OdometryConfig.X_DISTANCE;
        double dy = dCenter - OdometryConfig.CENTER_Y_POSITION * dTheta;

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
        leftPos = leftPosVel.position;
        rightPos = rightPosVel.position;
        centerPos = centerPosVel.position;
    }

    private void constAccelUpdate() {
        throw new NotImplementedError("Constant Acceleration Odometry mode is not yet written since Michael is too scared of that insanity");
    }
}