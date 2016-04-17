package frc.team5333.stronghold.core.vision

import frc.team5333.stronghold.core.configs.ConfigMap
import jaci.openrio.toast.lib.math.MathHelper

class VisionRectangle {

    var x: Double = 0.0
    var y: Double = 0.0
    var width: Double = 0.0
    var height: Double = 0.0
    var horizontalAngle: Double = 0.0

    fun area(): Double = width * height

    fun offsetCenterX(): Double {
        var centerRectX = x + (width / 2.0)
        var centerFrame = 640.0 / 2.0
        return (centerFrame - centerRectX) / centerFrame
    }

    fun finalize() {
        horizontalAngle = Math.atan(
                (x + (width / 2.0) - 640.0 / 2.0)
                /
                (0.5 * 640.0 / Math.tan(Math.toRadians(ConfigMap.Vision.Camera.horizontal_fov) / 2))
        )
    }

    override fun hashCode(): Int{
        var result = x.hashCode()
        result += 31 * result + y.hashCode()
        result += 31 * result + width.hashCode()
        result += 31 * result + height.hashCode()
        return result
    }

}