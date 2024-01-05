package org.firstinspires.ftc.teamcode.internal;

import java.util.ArrayList;
import java.util.Locale;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;

public abstract class CoreOpMode extends LinearOpMode {
    public CoreGamepad gamepad1;
    public CoreGamepad gamepad2;
    private final ArrayList<CoreSensor> sensors = new ArrayList<>();
    private final ArrayList<CoreSubsystem> subsystems = new ArrayList<>();
    private final ArrayList<CoreMessenger> messengers = new ArrayList<>();

    public void addSubsystem(CoreSubsystem system) {subsystems.add(system);}
    public void addSensor(CoreSensor sensor) {sensors.add(sensor);}
    public void addMessenger(CoreMessenger messenger) { messengers.add(messenger);}

    public <T extends CoreSensor> T getSensor(Class<T> cls, String name) throws RobotCoreException {
        for(CoreSensor sensor : sensors) {
            if(cls.isInstance(sensor) && sensor.name.equals(name)) {
                return (T) sensor;
            }
        }

        throw new RobotCoreException(String.format("Sensor with name %s of type %s was not found.", name, cls.getName()));
    }

    public <T extends CoreSensor> T getSensor(Class<T> cls) throws RobotCoreException {
        for(CoreSensor sensor : sensors) {
            if(cls.isInstance(sensor)) {
                return (T) sensor;
            }
        }

        throw new RobotCoreException(String.format("Sensor with of type %s was not found.", cls.getName()));
    }

    // Just in case something else requires a different subsystem's messenger.
    public <T extends CoreMessenger> T getMessenger(Class<T> cls) throws RobotCoreException {
        for(CoreMessenger messenger : messengers) {
            if(cls.isInstance(messenger)) {
                return (T) messenger;
            }
        }

        throw new RobotCoreException(String.format("Messenger with of type %s was not found.", cls.getName()));
    }

    // Updates the gamepads, sensors, and then subsystems in this order.
    // This allows for subsystems and sensors to ensure update order and no double readings for
    // things like distance sensors. Every subsystem should hold it's own state for this call.
    // The subsystems can also report fails and telemetry by updating states instead of
    // being forced inside of large loops. This method also allows for multiple telemetries
    // to exist at once. This also allows for sensors to directly take controller input for
    // Things like switchable light color sensors or Camera controls.

    public boolean inInit() {
        _intlUpdate();

        return opModeInInit();
    }

    public boolean isActive() {
        _intlUpdate();

        return opModeIsActive();
    }

    private void _intlUpdate() {
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

        // Last, update all the messengers to send the data to the telemetry stream
        for(CoreMessenger messenger : messengers) {
            messenger.update();
        }

        // Update the telemetry
        telemetry.update();
    }

    public void _intlSensorNotFound(String submoduleName, String sensorName, String sensorID) {
        throw new RuntimeException(String.format(Locale.US,
                "Submodule %s depends on sensor %s with ID %s, which was not found. Perhaps your initialization order is wrong?", submoduleName, sensorName, sensorID));
    }
    public void _intlSensorNotFound(String submoduleName, String sensorName) {
        throw new RuntimeException(String.format(Locale.US,
                "Submodule %s depends on sensor %s, which was not found. Perhaps your initialization order is wrong?", submoduleName, sensorName));
    }

    @Override
    public void runOpMode() {
        gamepad1 = new CoreGamepad(super.gamepad1);
        gamepad2 = new CoreGamepad(super.gamepad2);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
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
