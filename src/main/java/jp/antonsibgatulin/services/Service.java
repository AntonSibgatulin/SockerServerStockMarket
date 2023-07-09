package jp.antonsibgatulin.services;

import jp.antonsibgatulin.database.DatabaseModule;
import jp.antonsibgatulin.main.Server;
import jp.antonsibgatulin.messager.user.User;
import org.java_websocket.WebSocket;

public abstract class Service {

    public DatabaseModule databaseModule;
    public Server server;
    public Service(DatabaseModule databaseModule,Server server){
        this.databaseModule = databaseModule;
        this.server = server;

    }


    public abstract void execute(WebSocket webSocket,String json);


    public abstract void executeCommand(WebSocket webSocket, String[] arr);

    public abstract void executeCommand(User user, String[] arr);
}
