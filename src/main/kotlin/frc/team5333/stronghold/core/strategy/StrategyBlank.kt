package frc.team5333.stronghold.core.strategy

import frc.team5333.stronghold.core.control.IO

class StrategyBlank : Strategy() {

    override fun getName(): String = "[ BLANK ]"

    override fun tick() {
        IO.setBottomFlywheel(0.0)
        IO.setTopFlywheel(0.0)
        IO.setIntake(0.0)
        IO.setLeftMotors(0.0)
        IO.setRightMotors(0.0)
    }

    override fun isOperatorControl(): Boolean = false
}