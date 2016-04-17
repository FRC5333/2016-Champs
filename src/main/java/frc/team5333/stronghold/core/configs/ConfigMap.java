package frc.team5333.stronghold.core.configs;

public class ConfigMap {

    public static class IO {
        public static class Motor {
            public static int
                    left_master     = 11,   // CAN - SRX (encoder)
                    left_slave      = 10,   // CAN - SRX
                    right_master    = 13,   // CAN - SRX (encoder)
                    right_slave     = 12;   // CAN - SRX

            public static int
                    flywheel_top    = 14,   // CAN - SRX
                    flywheel_bottom = 15,   // CAN - SRX
                    intake          = 0;    // PWM - SR
        }
    }

    public static class Motion {
        public static double
                wheelbase_width = 0.633294, // Meters
                max_velocity = 1.7,         // Meters per Second
                max_acceleration = 2.0,     // Meters per Second per Second
                p = 0.8, d = 0.0, a = 0.0;  // Scalar
    }

    public static class Vision {
        public static class Camera {
            public static double horizontal_fov = 57;   // Degrees
        }
    }

    public static class Control {
        public static double
                turn_throttle_coefficient = 0.5,
                fine_control_throttle_speed = 0.5;
        public static class Shoot {
            public static double
                        intake_hold_throttle = -0.25,
                        intake_fire_throttle = 0.9;
        }
        public static class Align {
            public static double
                        p = 0.7,
                        i = 0.001,
                        d = 0.05;

            public static String gyroAlignAxis = "Y";
            public static double
                        acceptable_error = 0.05,
                        align_timeout = 2000;
        }
        public static int control_loop_freq = 100;
        public static int pid_target_samples = 5;
    }

}
