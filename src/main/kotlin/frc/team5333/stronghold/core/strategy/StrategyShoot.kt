package frc.team5333.stronghold.core.strategy

import frc.team5333.stronghold.core.configs.ConfigMap
import frc.team5333.stronghold.core.control.ControlLease
import frc.team5333.stronghold.core.systems.ShootSystem
import frc.team5333.stronghold.core.systems.Systems

class StrategyShoot : Strategy() {

    var startTime = 0L
    lateinit var lease: ControlLease.Lease<ShootSystem>

    override fun getName(): String = "Shoot"

    override fun onEnable() {
        startTime = System.currentTimeMillis()
        lease = Systems.shoot.LEASE.acquire(ControlLease.Priority.HIGHEST)
    }

    override fun tick() {
        var elapsed = System.currentTimeMillis() - startTime
        lease.use {
            it.setTop(ConfigMap.Control.Shoot.spinup_flywheel_throttle)
            it.setBottom(ConfigMap.Control.Shoot.spinup_flywheel_throttle)

            if (elapsed > 2000) {
                it.setIntake(ConfigMap.Control.Shoot.intake_fire_throttle)
            } else {
                it.setIntake(ConfigMap.Control.Shoot.intake_hold_throttle)
            }
        }
    }

    override fun isOperatorControl(): Boolean = false

    override fun isComplete(): Boolean {
        var elapsed = System.currentTimeMillis() - startTime
        return elapsed > 1500
    }
}