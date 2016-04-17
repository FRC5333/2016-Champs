package frc.team5333.stronghold.core.vision

import java.util.*

class VisionFrame(var selected: Int): ArrayList<VisionRectangle>() {

    var frameID = 0

    fun getSelectedRect(): VisionRectangle = this[selected]

}