package frc.team5333.stronghold.core.control;

import frc.team5333.stronghold.core.strategy.StrategyAngleAlignRough;
import frc.team5333.stronghold.core.strategy.StrategyController;

public class TransientControls {

    public static void init() {

        // Align with the Vision Target
        Operator.getRightJoystick().getButton(3).whenPressed(Commands.vision_align);

        // Override and regain operator control
        Operator.getRightJoystick().getButton(4).whenPressed(Commands.operator_override);

        Operator.getRightJoystick().getButton(5).whenPressed(Commands.command(() -> {
            StrategyController.INSTANCE.setStrategy(new StrategyAngleAlignRough(15.0));
        }));
    }

}
