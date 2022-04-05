package server;

import com.sun.net.httpserver.HttpServer;
import managers.filebacked.FileBackedTaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private static FileBackedTaskManager manager;
    private final HttpServer httpServer;
    private final KVServer kvServer;

    public HttpTaskServer(String filename) throws IOException {
        manager = new FileBackedTaskManager(filename);
        httpServer = HttpServer.create();
        kvServer = new KVServer();
    }

    public void start() throws IOException {
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler());
        httpServer.start();
        kvServer.start();
        System.out.println("Рабочий сервер открыт на порту " + PORT);
    }

    public String managerToJson(HttpClient httpClient) {
        URI requestURI = URI.create("http://localhost:" + PORT + "/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(requestURI)
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void managerFromJson(String json) throws IOException {
        String s = null;
        manager.loadFromFile(s);
    }

    public URI getKVServerURI() {
        return URI.create(kvServer.getAddress());
    }
    public void close() {
        httpServer.stop(0);
    }

    protected static FileBackedTaskManager getManager() {
        return manager;
    }
}
