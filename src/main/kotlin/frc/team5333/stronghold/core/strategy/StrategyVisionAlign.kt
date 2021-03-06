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

class StrategyVisionAlign : Strategy() {

    var started = false
    var start_time = 0L

    lateinit var pid: PIDController
    var pidOutput = 0.0
    lateinit var lease: ControlLease.Lease<DriveSystem>

    override fun getName(): String = "Vision Align"

    override fun onEnable() {
        super.onEnable()
        pid = PIDController(ConfigMap.Control.Align.p,
                            ConfigMap.Control.Align.i,
                            ConfigMap.Control.Align.d,
                            ConfigMap.Control.Align.f,
                            GyroPID(),
                            PIDOutput {
                                pidOutput = it
                            })
        pid.setInputRange(-180.0, 180.0)
        pid.setOutputRange(-1.0, 1.0)
        pid.setAbsoluteTolerance(ConfigMap.Control.Align.acceptable_error)
        pid.setContinuous(true)
        pid.enable()

        lease = Systems.drive.LEASE.acquire(ControlLease.Priority.HIGH)

        var frame = VisionNetwork.INSTANCE.activeFrame
        if (frame.size == 0) return
        var image = Math.toDegrees(frame.getSelectedRect().horizontalAngle)
        var imu = Util.boundHalfDegrees(IO.imuAlignAngle())

        pid.setpoint = image + imu
    }

    override fun onDisable() {
        pid.disable()
    }

    override fun tick() { }

    override fun tickFast() {
        if (!started) start_time = System.currentTimeMillis()
        started = true

        lease.use {
            it.drive(-pidOutput, pidOutput)
        }
    }

    override fun isOperatorControl(): Boolean = false

    override fun isComplete(): Boolean = started &&
            (System.currentTimeMillis() - start_time > ConfigMap.Control.Align.align_timeout)

}