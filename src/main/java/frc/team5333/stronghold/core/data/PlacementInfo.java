package frc.team5333.stronghold.core.data;

public class PlacementInfo {

    public static double[] angles = { 0.0, 12.5, 0.0, -12.5, -25.0 };

    public static int placement_id = 0;

    public static double getDesiredAlign() {
        if (placement_id < angles.length) {
            return angles[placement_id];
        } else {
            return 0.0;
        }
    }

    public static void update(int pid) {
        placement_id = pid;
    }
}
