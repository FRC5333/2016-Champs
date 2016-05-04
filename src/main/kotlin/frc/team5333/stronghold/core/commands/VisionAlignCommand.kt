package frc.team5333.stronghold.core.commands

import frc.team5333.stronghold.core.strategy.StrategyAngleAlignRough
import frc.team5333.stronghold.core.strategy.StrategyController
import frc.team5333.stronghold.core.strategy.StrategyVisionAlign
import jaci.openrio.toast.core.command.AbstractCommand
import jaci.openrio.toast.core.command.IHelpable
import kotlin.collections.first
import kotlin.text.toDouble
import kotlin.text.toInt

class VisionAlignCommand : AbstractCommand(), IHelpable {
    override fun getHelp(): String? = "Aligns the robot with a visible vision target"

    override fun getCommandName(): String? = "vision"

    override fun invokeCommand(argLength: Int, args: Array<out String>?, command: String?) {
        StrategyController.INSTANCE.setStrategy(StrategyVisionAlign())
    }
}