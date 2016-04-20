package frc.team5333.stronghold.core.control

import edu.wpi.first.wpilibj.PIDSource
import edu.wpi.first.wpilibj.PIDSourceType

class GyroPID : PIDSource {
    var pidSource: PIDSourceType? = PIDSourceType.kDisplacement

    override fun getPIDSourceType(): PIDSourceType? = pidSource

    override fun setPIDSourceType(pidSource: PIDSourceType?) {
        this.pidSource = pidSource
    }

    override fun pidGet(): Double = IO.imuAlignAngle()
}