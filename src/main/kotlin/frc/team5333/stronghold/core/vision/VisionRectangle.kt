package frc.team5333.stronghold.core.vision

import jaci.openrio.toast.lib.math.MathHelper

class VisionRectangle {

    var x: Double = 0.0
    var y: Double = 0.0
    var width: Double = 0.0
    var height: Double = 0.0

    fun area(): Double = width * height

    fun offsetCenterX(): Double {
        var centerRectX = x + (width / 2.0)
        var centerFrame = 640.0 / 2.0
        return (centerFrame - centerRectX) / centerFrame
    }

}