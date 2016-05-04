package frc.team5333.stronghold.auto.command;

import frc.team5333.stronghold.auto.Autonomous;
import jaci.openrio.toast.core.command.AbstractCommand;
import jaci.openrio.toast.core.command.IHelpable;

public class CommandListAuto extends AbstractCommand implements IHelpable {

    @Override
    public String getCommandName() {
        return "auto";
    }

    @Override
    public String getHelp() {
        return "Prints a list of available Autonomous Modes";
    }

    @Override
    public void invokeCommand(int argLength, String[] args, String command) {
        Autonomous.autoModes.forEach((name, mode) -> {
            Autonomous.log.info(String.format("%s -> %s", name, mode.getName()));
        });
    }
}
