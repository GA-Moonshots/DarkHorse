package org.firstinspires.ftc.teamcode.internal;

import java.util.ArrayList;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class CoreOpMode extends LinearOpMode {
    public CoreGamepad gamepad1;
    public CoreGamepad gamepad2;
    private ArrayList<CoreSensor> sensors = new ArrayList<>();
    private ArrayList<CoreSubsystem> subsystems = new ArrayList<>();

    public void addSubsystem(CoreSubsystem system) {
        subsystems.add(system);
    }

    public void addSensor(CoreSensor sensor) {
        sensors.add(sensor);
    }

    // Updates the gamepads, sensors, and then subsystems in this order.
    // This allows for subsystems and sensors to ensure update order and no double readings for
    // things like distance sensors. Every subsystem should hold it's own state for this call.
    // The subsystems can also report fails and telemetry by updating states instead of
    // being forced inside of large loops. This method also allows for multiple telemetries
    // to exist at once. This also allows for sensors to directly take controller input for
    // Things like switchable light color sensors or Camera controls.

    public boolean inInit() {
        internal_update();

        return opModeInInit();
    }

    public boolean isActive() {
        internal_update();

        return opModeIsActive();
    }

    public void internal_update() {
        sleep(25);
        // Update All Sensors, even under idle
        for(CoreSensor sensor : sensors) {
            sensor.update();
        }

        // Now that all of the sensors are updated, we can now update the subsystems
        // with the sensor inputs from the previous sensor read.
        for(CoreSubsystem subsystem : subsystems) {
            subsystem.update();
        }
    }

    @Override
    public void runOpMode() {
        runInit();
        waitForStart();
        runStart();
        runUpdate();
        runStop();
    }

    public abstract void runInit();
    public abstract void runStart();
    public abstract void runUpdate();
    public abstract void runStop();
}
