package frc.team5333.stronghold.auto.modes

import frc.team5333.stronghold.auto.AutoMode
import frc.team5333.stronghold.auto.Autonomous
import frc.team5333.stronghold.core.control.ControlLease
import frc.team5333.stronghold.core.strategy.Strategy
import frc.team5333.stronghold.core.strategy.StrategyShoot
import frc.team5333.stronghold.core.strategy.StrategyVisionAlign
import frc.team5333.stronghold.core.systems.DriveSystem
import frc.team5333.stronghold.core.systems.Systems

class StrategyForward() : Strategy() {
    var startTime: Long = 0L
    var runTime: Long = 0L
    var maxthrottle: Double = 0.0
    var speedupTime: Long = 0L
    var slowdownTime: Long = 0L

    lateinit var lease: ControlLease.Lease<DriveSystem>

    override fun getName(): String = "Forward"

    override fun onEnable() {
        startTime = System.currentTimeMillis()
        maxthrottle = Autonomous.config.getDouble("forward.maxthrottle", 0.9)
        speedupTime = Autonomous.config.getLong("forward.speeduptime", 2000)
        runTime = Autonomous.config.getLong("forward.flattime", 1500)
        slowdownTime = Autonomous.config.getLong("forward.slowtime", 500)

        lease = Systems.drive.LEASE.acquire(ControlLease.Priority.HIGH)
    }

    override fun tick() {
        var throttle = maxthrottle
        var elapsed = System.currentTimeMillis() - startTime

        if (elapsed < speedupTime) {
            throttle = (elapsed.toDouble() / speedupTime) * maxthrottle
        } else if (elapsed < (speedupTime + runTime)) {
            throttle = maxthrottle
        } else {
            throttle = (1.0 - ((elapsed.toDouble() - (speedupTime + runTime)) / slowdownTime)) * maxthrottle
        }

        if (throttle < 0.0) throttle = 0.0

        lease.use {
            it.drive(throttle, throttle)
        }
    }

    override fun isOperatorControl(): Boolean = false

    override fun isComplete(): Boolean {
        var elapsed = System.currentTimeMillis() - startTime
        return elapsed > (speedupTime + runTime + slowdownTime)
    }
}
