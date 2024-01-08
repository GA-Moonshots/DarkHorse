package org.firstinspires.ftc.teamcode.internal;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class CoreMessenger {
    // This is in case a subsystem or sensor wanted to grab dumped data from the stream.
    // Probably excessive. The majority of this class is just to handle sending info to the field
    // overlay on the dashboard.
    private boolean isDisabled = false;
    private Map<Object, Object> data = new HashMap<>();
    protected Telemetry telemetry;

    public CoreMessenger(CoreOpMode opMode) {
        this.telemetry = opMode.telemetry;

        opMode.addMessenger(this);
    }

    public void enable() {
        isDisabled = false;
    }

    public void disable() {
        isDisabled = true;
    }

    public void update() {
        if(isDisabled) {
            return;
        }

        for(Map.Entry<Object, Object> entry : data.entrySet()) {
            telemetry.addData(entry.getKey().toString(), entry.getValue());
        }
        // Clear data after update
        data.clear();
    }

    public void addData(Object caption, Object value) {
        data.put(caption, value);
    }
}
