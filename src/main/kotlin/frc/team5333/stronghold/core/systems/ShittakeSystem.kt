package frc.team5333.stronghold.core.systems

import frc.team5333.stronghold.core.control.ControlLease
import frc.team5333.stronghold.core.control.IO

class ShittakeSystem {

    var LEASE = ControlLease(this)

    enum class Actuation {
        RAISED, LOWERED, UNKNOWN;

        fun opposite(): Actuation {
            if (this == RAISED) {
                return LOWERED
            } else {
                // Stow away by default, even if unknown
                return RAISED
            }
        }
    }

    lateinit private var lease_instance: ControlLease.Lease<ShittakeSystem>

    fun init() {
        lease_instance = LEASE.acquire(ControlLease.Priority.NORMAL)
    }

    fun setActuation(state: Actuation) {
        if (state == Actuation.LOWERED) {
            IO.shittake_lower.set(true)
            IO.shittake_raise.set(false)
        } else {        // Stow away by default (i.e. RAISED or UNKNOWN)
            IO.shittake_lower.set(false)
            IO.shittake_raise.set(true)
        }
    }

    fun getActuation(): Actuation {
        var r = IO.shittake_raise.get()
        var l = IO.shittake_lower.get()

        if (r && !l) {
            return Actuation.RAISED
        } else if (l && !r) {
            return Actuation.LOWERED
        } else {
            return Actuation.UNKNOWN
        }
    }

    fun setShittake(value: Double) = IO.setShittake(value)

    fun tick() {
        var joystick = Systems.control.shootJoystick()

        var shittake = 0.0

        if (joystick.isPresent) {
            var joy = joystick.get()

            if (joy.getRawButton(3))        shittake = 1.0
            else if (joy.getRawButton(4))   shittake = -1.0

            if (joy.pov == 0 || joy.pov == 360) {
                // Raise
                setActuation(Actuation.RAISED)
            } else if (joy.pov == 180) {
                // Lower
                setActuation(Actuation.LOWERED)
            }
        }

        lease_instance.use {
            setShittake(shittake)
        }
    }

}