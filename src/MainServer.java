import managers.http.HttpTaskManager;
import managers.http.KVClient;
import server.HttpTaskServer;

import java.net.URI;

public class MainServer {
    public static void main(String[] args) throws Exception {
        HttpTaskServer server = new HttpTaskServer("TaskSavedBackup");
        server.start();
        URI url = URI.create(("http://localhost:8078"));
        HttpTaskManager taskManager = new HttpTaskManager("TaskSavedBackup", url);
        KVClient client = new KVClient(url);
    }
}
