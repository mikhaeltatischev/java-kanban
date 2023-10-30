import server.HttpTaskServer;
import server.KVServer;
import service.TaskManager;
import service.impl.Managers;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        KVServer kvServer = new KVServer();
        kvServer.start();

        TaskManager manager = Managers.getDefault();
        HttpTaskServer httpTaskServer = new HttpTaskServer(manager);
        httpTaskServer.start();

    }
}