package frc.team5333.stronghold.core.control;

import edu.wpi.first.wpilibj.command.Command;
import frc.team5333.stronghold.core.configs.JoyMap;

import java.util.function.Consumer;

public class Operator {

    public static LogitechJoy joy_left, joy_right;

    public static void init() {
        joy_left    = new LogitechJoy(JoyMap.Operator.left);
        joy_right   = new LogitechJoy(JoyMap.Operator.right);
    }

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

    public static LogitechJoy getLeftJoystick() {
        return joy_left;
    }

    public static LogitechJoy getRightJoystick() {
        return joy_right;
    }

}
