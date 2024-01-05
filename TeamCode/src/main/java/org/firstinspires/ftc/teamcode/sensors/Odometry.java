package org.firstinspires.ftc.teamcode.sensors;

import com.acmerobotics.roadrunner.DualNum;
import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.Twist2dDual;
import com.acmerobotics.roadrunner.Vector2dDual;
import com.acmerobotics.roadrunner.ftc.Encoder;
import com.acmerobotics.roadrunner.ftc.FlightRecorder;
import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.PositionVelocityPair;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.internal.CoreOpMode;
import org.firstinspires.ftc.teamcode.internal.CoreSensor;

import java.util.Locale;

public class Odometry extends CoreSensor {
    Twist2dDual<Time> twist;
    
    @Override
    public void update() {
        PositionVelocityPair par0PosVel = left.getPositionAndVelocity();
        PositionVelocityPair par1PosVel = right.getPositionAndVelocity();
        PositionVelocityPair perpPosVel = center.getPositionAndVelocity();

        int par0PosDelta = par0PosVel.position - lastPar0Pos;
        int par1PosDelta = par1PosVel.position - lastPar1Pos;
        int perpPosDelta = perpPosVel.position - lastPerpPos;

        Twist2dDual<Time> twist = new Twist2dDual<>(
                new Vector2dDual<>(
                        new DualNum<Time>(new double[] {
                                (Constants.ODOMETRY_LEFT_Y_POSITION * par1PosDelta - Constants.ODOMETRY_RIGHT_Y_POSITION * par0PosDelta) / (Constants.ODOMETRY_LEFT_Y_POSITION - Constants.ODOMETRY_RIGHT_Y_POSITION),
                                (Constants.ODOMETRY_LEFT_Y_POSITION * par1PosVel.velocity - Constants.ODOMETRY_RIGHT_Y_POSITION * par0PosVel.velocity) / (Constants.ODOMETRY_LEFT_Y_POSITION - Constants.ODOMETRY_RIGHT_Y_POSITION),
                        }).times(inPerTick),
                        new DualNum<Time>(new double[] {
                                (Constants.ODOMETRY_CENTER_SENSOR_X_POSITION / (Constants.ODOMETRY_LEFT_Y_POSITION - Constants.ODOMETRY_RIGHT_Y_POSITION) * (par1PosDelta - par0PosDelta) + perpPosDelta),
                                (Constants.ODOMETRY_CENTER_SENSOR_X_POSITION / (Constants.ODOMETRY_LEFT_Y_POSITION - Constants.ODOMETRY_RIGHT_Y_POSITION) * (par1PosVel.velocity - par0PosVel.velocity) + perpPosVel.velocity),
                        }).times(inPerTick)
                ),
                new DualNum<>(new double[] {
                        (par0PosDelta - par1PosDelta) / (Constants.ODOMETRY_LEFT_Y_POSITION - Constants.ODOMETRY_RIGHT_Y_POSITION),
                        (par0PosVel.velocity - par1PosVel.velocity) / (Constants.ODOMETRY_LEFT_Y_POSITION - Constants.ODOMETRY_RIGHT_Y_POSITION),
                })
        );

        lastPar0Pos = par0PosVel.position;
        lastPar1Pos = par1PosVel.position;
        lastPerpPos = perpPosVel.position;

        this.twist = twist;
    }

    public final Encoder left, right, center;

    public final double inPerTick;

    private int lastPar0Pos, lastPar1Pos, lastPerpPos;

    public Odometry(CoreOpMode opMode) {

        left = new OverflowEncoder(new RawEncoder(opMode.hardwareMap.get(DcMotorEx.class, Constants.LEFT_ODOMETRY_NAME)));
        right = new OverflowEncoder(new RawEncoder(opMode.hardwareMap.get(DcMotorEx.class, Constants.RIGHT_ODOMETRY_NAME)));
        center = new OverflowEncoder(new RawEncoder(opMode.hardwareMap.get(DcMotorEx.class, Constants.CENTER_ODOMETRY_NAME)));

        // TODO: reverse encoder directions if needed
        //   left.setDirection(DcMotorSimple.Direction.REVERSE);

        lastPar0Pos = left.getPositionAndVelocity().position;
        lastPar1Pos = right.getPositionAndVelocity().position;
        lastPerpPos = center.getPositionAndVelocity().position;

        this.inPerTick = Constants.ODOMETRY_TICKS_PER_INCH;

        FlightRecorder.write("THREE_DEAD_WHEEL_PARAMS", String.format(Locale.US, "LeftY: %f, CenterX: %f, RightY: %f",
                Constants.ODOMETRY_LEFT_Y_POSITION, Constants.ODOMETRY_CENTER_SENSOR_X_POSITION, Constants.ODOMETRY_RIGHT_Y_POSITION));

        opMode.addSensor(this);
    }
}
