package frc.team5333.stronghold.core.commands

import frc.team5333.stronghold.core.strategy.StrategyAngleAlignRough
import frc.team5333.stronghold.core.strategy.StrategyController
import jaci.openrio.toast.core.command.AbstractCommand
import jaci.openrio.toast.core.command.IHelpable
import kotlin.collections.first
import kotlin.text.toDouble
import kotlin.text.toInt

class RoughAlignCommand : AbstractCommand(), IHelpable {
    override fun getHelp(): String? = "Performs a Rough Alignment with the specified angle"

    override fun getCommandName(): String? = "rough"

    override fun invokeCommand(argLength: Int, args: Array<out String>?, command: String?) {
        var angleS = args!![0]
        var angle = angleS.toDouble()

        StrategyController.INSTANCE.setStrategy(StrategyAngleAlignRough(angle))
    }
}