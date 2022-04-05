package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.filebacked.FileBackedTaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class TaskHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response;
        FileBackedTaskManager manager = HttpTaskServer.getManager();
        String method = httpExchange.getRequestMethod();
        URI requestURI = httpExchange.getRequestURI();
        String path = requestURI.getPath();
        String query = requestURI.getQuery();
        InputStream body = httpExchange.getRequestBody();

        switch (method) {
            case "GET" -> response = ProcessGetRequest.processGetRequest(manager, query, path);
            case "POST" -> response = ProcessPostRequest.processPostRequest(manager, body, path);
            case "DELETE" -> response = ProcessDeleteRequest.processDeleteRequest(manager, query, path);
            default -> response = "Метод " + method + " не опознан";
        }
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
