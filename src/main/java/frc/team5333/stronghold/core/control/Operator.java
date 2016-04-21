package frc.team5333.stronghold.core.control;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5333.stronghold.core.configs.JoyMap;

public class Operator {

    public static LogitechJoy joy_left, joy_right;

    public static void init() {
        joy_left    = new LogitechJoy(JoyMap.Operator.left);
        joy_right   = new LogitechJoy(JoyMap.Operator.right);
    }

    public static LogitechJoy getLeftJoystick() {
        return joy_left;
    }

    public static LogitechJoy getRightJoystick() {
        return joy_right;
    }

}
