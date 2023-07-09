package jp.antonsibgatulin.services.messages;

import jp.antonsibgatulin.database.DatabaseModule;
import jp.antonsibgatulin.main.Server;
import jp.antonsibgatulin.messager.message.Message;
import jp.antonsibgatulin.messager.user.User;
import jp.antonsibgatulin.services.Service;
import org.java_websocket.WebSocket;
import org.json.JSONObject;

public class MessagerController extends Service {


    public MessagerController(DatabaseModule databaseModule, Server server) {
        super(databaseModule, server);
    }

    @Override
    public void execute(WebSocket webSocket, String json) {

    }

    @Override
    public void executeCommand(WebSocket webSocket, String[] arr) {

    }

    public void execute(User user,String json){
        var jsonObject = new JSONObject(json);
        var message_action = EMessage.valueOf(jsonObject.getString("action").toUpperCase());

        if(message_action == EMessage.NEW_MESSAGE){

            var dilog = databaseModule.getDilogById(jsonObject.getLong("dilogId"));
            var text = jsonObject.getString("message");

            var message = new Message(dilog,user,text);
            databaseModule.save(message);

            server.sendNotificationToAll(jsonObject.toString(),dilog.getUser_1(),dilog.getUser_2());
        }

    }

    @Override
    public void executeCommand(User user, String[] arr) {

    }
}
