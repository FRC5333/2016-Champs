package frc.team5333.stronghold.core.strategy

import frc.team5333.stronghold.core.control.ControlLease
import frc.team5333.stronghold.core.systems.DriveSystem
import frc.team5333.stronghold.core.systems.Systems

class StrategyOperator : Strategy() {

    lateinit var lease_drive: ControlLease.Lease<DriveSystem>

    override fun getName(): String = "Operator"

    override fun isOperatorControl(): Boolean = true

    override fun tick() {
        lease_drive.use { it.drive() }
    }

    override fun onEnable() {
        super.onEnable()
        lease_drive = Systems.drive.LEASE.acquire(ControlLease.Priority.NORMAL)
    }

    override fun onDisable() {
        super.onDisable()
        lease_drive.release()
    }
}