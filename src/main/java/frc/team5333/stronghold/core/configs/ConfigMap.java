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
                    intake          = 0,    // PWM - SR
                    shittake        = 1;    // PWM - SR
        }
        public static class Pneumatics {
            public static int
                    pcm_device_id = 1,
                    shittake_raise_solenoid = 0,
                    shittake_lower_solenoid = 1;
        }
        public static class Power {
            public static int
                    pdp_device_id = 0;
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

            public static double
                        joystick_override_deadzone = 0.1,
                        spinup_flywheel_throttle = 0.8;
        }
        public static class Align {
            public static double
                        p = 0.7,
                        f = 0.0,
                        i = 0.001,
                        d = 0.05;

            public static String gyroAlignAxis = "Y";
            public static double
                        acceptable_error = 0.05,
                        align_timeout = 2000;
        }
        public static class Motors {
            public static double
                        left_coefficient = 1.0,
                        right_coefficient = 1.0,
                        flywheel_top_coefficient = 1.0,
                        flywheel_bottom_coefficient = 1.0,
                        flywheel_intake_coefficient = 1.0,
                        shittake_coefficient = 1.0;
        }
        public static int control_loop_freq = 100;
        public static int pid_target_samples = 5;
    }

}
