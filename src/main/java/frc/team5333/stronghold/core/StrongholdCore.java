package frc.team5333.stronghold.core;

import frc.team5333.stronghold.core.configs.ConfigMap;
import frc.team5333.stronghold.core.configs.Configurations;
import frc.team5333.stronghold.core.configs.JoyMap;
import frc.team5333.stronghold.core.data.MatchInfo;
import frc.team5333.stronghold.core.vision.VisionNetwork;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.module.ModuleConfig;

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
        MatchInfo.load();
        VisionNetwork.INSTANCE.start();
    }

    @Override
    public void teleopInit() {
    }
}
