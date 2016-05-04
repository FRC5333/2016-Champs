package frc.team5333.stronghold.core.commands

import frc.team5333.stronghold.core.StrongholdCore
import jaci.openrio.module.cereal.Cereal
import jaci.openrio.toast.core.command.AbstractCommand
import jaci.openrio.toast.core.command.IHelpable
import kotlin.collections.forEach

class ListSerialPortsCommand : AbstractCommand(), IHelpable {
    override fun getHelp(): String? = "Returns a list of all the available Serial Ports on the device"

    override fun getCommandName(): String? = "portlist"

    override fun invokeCommand(argLength: Int, args: Array<out String>?, command: String?) {
        var list = Cereal.getAvailablePorts()
        StrongholdCore.logger.info("Available Serial Ports: ")
        if (list != null) {
            list.forEach {
                StrongholdCore.logger.info(it)
            }
        } else {
            StrongholdCore.logger.info("No Ports Available!")
        }
    }
}