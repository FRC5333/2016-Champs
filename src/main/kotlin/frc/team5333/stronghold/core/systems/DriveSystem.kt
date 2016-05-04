package frc.team5333.stronghold.core.systems

import frc.team5333.stronghold.core.configs.ConfigMap
import frc.team5333.stronghold.core.control.ControlLease
import frc.team5333.stronghold.core.control.IO
import frc.team5333.stronghold.core.control.Operator

class DriveSystem {
    val LEASE = ControlLease(this)

    // TODO: Use button for "FineTuned" turning (for alignment)

    var throttle_scale = 1.0

    fun tick() { }

    fun drive() {
        var pairs = getDrivePairs()
        drive(pairs.first, pairs.second)
    }

    fun drive(l: Double, r: Double) {
        IO.setLeftMotors(l)
        IO.setRightMotors(-r)
    }

    fun leftEncoder(): Double = IO.getLeftEncoderPosition()
    fun rightEncoder(): Double = -IO.getRightEncoderPosition()

    fun getDrivePairs(): Pair<Double, Double> {
        var lJoy = Operator.getLeftJoystick()
        var rJoy = Operator.getRightJoystick()
        var mode = Systems.control.driveMode()

        if (mode == ControlSystem.DriveMode.BOTH)
            return Pair(sq(-rJoy.y), sq(-lJoy.y))
        else if (mode == ControlSystem.DriveMode.LEFT_ONLY)
            return jaciDrive3(sq(-lJoy.y), sq(lJoy.x), lJoy.twist, lJoy.bumper)
        else if (mode == ControlSystem.DriveMode.RIGHT_ONLY)
            return jaciDrive3(sq(-rJoy.y), sq(rJoy.x), rJoy.twist, rJoy.bumper)
        return Pair(0.0, 0.0)
    }

    internal fun jaciDrive3(throttle: Double, rotate: Double, twist: Double, fine: Boolean): Pair<Double, Double> {
        var scale = 1.0
        if (fine) scale = ConfigMap.Control.fine_control_throttle_speed

        var throttleCoefficient = ConfigMap.Control.turn_throttle_coefficient
        var adjustedRotation = (rotate * scale * throttleCoefficient) + (rotate * scale * twist * twist * (1 - throttleCoefficient))
        var adjustedThrottle = throttle * scale

        var left = 0.0
        var right = 0.0
        if (adjustedThrottle > 0.0) {
            if (adjustedRotation > 0.0) {
                left = adjustedThrottle - adjustedRotation
                right = Math.max(adjustedThrottle, adjustedRotation);
            } else {
                left = Math.max(adjustedThrottle, -adjustedRotation);
                right = adjustedThrottle + adjustedRotation;
            }
        } else {
            if (adjustedRotation > 0.0) {
                left = -Math.max(-adjustedThrottle, adjustedRotation);
                right = adjustedThrottle + adjustedRotation
            } else {
                left = adjustedThrottle - adjustedRotation;
                right = -Math.max(-adjustedThrottle, -adjustedRotation);
            }
        }
        return Pair(left * throttle_scale, right * throttle_scale)
    }

    /**
     * Square value while maintaining the sign. This is used as a filter on the joystick inputs to make it easier for
     * the driver to control.
     */
    internal fun sq(value: Double): Double {
        if (value >= 0.0)
            return value * value
        else
            return -(value * value)
    }

}