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
import org.firstinspires.ftc.teamcode.internal.CoreLocalizer;
import org.firstinspires.ftc.teamcode.internal.CoreOpMode;

public final class ThreeWheelLocalizer extends CoreLocalizer {
    public final Encoder left, right, center;

    private int lastLeftPos, lastRightPos, lastCenterPos;

    public ThreeWheelLocalizer(CoreOpMode opMode) {
        super(opMode);
        HardwareMap hardwareMap = opMode.hardwareMap;
        // TODO: make sure your config has **motors** with these names (or change them)
        //   the encoders should be plugged into the slot matching the named motor
        //   see https://ftc-docs.firstinspires.org/en/latest/hardware_and_software_configuration/configuring/index.html
        left = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, Constants.LEFT_ODOMETRY_NAME)));
        right = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, Constants.RIGHT_ODOMETRY_NAME)));
        center = new OverflowEncoder(new RawEncoder(hardwareMap.get(DcMotorEx.class, Constants.CENTER_ODOMETRY_NAME)));

        // TODO: reverse encoder directions if needed
        //   par0.setDirection(DcMotorSimple.Direction.REVERSE);

        lastLeftPos = left.getPositionAndVelocity().position;
        lastRightPos = right.getPositionAndVelocity().position;
        lastCenterPos = center.getPositionAndVelocity().position;
    }

    public void update() {
        PositionVelocityPair leftPosVel = left.getPositionAndVelocity();
        PositionVelocityPair rightPosVel = right.getPositionAndVelocity();
        PositionVelocityPair centerPosVel = center.getPositionAndVelocity();

        int leftDeltaPos = leftPosVel.position - lastLeftPos;
        int rightDeltaPos = rightPosVel.position - lastRightPos;
        int centerDeltaPos = centerPosVel.position - lastCenterPos;

        Twist2dDual<Time> twist = new Twist2dDual<>(
                new Vector2dDual<>(
                        new DualNum<Time>(new double[] {
                                (OdometryConfig.LEFT_X_POSITION * rightDeltaPos - OdometryConfig.RIGHT_X_POSITION * leftDeltaPos) /
                                        (OdometryConfig.LEFT_X_POSITION - OdometryConfig.RIGHT_X_POSITION),
                                (OdometryConfig.LEFT_X_POSITION * rightPosVel.velocity - OdometryConfig.RIGHT_X_POSITION * leftPosVel.velocity) /
                                        (OdometryConfig.LEFT_X_POSITION - OdometryConfig.RIGHT_X_POSITION),
                        }).times(DriveConfig.inPerTick),
                        new DualNum<Time>(new double[] {
                                (OdometryConfig.CENTER_Y_POSITION /
                                        (OdometryConfig.LEFT_X_POSITION - OdometryConfig.RIGHT_X_POSITION) *
                                        (rightDeltaPos - leftDeltaPos) + centerDeltaPos),
                                (OdometryConfig.CENTER_Y_POSITION /
                                        (OdometryConfig.LEFT_X_POSITION - OdometryConfig.RIGHT_X_POSITION) *
                                        (rightPosVel.velocity - leftPosVel.velocity) + centerPosVel.velocity),
                        }).times(DriveConfig.inPerTick)
                ),
                new DualNum<>(new double[] {
                        (leftDeltaPos - rightDeltaPos) /
                                (OdometryConfig.LEFT_X_POSITION - OdometryConfig.RIGHT_X_POSITION),
                        (leftPosVel.velocity - rightPosVel.velocity) /
                                (OdometryConfig.LEFT_X_POSITION - OdometryConfig.RIGHT_X_POSITION),
                })
        );

        lastLeftPos = leftPosVel.position;
        lastRightPos = rightPosVel.position;
        lastCenterPos = centerPosVel.position;

        pose = twist;
    }
}