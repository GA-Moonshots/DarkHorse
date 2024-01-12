package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.config.Config;

public class CoreGamepad {
    private Gamepad gamepad;

    private boolean aPressed = false;
    private boolean aPressedLast = false;
    private boolean bPressed = false;
    private boolean bPressedLast = false;
    private boolean xPressed = false;
    private boolean xPressedLast = false;
    private boolean yPressed = false;
    private boolean yPressedLast = false;
    private boolean leftPressed = false;
    private boolean leftPressedLast = false;
    private boolean rightPressed = false;
    private boolean rightPressedLast = false;
    public CoreGamepad(Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    // An interesting thing could be instead of having the update method update the pressedLast
    // variables, we could update them inside of the getter method. This could cause other problems
    // if we ask for the same input in multiple locations. This way the state is only updated once
    // and is GUARANTEED to be the same until the next update call. The special start + a or b
    // could still cause problems if we press b and then after press start, and that logic should
    // be added here, centered around that pressedLast variable. We can also add logic for
    // restricting accidental presses in here, or potentially add builtin double press / hold logic.
    public void update() {
        aPressedLast = aPressed;
        aPressed = gamepad.a && !gamepad.start;
        bPressedLast = bPressed;
        bPressed = gamepad.b && !gamepad.start;
        xPressedLast = xPressed;
        xPressed = gamepad.x;
        yPressedLast = yPressed;
        yPressed = gamepad.y;
        leftPressedLast = leftPressed;
        leftPressed = gamepad.left_bumper;
        rightPressedLast = rightPressed;
        rightPressed = gamepad.right_bumper;
    }

    // -----------------
    // BUTTONS
    // -----------------

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

    // For the rest of these, I expect you've read the above. I wish there was an easier way to
    // autogen this file so I don't have to copy and paste it so many times
    public boolean getBButton() {
        return bPressed;
    }
    public boolean getBDown() {
        return bPressed && !bPressedLast;
    }
    public boolean getBReleased() {
        return !bPressed && bPressedLast;
    }
    public boolean getXButton() {
        return xPressed;
    }
    public boolean getXDown() {
        return xPressed && !xPressedLast;
    }
    public boolean getXReleased() {
        return !xPressed && xPressedLast;
    }
    public boolean getYButton() {
        return yPressed;
    }
    public boolean getYDown() {
        return yPressed && !yPressedLast;
    }
    public boolean getYReleased() {
        return !yPressed && yPressedLast;
    }
    public boolean getLeftButton() {
        return leftPressed;
    }
    public boolean getLeftButtonDown() {
        return leftPressed && !leftPressedLast;
    }
    public boolean getLeftButtonReleased() {
        return !leftPressed && leftPressedLast;
    }

    public boolean getRightButton() {
        return rightPressed;
    }
    public boolean getRightButtonDown() {
        return rightPressed && !rightPressedLast;
    }
    public boolean getRightButtonReleased() {
        return !rightPressed && rightPressedLast;
    }

    // -----------------
    // AXES
    // -----------------

    // Potentially deadzone clip in these methods? yeah sure
    public double getLeftStickX() {
        // I'm using v for value here since I'm lazy
        double v = gamepad.left_stick_x;

        if(Math.abs(v) <= Config.INPUT_THRESHOLD) {
            return 0;
        }
        return v;
    }

    // The Y axis is inverted in the SDK, so we'll preform the reversal here.
    public double getLeftStickY() {
        double v = -gamepad.left_stick_y;

        if(Math.abs(v) <= Config.INPUT_THRESHOLD) {
            return 0;
        }
        return v;
    }

    public double getRightStickX() {
        double v = gamepad.right_stick_x;

        if(Math.abs(v) <= Config.INPUT_THRESHOLD) {
            return 0;
        }
        return v;
    }

    public double getRightStickY() {
        double v = gamepad.right_stick_y;

        if(Math.abs(v) <= Config.INPUT_THRESHOLD) {
            return 0;
        }
        return v;
    }

    // Interesting design choice: I could put the triggers on their own axes, or I could put them
    // on a single one. I don't think it matters, and it is probably best to put them on separate
    // ones due to flexibility reasons. Something to discuss later.
    public double getLeftTrigger() {
        double v = gamepad.left_trigger;

        // These are always positive, so we can simplify a bit
        if(v <= Config.INPUT_THRESHOLD) {
            return 0;
        }
        return v;
    }

    public double getRightTrigger() {
        double v = gamepad.right_trigger;

        // These are always positive, so we can simplify a bit
        if(v <= Config.INPUT_THRESHOLD) {
            return 0;
        }
        return v;
    }

    // I'm putting the DPad on an axes pair since it is, in effect, a discrete version of a
    // joystick. Again, something to be discussed.
    public double getDPadHorizontal() {
        double v = gamepad.dpad_left ? 1 : 0;
        double i = gamepad.dpad_right ? 1: 0;

        // This is never less than our inupt threshold in magnitude, so let's find out which way
        // it's pointing
        return i - v;
    }

    public double getDPadVertical() {
        double v = gamepad.dpad_down ? 1 : 0;
        double i = gamepad.dpad_up ? 1: 0;

        // This is never less than our inupt threshold in magnitude, so let's find out which way
        // it's pointing
        return i - v;
    }
}
