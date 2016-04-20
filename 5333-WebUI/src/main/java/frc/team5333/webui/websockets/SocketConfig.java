package frc.team5333.webui.websockets;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import frc.team5333.stronghold.core.configs.Configurations;
import frc.team5333.webui.WebHandler;
import jaci.openrio.toast.lib.module.ModuleConfig;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import javax.script.ScriptException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@WebSocket
public class SocketConfig {

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
        if (message.equalsIgnoreCase("list")) {
            try {
                JsonObject masterObj = new JsonObject();
                JsonArray json = new JsonArray();
                ModuleConfig.allConfigs.forEach(config -> {
                    JsonObject obj = new JsonObject();
                    obj.put("name", config.parent_file.getName());
                    obj.put("code", Integer.toUnsignedString(config.hashCode(), 16));
                    json.add(obj);
                });
                masterObj.put("action", "list");
                masterObj.put("data", json);
                session.getRemote().sendString(WebHandler.jsonToString(masterObj));
            } catch (IOException e) { }
        } else {
            try {
                JsonObject obj = JsonParser.object().from(message);
                String action = obj.getString("action");
                String hashcode = obj.getString("code");

                Optional<ModuleConfig> optConfig = ModuleConfig.allConfigs.stream().filter(config2 -> {
                    try {
                        return hashcode != null && config2.hashCode() == Integer.parseUnsignedInt(hashcode, 16);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }).findFirst();

                ModuleConfig config = Configurations.config;
                if (optConfig.isPresent()) config = optConfig.get();

                JsonObject jsonSend = new JsonObject();
                jsonSend.put("action", "update");
                jsonSend.put("name", config.parent_file.getName());

                if (action.equalsIgnoreCase("initial")) {
                    try {
                        jsonSend.put("data", config.toJSON());
                        session.getRemote().sendString(WebHandler.jsonToString(jsonSend));
                    } catch (IOException | ScriptException e) {}
                } else if (action.equalsIgnoreCase("set")) {
                    FileWriter writer = new FileWriter(config.parent_file);
                    writer.write(obj.getString("data"));
                    writer.close();
                    config.reload();

                    try {
                        jsonSend.put("data", config.toJSON());
                        session.getRemote().sendString(WebHandler.jsonToString(jsonSend));
                    } catch (IOException | ScriptException e) {}
                }
            } catch (JsonParserException e) { }
        }
    }
}
