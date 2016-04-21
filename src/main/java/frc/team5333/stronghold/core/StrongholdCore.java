package frc.team5333.stronghold.core;

import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team5333.stronghold.core.configs.Configurations;
import frc.team5333.stronghold.core.control.*;
import frc.team5333.stronghold.core.data.MatchInfo;
import frc.team5333.stronghold.core.strategy.StrategyController;
import frc.team5333.stronghold.core.strategy.StrategyOperator;
import frc.team5333.stronghold.core.systems.Systems;
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
        Operator.init();
        Systems.init();

        ControlLoopManager.start();

        MatchInfo.load();
        VisionNetwork.INSTANCE.start();

        StateTracker.addTicker((s) -> { Scheduler.getInstance().run(); });

        TransientControls.init();
    }

    @Override
    public void autonomousInit() {
        IO.maybeIMU(ADIS16448_IMU::calibrateNow);
    }

    @Override
    public void autonomousPeriodic() {
        StrategyController.INSTANCE.tickSlow();
        Systems.tick();
    }

    @Override
    public void teleopInit() {
        StrategyController.INSTANCE.setStrategy(new StrategyOperator());
    }

    @Override
    public void teleopPeriodic() {
        StrategyController.INSTANCE.tickSlow();
        Systems.tick();
    }
}
