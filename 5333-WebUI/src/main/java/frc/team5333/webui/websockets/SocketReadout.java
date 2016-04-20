package frc.team5333.webui.websockets;


import com.grack.nanojson.JsonObject;
import frc.team5333.stronghold.core.control.ADIS16448_IMU;
import frc.team5333.stronghold.core.control.IO;
import frc.team5333.stronghold.core.strategy.StrategyController;
import frc.team5333.webui.WebHandler;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@WebSocket
public class SocketReadout {

    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

    @OnWebSocketConnect
    public void connected(Session session) {
        sessions.add(session);
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        sessions.remove(session);
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException { }

    public static void tick() {
        JsonObject obj = new JsonObject();
        obj.put("Left Throttle", p(IO.motor_master_left.get() * 100));
        obj.put("Right Throttle", p(IO.motor_master_right.get() * 100));

        obj.put("Flywheel Top", p(IO.motor_flywheel_top.get() * 100));
        obj.put("Flywheel Bottom", p(IO.motor_flywheel_bottom.get() * 100));

        obj.put("Strategy", StrategyController.INSTANCE.getStrategy().getName());

        obj.put("Left Encoder", IO.motor_master_left.getEncPosition());
        obj.put("Right Encoder", IO.motor_master_right.getEncPosition());

        obj.put("IMU", String.format("%.2f %.2f %.2f",
                IO.maybeIMU(ADIS16448_IMU::getAngleX),
                IO.maybeIMU(ADIS16448_IMU::getAngleY),
                IO.maybeIMU(ADIS16448_IMU::getAngleZ)
        ));

        obj.put("IMU-RATE", String.format("%.2f %.2f %.2f",
                IO.maybeIMU(ADIS16448_IMU::getRateX),
                IO.maybeIMU(ADIS16448_IMU::getRateY),
                IO.maybeIMU(ADIS16448_IMU::getRateZ)
        ));

        sessions.forEach(session -> {
            try {
                session.getRemote().sendString(WebHandler.jsonToString(obj));
            } catch (Exception e) { }
        });
    }

    public static String p(double d) {
        return String.format("%.2f%%", d);
    }
}
