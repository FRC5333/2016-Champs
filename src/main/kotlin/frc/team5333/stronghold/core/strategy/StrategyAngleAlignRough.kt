package frc.team5333.stronghold.core.strategy

import edu.wpi.first.wpilibj.PIDController
import edu.wpi.first.wpilibj.PIDOutput
import frc.team5333.stronghold.core.Util
import frc.team5333.stronghold.core.configs.ConfigMap
import frc.team5333.stronghold.core.control.ControlLease
import frc.team5333.stronghold.core.control.GyroPID
import frc.team5333.stronghold.core.control.IO
import frc.team5333.stronghold.core.systems.DriveSystem
import frc.team5333.stronghold.core.systems.Systems
import frc.team5333.stronghold.core.vision.VisionNetwork

class StrategyAngleAlignRough(var angle: Double) : Strategy() {

    var started = false
    var done = false
    var start_time = 0L

    lateinit var lease: ControlLease.Lease<DriveSystem>

    override fun getName(): String = "Angle Align [ROUGH]"

    override fun onEnable() {
        super.onEnable()
        lease = Systems.drive.LEASE.acquire(ControlLease.Priority.HIGH)
    }

    override fun onDisable() {
    }

    override fun tick() { }

    override fun tickFast() {
        if (!started) start_time = System.currentTimeMillis()
        started = true

        var output = 0.0
        var imu = Util.boundHalfDegrees(IO.imuAlignAngle())
        var delta = Util.boundHalfDegrees(angle - imu)

        if (delta > ConfigMap.Control.Align.rough_tolerance) {
            output = ConfigMap.Control.Align.rough_speed
        } else if (delta < ConfigMap.Control.Align.rough_tolerance) {
            output = -ConfigMap.Control.Align.rough_speed
        } else {
            output = 0.0
            done = true
        }

        output = output * ConfigMap.Control.Align.rough_output_modifier

        lease.use {
            it.drive(-output, output)
        }
    }

    override fun isOperatorControl(): Boolean = false

    override fun isComplete(): Boolean = started && (done ||
            (System.currentTimeMillis() - start_time > ConfigMap.Control.Align.rough_timeout))

}