package frc.team5333.stronghold.core.systems

import frc.team5333.stronghold.core.control.LogitechJoy
import frc.team5333.stronghold.core.control.Operator
import java.util.*

class ControlSystem {

    enum class DriveMode {
        LEFT_ONLY, RIGHT_ONLY, BOTH
    }

    fun driveMode(): DriveMode {
        val lTrig = Operator.getLeftJoystick().trigger
        val rTrig = Operator.getRightJoystick().trigger

        if (lTrig && rTrig) return DriveMode.BOTH
        else if (lTrig) return DriveMode.LEFT_ONLY
        else return DriveMode.RIGHT_ONLY
    }

    fun tick() { }

    fun driveJoystick(): Optional<LogitechJoy> {
        var dm = driveMode()
        if (dm == DriveMode.LEFT_ONLY) return Optional.of(Operator.getLeftJoystick())
        if (dm == DriveMode.RIGHT_ONLY) return Optional.of(Operator.getRightJoystick())
        return Optional.empty()
    }

    fun shootJoystick(): Optional<LogitechJoy> {
        var dm = driveMode()
        if (dm == DriveMode.LEFT_ONLY) return Optional.of(Operator.getRightJoystick())
        if (dm == DriveMode.RIGHT_ONLY) return Optional.of(Operator.getLeftJoystick())
        return Optional.empty()
    }

    fun driveButton(buttonID: Int): Boolean {
        var j = driveJoystick()
        if (j.isPresent) return j.get().getRawButton(buttonID)
        return Operator.getLeftJoystick().getRawButton(buttonID)
    }

    fun shootButton(buttonID: Int): Boolean {
        var j = shootJoystick()
        if (j.isPresent) return j.get().getRawButton(buttonID)
        return false
    }

}