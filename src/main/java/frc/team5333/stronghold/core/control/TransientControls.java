package frc.team5333.stronghold.core.control;

import frc.team5333.stronghold.core.strategy.StrategyController;
import frc.team5333.stronghold.core.strategy.StrategyOperator;
import frc.team5333.stronghold.core.strategy.StrategyVisionAlign;

public class TransientControls {

    public static void init() {

        // Align with the Vision Target
        Operator.getRightJoystick().getButton(3).whenPressed(Operator.command(() -> {
            StrategyController.INSTANCE.setStrategy(new StrategyVisionAlign());
        }));

        // Override and regain operator control
        Operator.getRightJoystick().getButton(4).whenPressed(Operator.command(() -> {
            StrategyController.INSTANCE.setStrategy(new StrategyOperator());
        }));
    }

}
