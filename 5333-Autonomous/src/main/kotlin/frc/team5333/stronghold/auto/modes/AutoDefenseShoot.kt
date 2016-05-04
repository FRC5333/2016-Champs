package frc.team5333.stronghold.auto.modes

import frc.team5333.stronghold.auto.AutoMode
import frc.team5333.stronghold.core.commands.RoughAlignCommand
import frc.team5333.stronghold.core.data.PlacementInfo
import frc.team5333.stronghold.core.strategy.StrategyAngleAlignRough
import frc.team5333.stronghold.core.strategy.StrategyShoot
import frc.team5333.stronghold.core.strategy.StrategyVisionAlign

class AutoDefenseShoot : AutoMode {
    override fun getName(): String = "Defense Shoot"

    override fun init() {}

    override fun start() {
        var strat = StrategyForward()
        strat.then(StrategyAngleAlignRough(PlacementInfo.getDesiredAlign()))
        strat.then(StrategyVisionAlign())
        strat.then(StrategyShoot())

        AutoMode.strategy(strat)
    }

    override fun tick() {}

}
