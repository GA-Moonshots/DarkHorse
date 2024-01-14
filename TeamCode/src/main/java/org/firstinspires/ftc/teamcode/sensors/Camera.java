package org.firstinspires.ftc.teamcode.sensors;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.config.Constants;
import org.firstinspires.ftc.teamcode.core.CoreOpMode;
import org.firstinspires.ftc.teamcode.core.CoreSensor;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Camera extends CoreSensor {
    private final AprilTagProcessor aprilTag;
    private final VisionPortal visionPortal;

    // Storage state
    List<AprilTagDetection> detections;
    String status;

    public Camera(CoreOpMode opMode) {
        super(opMode);
        aprilTag = new AprilTagProcessor.Builder()
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                .build();
        WebcamName name = opMode.hardwareMap.get(WebcamName.class, Constants.WEBCAM_NAME);
        visionPortal = new VisionPortal.Builder()
                .setCamera(name)
                .addProcessor(aprilTag)
                .build();
    }

    @Override
    public void update() {
        if(visionPortal.getCameraState() == VisionPortal.CameraState.STREAMING) {
            ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
            if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
                exposureControl.setMode(ExposureControl.Mode.Manual);
            }
            exposureControl.setExposure(15, TimeUnit.MILLISECONDS);

            // Set Gain.
            GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
            gainControl.setGain(25);
        }
        detections = aprilTag.getDetections();
        status = visionPortal.getCameraState().toString();
    }

    public List<AprilTagDetection> getDetections() {
        return detections;
    }

    public String getStatus(){
        return status;
    }
}
