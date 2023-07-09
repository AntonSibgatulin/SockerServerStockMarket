package jp.antonsibgatulin.services.dilog;

import jp.antonsibgatulin.database.DatabaseModule;
import jp.antonsibgatulin.main.Server;
import jp.antonsibgatulin.messager.message.Dilog;
import jp.antonsibgatulin.messager.user.User;
import jp.antonsibgatulin.services.Service;
import jp.antonsibgatulin.utils.ClassUtils;
import org.java_websocket.WebSocket;
import org.json.JSONObject;

import java.awt.*;
import java.util.List;

public class DialogueController extends Service {


    public DialogueController(DatabaseModule databaseModule, Server server) {
        super(databaseModule, server);
    }

    @Override
    public void execute(WebSocket webSocket, String json) {

    }

    @Override
    public void executeCommand(WebSocket webSocket, String[] arr) {

    }

    @Override
    public void executeCommand(User user, String[] arr) {
       var type = EDialogue.valueOf(arr[2]);
       if( type == EDialogue.GET_ALL_DIALOGUES){
           List<Dilog> dialogList = databaseModule.getAllMyDialogues(user);
           var st = ClassUtils.fromObjectToString(dialogList);
           user.send(String.valueOf(EDialogue.GET_ALL_DIALOGUES)+";json;"+st);
       }
    }
}
