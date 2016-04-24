package frc.team5333.stronghold.auto.modes

import frc.team5333.stronghold.auto.AutoMode

class AutoForward : AutoMode {

    override fun getName(): String = "Forward"

    override fun init() {  }

    override fun start() {
        AutoMode.strategy(StrategyForward())
    }

    override fun tick() { }
}