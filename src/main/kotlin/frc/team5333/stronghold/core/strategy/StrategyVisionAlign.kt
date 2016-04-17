package frc.team5333.stronghold.core.strategy

import frc.team5333.stronghold.core.StrongholdCore
import frc.team5333.stronghold.core.configs.ConfigMap
import frc.team5333.stronghold.core.control.ControlLease
import frc.team5333.stronghold.core.control.IO
import frc.team5333.stronghold.core.control.InternalPID
import frc.team5333.stronghold.core.systems.DriveSystem
import frc.team5333.stronghold.core.systems.Systems
import frc.team5333.stronghold.core.vision.VisionFrame
import frc.team5333.stronghold.core.vision.VisionNetwork
import kotlin.collections.size

class StrategyVisionAlign : Strategy() {

    var started = false
    var start_time = 0L

    lateinit var internal_pid: InternalPID
    lateinit var lease: ControlLease.Lease<DriveSystem>

    override fun getName(): String = "Vision Align"

    override fun onEnable() {
        super.onEnable()
        internal_pid = InternalPID( ConfigMap.Control.Align.p,
                                    ConfigMap.Control.Align.i,
                                    ConfigMap.Control.Align.d)
        lease = Systems.drive.LEASE.acquire(ControlLease.Priority.HIGH)
        var frame = VisionNetwork.INSTANCE.activeFrame
        if (frame.size != 0) {
            var image = frame.getSelectedRect().horizontalAngle
            var imu = Math.toRadians(IO.imuAlignAngle())

            var sp = image + imu
            internal_pid.target = sp
        }
    }

    override fun tick() {
        if (!started) start_time = System.currentTimeMillis()
        started = true

        var frame = VisionNetwork.INSTANCE.activeFrame
        if (frame.size != 0) {
            var image = frame.getSelectedRect().horizontalAngle
            var imu = Math.toRadians(IO.imuAlignAngle())        // TODO: Setpoint update on vision frame received. Run in a faster control loop

//            var sp = image + imu
//            internal_pid.target = sp

            var output = internal_pid.update(imu)
            lease.use {
                it.drive(-output, output)
            }
        } else {
            internal_pid.last_error = 0.0
            StrongholdCore.logger.info("Could not vision align: No Frame!")
        }
    }

    override fun isOperatorControl(): Boolean = false

    override fun isComplete(): Boolean = started &&
            (internal_pid.hasSettled(ConfigMap.Control.Align.acceptable_error)
                || System.currentTimeMillis() - start_time > ConfigMap.Control.Align.align_timeout)

}