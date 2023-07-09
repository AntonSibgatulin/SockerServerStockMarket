package jp.antonsibgatulin.main;

import jp.antonsibgatulin.config.Config;
import jp.antonsibgatulin.database.DatabaseModule;
import jp.antonsibgatulin.messager.user.User;
import jp.antonsibgatulin.services.messages.MessagerController;
import jp.antonsibgatulin.services.auth.AuthController;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server extends WebSocketServer {

    private DatabaseModule databaseModule;

    private HashSet<WebSocket> connections = new HashSet<>();
    private HashMap<WebSocket, User> userHashMap = new HashMap<>();
    private HashMap<Long, User> userHashMapById = new HashMap<>();

    private ExecutorService executorService;


    private AuthController authController;
    private MessagerController messagerController;

    private Config config;

    public Server(InetSocketAddress address, DatabaseModule databaseModule, Config config) {
        super(address);
        init(databaseModule, config);
    }


    public void init(DatabaseModule databaseModule, Config config) {
        this.databaseModule = databaseModule;
        this.config = config;

        authController = new AuthController(databaseModule, this);
        executorService = Executors.newFixedThreadPool(config.countOfThreadPoll);
    }


    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        var runnable = new Runnable() {
            @Override
            public void run() {
                connections.add(webSocket);
            }
        };
        addThread(runnable);
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        var runnable = new Runnable() {
            @Override
            public void run() {

                onExit(webSocket);
            }
        };
        addThread(runnable);
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        var user = userHashMap.get(webSocket);
        String[] arr = s.split(";");
        String json = null;
        if (arr[1].equals("json")) {
            json = s.replace(arr[0] + ";" + arr[1] + ";", "");
        }
        if (user == null) {
            if (json != null) {
                authController.execute(webSocket, json);
            } else {
                authController.executeCommand(webSocket, arr);
            }
        } else {
            var message_socket_type = EMessageSocket.valueOf(arr[0]);

            if (message_socket_type == EMessageSocket.MESSAGE) {

                if (json != null) {
                    messagerController.execute(user, json);
                } else {
                    messagerController.executeCommand(user, arr);
                }
            }else if (message_socket_type == EMessageSocket.GET_DIALOGUES){
                messagerController.executeCommand(user,arr);
            }

        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {
        System.out.println("Server was started");

    }


    public void onAuth(WebSocket webSocket, User user) {
        userHashMap.put(webSocket, user);
        userHashMapById.put(user.getId(), user);
    }

    public void onExit(WebSocket webSocket) {
        connections.remove(webSocket);

        var user = userHashMap.get(webSocket);
        if (user != null) {
            userHashMap.remove(webSocket);
            userHashMapById.remove(user.getId());
        }
    }


    public boolean checkOnline(Long id) {
        var user = userHashMapById.get(id);
        return user != null;
    }

    public void addThread(Runnable runnable) {
        executorService.execute(runnable);
    }

    public void sendNotificationToAll(String message, User... users) {
        for (User user : users) {
            var u = userHashMapById.get(user.getId());
            if (u != null) {
                u.send(message);
            }
        }
    }
}
