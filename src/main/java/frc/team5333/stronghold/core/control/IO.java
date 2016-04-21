package frc.team5333.stronghold.core.control;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import frc.team5333.stronghold.core.configs.ConfigMap;
import jaci.openrio.toast.core.Environment;
import jaci.openrio.toast.lib.registry.Registrar;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class IO {

    public static CANTalon motor_master_left, motor_slave_left, motor_master_right, motor_slave_right;
    public static CANTalon motor_flywheel_top, motor_flywheel_bottom;
    public static Talon motor_intake, motor_shittake;

    public static Solenoid shittake_raise, shittake_lower;

    public static ADIS16448_IMU imu_mxp;

    public static void init() {
        motor_master_left       = Registrar.canTalon(ConfigMap.IO.Motor.left_master);
        motor_slave_left        = Registrar.canTalon(ConfigMap.IO.Motor.left_slave);
        motor_master_right      = Registrar.canTalon(ConfigMap.IO.Motor.right_master);
        motor_slave_right       = Registrar.canTalon(ConfigMap.IO.Motor.right_slave);

        motor_flywheel_top      = Registrar.canTalon(ConfigMap.IO.Motor.flywheel_top);
        motor_flywheel_bottom   = Registrar.canTalon(ConfigMap.IO.Motor.flywheel_bottom);

        motor_intake            = Registrar.talon(ConfigMap.IO.Motor.intake);

        // Our St. Louis addition. I didn't like the name of "Intake 2" so I called it the "Shit Take" because
        // no one can tell me otherwise
        motor_shittake          = Registrar.talon(ConfigMap.IO.Motor.shittake);
        shittake_lower          = new Solenoid(ConfigMap.IO.Pneumatics.pcm_device_id, ConfigMap.IO.Pneumatics.shittake_lower_solenoid);
        shittake_raise          = new Solenoid(ConfigMap.IO.Pneumatics.pcm_device_id, ConfigMap.IO.Pneumatics.shittake_raise_solenoid);

        if (IMU_SUPPORTED()) imu_mxp = new ADIS16448_IMU();
    }

    public static double maybeIMU(Function<ADIS16448_IMU, Double> d) {
        if (IMU_SUPPORTED()) return d.apply(imu_mxp);
        return 0.0;
    }

    public static void maybeIMU(Consumer<ADIS16448_IMU> s) {
        if (IMU_SUPPORTED()) s.accept(imu_mxp);
    }

    public static double imuAlignAngle() {
        return maybeIMU((imu) -> {
            switch (ConfigMap.Control.Align.gyroAlignAxis) {
                case "X": return imu.getAngleX();
                case "Y": return imu.getAngleY();
                case "Z": return imu.getAngleZ();
                default:  return imu.getAngleY();
            }
        });
    }

    public static boolean IMU_SUPPORTED() {
        return Environment.isEmbedded();
    }

    public static double getLeftEncoderPosition() {
        return motor_master_left.getEncPosition();
    }

    public static double getRightEncoderPosition() {
        return motor_master_right.getEncPosition();
    }

    public static void setLeftMotors(double value) {
        value = value * ConfigMap.Control.Motors.left_coefficient;
        motor_master_left.set(value);
        motor_slave_left.set(value);
    }

    public static void setRightMotors(double value) {
        value = value * ConfigMap.Control.Motors.right_coefficient;
        motor_master_right.set(value);
        motor_slave_right.set(value);
    }

    public static void setTopFlywheel(double value) {
        value = value * ConfigMap.Control.Motors.flywheel_top_coefficient;
        motor_flywheel_top.set(value);
    }

    public static void setBottomFlywheel(double value) {
        value = value * ConfigMap.Control.Motors.flywheel_bottom_coefficient;
        motor_flywheel_bottom.set(value);
    }

    public static void setIntake(double value) {
        value = value * ConfigMap.Control.Motors.flywheel_intake_coefficient;
        motor_intake.set(value);
    }

    public static void setShittake(double value) {
        value = value * ConfigMap.Control.Motors.shittake_coefficient;
        motor_shittake.set(value);
    }

}
