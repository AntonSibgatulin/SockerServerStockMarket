package jp.antonsibgatulin.services.auth;

import jp.antonsibgatulin.database.DatabaseModule;
import jp.antonsibgatulin.main.Server;
import jp.antonsibgatulin.services.Service;
import netscape.javascript.JSObject;
import org.java_websocket.WebSocket;
import org.json.JSONObject;

public class AuthController extends Service {


    public AuthController(DatabaseModule databaseModule, Server server) {
        super(databaseModule, server);
    }

    public void execute(WebSocket webSocket, String message){
        var jsonObject = new JSONObject(message);
        var type = EAuth.valueOf(jsonObject.getString("type").toUpperCase());
        if(type == EAuth.AUTH_TOKEN){
            var token = jsonObject.getString("token");
            var tokenModel = databaseModule.authByToken(token);
            if(tokenModel == null){
                webSocket.send("auth;string;error");
                return;
            }else{
                tokenModel.getUser().setWebSocket(webSocket);
                server.onAuth(webSocket,tokenModel.getUser());

            }
        }



    }

    public void setDatabaseModule(DatabaseModule databaseModule){
        this.databaseModule = databaseModule;
    }

    @Override
    public void executeCommand(WebSocket webSocket, String[] arr) {

    }
}
