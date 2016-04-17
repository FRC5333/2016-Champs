package frc.team5333.stronghold.core.control;

import edu.wpi.first.wpilibj.Notifier;
import frc.team5333.stronghold.core.StrongholdCore;
import frc.team5333.stronghold.core.configs.ConfigMap;
import frc.team5333.stronghold.core.strategy.StrategyController;

public class ControlLoopManager implements Runnable {

    boolean firstRun = true;

    @Override
    public void run() {
        if (firstRun) {
            Thread.currentThread().setPriority(6);
            firstRun = false;
        }
        StrategyController.INSTANCE.tickFast();
    }

    public static Notifier notifier;

    public static void start() {
        notifier = new Notifier(new ControlLoopManager());
        notifier.startPeriodic(1.0 / ConfigMap.Control.control_loop_freq);
    }

}