package org.firstinspires.ftc.teamcode.internal.math;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

public class Vector {
    /**
     * Rotates a 2d vector via an angle to the vector with the same magnitude and an (r, theta)
     * represented by (r, theta + dTheta) by a rotation matrix.
     * @param pose The Vector2D to rotate and the angle to rotate by
     * @return A Vector2D representing the new transformed vector
     */
    public static Vector2d rotateVector(Pose2d pose) {
        double angle = pose.heading.toDouble();
        double x = pose.position.y * Math.sin(angle) + pose.position.x * Math.cos(angle);
        double y = pose.position.y * Math.cos(angle) - pose.position.x * Math.sin(angle);

        return new Vector2d(x, y);
    }

    /**
     * Normalizes a vector to length 1
     * @param vector The vector to be normalized
     * @return A vector with length 1 with the same theta as the input
     */
    public static Vector2d normalizeVector(Vector2d vector) {
        double magnitude = Math.hypot(vector.x, vector.y);
        return new Vector2d(vector.x / magnitude, vector.y / magnitude);
    }
}
