package frc.team5333.stronghold.core.vision;


import edu.wpi.first.wpilibj.SerialPort;
import frc.team5333.stronghold.core.StrongholdCore;
import jaci.openrio.module.cereal.Cereal;
import jaci.openrio.module.cereal.SerialPortWrapper;
import jaci.openrio.toast.core.thread.Async;
import jssc.SerialPortException;

public class LED {

    static SerialPortWrapper sp;
    static boolean started = false;

    public static void init() {
//        Async.schedule(() -> {
//            try {
//                sp = Cereal.getPort("/dev/ttyACM0", 9600, 8, 1, 0);
//                started = true;
//                StrongholdCore.logger.info("LED TTY Connected!");
//            } catch (Throwable e) {
//                StrongholdCore.logger.error("Could not instantiate Serial Port to LED");
//                StrongholdCore.logger.exception(e);
//            }
//        });
    }

    public static void sendLED(VisionFrame frame) {
        if (!started) return;
        double left = 0;
        double right = 0;
        if (!frame.isEmpty()) {
            VisionRectangle rect = frame.getSelectedRect();
            int center_x_rect = (int) (rect.getX() + (rect.getWidth() / 2));
            int center_x_frame = 640 / 2;
            double perc = (center_x_frame - center_x_rect) / (double)(center_x_frame);

            left = 1 - Math.abs(perc * perc);
            right = left;

            if (perc < 0.0) {
                left += 3 * perc;
            } else if (perc > 0.0) {
                right -= 3 * perc;
            }
        }

        byte[] bytes = new byte[2];
        bytes[0] = (byte)(Math.max(left, 0) * 100);
        bytes[1] = (byte)(Math.max(right, 0) * 100);
        try {
            sp.writeBytes(bytes);
        } catch (SerialPortException e) { }
    }

}
