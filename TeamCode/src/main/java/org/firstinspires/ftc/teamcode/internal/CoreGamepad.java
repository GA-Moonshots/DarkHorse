package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.robotcore.hardware.Gamepad;

public class CoreGamepad {
    private Gamepad gamepad;

    private boolean aPressed = false;
    private boolean aPressedLast = false;

    private boolean bPressed = false;
    private boolean bPressedLast = false;
    public CoreGamepad(Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    // An interesting thing could be instead of having the update method update the pressedLast
    // variables, we could update them inside of the getter method. This could cause other problems
    // if we ask for the same input in multiple locations. This way the state is only updated once
    // and is GUARANTEED to be the same until the next update call. The special start + a or b
    // could still cause problems if we press b and then after press start, and that logic should
    // be added here, centered around that pressedLast variable.
    public void update() {
        aPressedLast = aPressed;
        aPressed = gamepad.a && !gamepad.start;
        bPressedLast = bPressed;
        bPressed = gamepad.b && !gamepad.start;
    }

    // A and B have special info is the start button is also hit, therefore we don't want to say
    // that is was pressed at all, including in the pressedLast variable since that could trigger
    // the released call.
    public boolean getAButton() {
        return aPressed;
    }

    // If the button was pressed in the last frame, then we've held it down. Makes sense so far?
    // However, if we've pressed it now but it wasn't pressed last update, we can now confidently
    // say that we;ve now pressed it, not just held it.
    public boolean getADown() {
        return aPressed && !aPressedLast;
    }

    // The same as the above but the opposite. Now if we're not currently pressing it, but last
    // update it was pressed, we can now say that we released it during this update.
    public boolean getAReleased() {
        return !aPressed && aPressedLast;
    }

    public boolean getBButton() {
        return aPressed;
    }

    // If the button was pressed in the last frame, then we've held it down. Makes sense so far?
    // However, if we've pressed it now but it wasn't pressed last update, we can now confidently
    // say that we;ve now pressed it, not just held it.
    public boolean getBDown() {
        return aPressed && !aPressedLast;
    }

    // The same as the above but the opposite. Now if we're not currently pressing it, but last
    // update it was pressed, we can now say that we released it during this update.
    public boolean getBReleased() {
        return !aPressed && aPressedLast;
    }
}
