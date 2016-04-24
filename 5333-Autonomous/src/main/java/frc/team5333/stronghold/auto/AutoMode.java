package frc.team5333.stronghold.auto;

import frc.team5333.stronghold.core.strategy.Strategy;
import frc.team5333.stronghold.core.strategy.StrategyController;

public interface AutoMode {

    public static void strategy(Strategy strat) {
        StrategyController.INSTANCE.setStrategy(strat);
    }

    public String getName();

    public void init();

    public void start();

    public void tick();

}
