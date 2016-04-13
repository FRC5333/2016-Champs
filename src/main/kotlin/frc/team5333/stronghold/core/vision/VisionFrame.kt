package frc.team5333.stronghold.core.vision

import java.util.*

class VisionFrame(var selected: Int): ArrayList<VisionRectangle>() {

    fun getSelectedRect(): VisionRectangle = this[selected]

}