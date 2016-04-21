package frc.team5333.stronghold.core.control;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5333.stronghold.core.control.Operator;
import frc.team5333.stronghold.core.strategy.StrategyController;
import frc.team5333.stronghold.core.strategy.StrategyOperator;
import frc.team5333.stronghold.core.strategy.StrategyVisionAlign;
import frc.team5333.stronghold.core.systems.ShittakeSystem;
import frc.team5333.stronghold.core.systems.Systems;

public class Commands {

    public static Command vision_align = command(() -> {
        StrategyController.INSTANCE.setStrategy(new StrategyVisionAlign());
    });

    public static Command operator_override = command(() -> {
        StrategyController.INSTANCE.setStrategy(new StrategyOperator());
    });

    public static Command raise_shittake = command(() -> {
        Systems.shittake.setActuation(ShittakeSystem.Actuation.RAISED);
    });

    public static Command lower_shittake = command(() -> {
        Systems.shittake.setActuation(ShittakeSystem.Actuation.LOWERED);
    });

    public static Command command(Runnable run) {
        return new Command() {
            @Override
            protected void initialize() { }

            @Override
            protected void execute() {
                run.run();
            }

            @Override
            protected boolean isFinished() {
                return true;
            }

            @Override
            protected void end() { }

            @Override
            protected void interrupted() { }
        };
    }


}
