package frc.team5333.stronghold.core.control;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team5333.stronghold.core.configs.JoyMap;

public class LogitechJoy extends Joystick {

    Button[] buttons = new JoystickButton[12];

    public LogitechJoy(int port) {
        super(port);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JoystickButton(this, i);
        }
    }

    @Override
    public double getX(Hand hand) {
        return getRawAxis(JoyMap.ID.x);
    }

    @Override
    public double getY(Hand hand) {
        return getRawAxis(JoyMap.ID.y);
    }

    @Override
    public double getTwist() {
        return getRawAxis(JoyMap.ID.twist);
    }

    public double getSlider() {
        return getRawAxis(JoyMap.ID.slider);
    }

    @Override
    public boolean getBumper(Hand hand) {
        return getRawButton(JoyMap.ID.bumper);
    }

    @Override
    public boolean getTrigger(Hand hand) {
        return getRawButton(JoyMap.ID.trigger);
    }

    public Button getButton(int i) {
        return buttons[i];
    }

    public Button getBumperButton() {
        return buttons[JoyMap.ID.bumper];
    }

    public Button getTriggerButton() {
        return buttons[JoyMap.ID.trigger];
    }
}
