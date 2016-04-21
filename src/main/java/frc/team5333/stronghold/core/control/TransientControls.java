package frc.team5333.stronghold.core.control;

public class TransientControls {

    public static void init() {

        // Align with the Vision Target
        Operator.getRightJoystick().getButton(3).whenPressed(Commands.vision_align);

        // Override and regain operator control
        Operator.getRightJoystick().getButton(4).whenPressed(Commands.operator_override);
    }

}
