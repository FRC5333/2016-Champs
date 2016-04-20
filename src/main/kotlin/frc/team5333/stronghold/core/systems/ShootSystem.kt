package frc.team5333.stronghold.core.systems

import edu.wpi.first.wpilibj.Joystick
import frc.team5333.stronghold.core.configs.ConfigMap
import frc.team5333.stronghold.core.control.ControlLease
import frc.team5333.stronghold.core.control.IO
import frc.team5333.stronghold.core.vision.VisionNetwork

class ShootSystem {
    var LEASE = ControlLease(this)

    lateinit private var lease_instance: ControlLease.Lease<ShootSystem>

    fun init() {
        lease_instance = LEASE.acquire(ControlLease.Priority.HIGH)
    }

    fun tick() {
        runTeleop()
    }

    fun setTop(v: Double) = IO.setTopFlywheel(v)
    fun setBottom(v: Double) = IO.setBottomFlywheel(v)
    fun setIntake(v: Double) = IO.setIntake(v)
    fun setAll(v: Double) {
        setTop(v)
        setBottom(v)
        setIntake(v)
    }

    fun withinDeadzone(axis: Double): Boolean = Math.abs(axis) <= ConfigMap.Control.Shoot.joystick_override_deadzone

    fun runTeleop() {
        var joystick = Systems.control.shootJoystick()
        if (joystick.isPresent) {
            var joy = joystick.get()
            var slider = joy.slider > 0.5

            var top = 0.0
            var bottom = 0.0
            var intake = 0.0

            if (!withinDeadzone(joy.y)) {
                // Manual
                top = joy.y
                bottom = joy.y
            } else if (!VisionNetwork.INSTANCE.activeFrame.isEmpty() && slider) {
                // Passive
                top = ConfigMap.Control.Shoot.spinup_flywheel_throttle
                bottom = ConfigMap.Control.Shoot.spinup_flywheel_throttle
            }

            if (!withinDeadzone(joy.x)) {
                // Manual
                intake = joy.x
            } else if (!VisionNetwork.INSTANCE.activeFrame.isEmpty() && slider) {
                // Passive
                intake = ConfigMap.Control.Shoot.intake_hold_throttle
            }

            lease_instance.use {
                it.setTop(top)
                it.setBottom(bottom)
                it.setIntake(intake)
            }
        }
    }

    fun getFlywheelPairs(): Pair<Double, Double> {
        if (Systems.control.shootJoystick().isPresent)
            return calc(Systems.control.shootJoystick().get())
        return Pair(0.0, 0.0)
    }

    internal fun calc(joy: Joystick): Pair<Double, Double> = Pair(sq(-joy.y), sq(joy.twist))

    internal fun sq(value: Double): Double {
        if (value >= 0.0)
            return value * value
        else
            return -(value * value)
    }

}