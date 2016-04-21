package frc.team5333.stronghold.auto.modes

import frc.team5333.stronghold.auto.AutoMode
import frc.team5333.stronghold.core.strategy.StrategyShoot
import frc.team5333.stronghold.core.strategy.StrategyVisionAlign

class AutoSpyboxShoot : AutoMode {

    override fun getName(): String = "Spybox Shoot"

    override fun init() {}

    override fun start() {
        var strat = StrategyVisionAlign()
        strat.then(StrategyShoot())

        AutoMode.strategy(strat)
    }

}