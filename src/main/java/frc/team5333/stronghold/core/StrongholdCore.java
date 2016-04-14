package frc.team5333.stronghold.core;

import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team5333.stronghold.core.configs.Configurations;
import frc.team5333.stronghold.core.data.MatchInfo;
import frc.team5333.stronghold.core.control.IO;
import frc.team5333.stronghold.core.vision.VisionNetwork;
import jaci.openrio.toast.core.StateTracker;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;

public class StrongholdCore extends IterativeModule {

    public static Logger logger;

    @Override
    public String getModuleName() {
        return "5333-StrongholdCore";
    }

    @Override
    public String getModuleVersion() {
        return "1.0.0-champs";
    }

    @Override
    public void robotInit() {
        logger = new Logger(getModuleName(), Logger.ATTR_DEFAULT);
        Configurations.init();
        IO.init();
        MatchInfo.load();
        VisionNetwork.INSTANCE.start();

        StateTracker.addTicker((s) -> { Scheduler.getInstance().run(); });
    }

    @Override
    public void teleopInit() {
    }
}
