package jp.antonsibgatulin;

import jp.antonsibgatulin.config.Config;
import jp.antonsibgatulin.database.DatabaseModule;
import jp.antonsibgatulin.main.Server;
import jp.antonsibgatulin.services.Service;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws UnknownHostException {

        DatabaseModule databaseModule = new DatabaseModule();
        Config config = new Config();
        Server server = new Server(new InetSocketAddress(InetAddress.getByName("localhost"),8080), databaseModule, config);
        server.start();


    }
}
