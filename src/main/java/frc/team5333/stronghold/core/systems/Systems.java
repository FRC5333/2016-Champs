package frc.team5333.stronghold.core.systems;

public class Systems {

    public static ControlSystem control;
    public static DriveSystem drive;

    public static void init() {
        control = new ControlSystem();
        drive   = new DriveSystem();
    }

    /**
     * Tick all Systems. This will only go through during Autonomous / Teleoperated periods.
     */
    public static void tick() {
        control.tick();
        drive.tick();
    }

}
