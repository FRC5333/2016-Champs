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

}