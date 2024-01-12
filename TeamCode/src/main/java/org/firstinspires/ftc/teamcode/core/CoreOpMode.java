package org.firstinspires.ftc.teamcode.core;

import java.util.ArrayList;
import java.util.List;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class CoreOpMode extends LinearOpMode {
    public CoreGamepad gamepad1;
    public CoreGamepad gamepad2;
    private final ArrayList<CoreSensor> sensors = new ArrayList<>();
    private final ArrayList<CoreSubsystem> subsystems = new ArrayList<>();
    private final ElapsedTime runtime = new ElapsedTime();
    private double lastUpdateTime = 0.0;
    private double currentUpdateTime = 0.0;

    public double getRuntimeSeconds() {
        return currentUpdateTime;
    }

    public double getDeltaTime() {
        return currentUpdateTime - lastUpdateTime;
    }

    public void addSensor(CoreSensor sensor) {
        sensors.add(sensor);
        sensor.update();
    }
    public void addSubsystem(CoreSubsystem system) {
        subsystems.add(system);
        system.init();
    }

    @SuppressWarnings("unchecked")
    public <T extends CoreSensor> T getSensor(Class<T> cls, String name) {
        for(CoreSensor sensor : sensors) {
            if(cls.isInstance(sensor) && sensor.name.equals(name)) {
                return (T) sensor;
            }
        }
        try {
            return cls.getConstructor(CoreOpMode.class, String.class).newInstance(this, name);
        } catch(Exception e) {
            throw new RuntimeException(String.format("Sensor with name %s of type %s was not found.", name, cls.getName()));
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends CoreSensor> T getSensor(Class<T> cls) {
        for(CoreSensor sensor : sensors) {
            if(cls.isInstance(sensor)) {
                return (T) sensor;
            }
        }
        try {
            return cls.getConstructor(CoreOpMode.class).newInstance(this);
        } catch(Exception e) {
            throw new RuntimeException(String.format("Sensor with of type %s was not found.", cls.getName()));
        }
    }

    public boolean inInit() {
        _intlUpdate();

        return opModeInInit();
    }

    public boolean isActive() {
        _intlUpdate();

        return opModeIsActive();
    }


    /* Updates the gamepads and then subsystems in order. Sensors are updated when their value is
    // requested. This allows for subsystems and sensors to ensure update order and no double readings
    // for things like distance sensors, but avoids spending time on sensors that are not needed.
    // Every subsystem should hold it's own state for this call.
    // The subsystems can also report fails and telemetry by updating states instead of
    // being forced inside of large loops. This method also allows for multiple telemetries
    // to exist at once. This also allows for sensors to directly take controller input for
    // Things like switchable light color sensors or Camera controls.
     */
    private void _intlUpdate() {
        lastUpdateTime = currentUpdateTime;
        currentUpdateTime = runtime.seconds();
        gamepad1.update();
        gamepad2.update();
        // Now that all of the sensors are updated, we can now update the subsystems
        // with the sensor inputs from the previous sensor read.
        for(CoreSubsystem subsystem : subsystems) {
            subsystem.update();
        }

        // Update the telemetry
        telemetry.update();

        // check for sensors that have been updated and ensure that the hasUpdated flag is set
        // to false
        for(CoreSensor sensor : sensors) {
            sensor._intlPostUpdate();
        }
    }

    public void resetRuntime() {
        super.resetRuntime();
        this.lastUpdateTime = 0.0;
        this.currentUpdateTime = 0.0;
        this.runtime.reset();
    }

    @Override
    public void runOpMode() {
        gamepad1 = new CoreGamepad(super.gamepad1);
        gamepad2 = new CoreGamepad(super.gamepad2);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        // Optimize sensor reads
        // Note that if a sensor that breaks the spec of CoreSensor is being called multiple times
        // it requires that this mode be set to MANUAL and an additional for loop should be added in
        // _intlUpdate calling clearBulkCache.
        List<LynxModule> hubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        try {
            runInit();
            waitForStart();
            runStart();
            runUpdate();
            runStop();
        } catch(InterruptedException e) {
            runStop();
        }
    }

    public abstract void runInit() throws InterruptedException;
    public abstract void runStart() throws InterruptedException;
    public abstract void runUpdate() throws InterruptedException;
    public abstract void runStop();
}
