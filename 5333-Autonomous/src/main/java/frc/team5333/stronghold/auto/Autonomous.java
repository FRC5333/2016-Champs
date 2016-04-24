package frc.team5333.stronghold.auto;

import frc.team5333.stronghold.auto.command.CommandListAuto;
import frc.team5333.stronghold.auto.modes.AutoForward;
import frc.team5333.stronghold.auto.modes.AutoForwardShoot;
import frc.team5333.stronghold.auto.modes.AutoSpyboxShoot;
import jaci.openrio.toast.core.command.CommandBus;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.module.ModuleConfig;

import java.util.HashMap;

public class Autonomous extends IterativeModule {

    public static ModuleConfig config;
    public static Logger log;
    public static HashMap<String, AutoMode> autoModes;
    public static AutoMode chosenMode;

    @Override
    public String getModuleName() {
        return "5333-Autonomous";
    }

    @Override
    public String getModuleVersion() {
        return "1.0.0-champs";
    }

    @Override
    public void robotInit() {
        config = new ModuleConfig("5333-Autonomous");
        log = new Logger("5333-Autonomous", Logger.ATTR_DEFAULT);
        autoModes = new HashMap<>();

        registerModes();
        autoModes.forEach((name, mode) -> mode.init());
        config.getString("auto.mode", "forward");

        CommandBus.registerCommand(new CommandListAuto());
    }

    @Override
    public void autonomousInit() {
        String mode = config.getString("auto.mode", "forward");
        if (!mode.equalsIgnoreCase("none") && autoModes.containsKey(mode)) {
            chosenMode = autoModes.get(mode);

            log.info("Auto Mode: " + chosenMode.getName());
            chosenMode.start();
        } else {
            log.warn("No Auto Mode selected!");
        }
    }

    @Override
    public void autonomousPeriodic() {
        if (chosenMode != null) {
            chosenMode.tick();
        }
    }

    public static void registerModes() {
        autoModes.put("forward", new AutoForward());
        autoModes.put("forward_shoot", new AutoForwardShoot());
        autoModes.put("spybox_shoot", new AutoSpyboxShoot());
    }


}
