package org.firstinspires.ftc.teamcode.sensors.localizers;

import com.acmerobotics.roadrunner.ftc.Encoder;

import org.firstinspires.ftc.teamcode.core.CoreLocalizer;
import org.firstinspires.ftc.teamcode.core.CoreOpMode;
import org.firstinspires.ftc.teamcode.sensors.IMU;

public class TwoWheelLocalizer extends CoreLocalizer {
    private IMU imu;
    private Encoder parallel, perpendicular;

    public TwoWheelLocalizer(CoreOpMode opMode) {
        super(opMode);
        imu = opMode.getSensor(IMU.class);
    }

    @Override
    public void update() {

    }
}
