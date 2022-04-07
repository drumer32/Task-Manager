package server;

import com.sun.net.httpserver.HttpServer;
import managers.filebacked.FileBackedTaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

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
        System.out.println("Cервер открыт на порту " + PORT);
    }

    public URI getKVServerURI() {
        return URI.create(kvServer.getAddress());
    }

    public void close() {
        httpServer.stop(0);
        kvServer.stop();
    }

    protected static FileBackedTaskManager getManager() {
        return manager;
    }
}
