package frc.team5333.webui.websockets;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import frc.team5333.stronghold.core.vision.VisionFrame;
import frc.team5333.stronghold.core.vision.VisionNetwork;
import frc.team5333.stronghold.core.vision.VisionRectangle;
import frc.team5333.webui.WebHandler;
import frc.team5333.webui.WebUIConfig;
import jaci.openrio.toast.core.command.CommandBus;
import jaci.openrio.toast.core.thread.HeartbeatListener;
import jaci.openrio.toast.lib.util.Pretty;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@WebSocket
public class SocketVision {

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
    public void message(Session session, String message) throws IOException {
        CommandBus.parseMessage(message);
    }

    public static void start() {
        new Thread(() -> {
            while (true) {
                try {
                    tick();
                    Thread.sleep((long) (1000.0 / WebUIConfig.Vision.display_framerate));
                } catch (InterruptedException e) { }
            }
        }).start();
    }

    public static void tick() {
        VisionFrame frame = VisionNetwork.INSTANCE.getActiveFrame();
        JsonArray array = new JsonArray();
        if (!frame.isEmpty()) {
            for (int i = 0; i < frame.size(); i++) {
                JsonObject object = new JsonObject();
                VisionRectangle rect = frame.get(i);
                object.put("active", i == frame.getSelected());
                object.put("x", rect.getX());
                object.put("y", rect.getY());
                object.put("width", rect.getWidth());
                object.put("height", rect.getHeight());
                array.add(object);
            }
        }

        sessions.forEach(session -> {
            try {
                session.getRemote().sendString(WebHandler.jsonToString(array));
            } catch (Exception e) { }
        });
    }
}
