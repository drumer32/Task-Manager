package http_test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import managers.http.HttpTaskManager;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;
import server.converter.Converter;
import support.TaskGenerator;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public class deleteRequestTest {

    protected HttpClient client;
    protected Gson gson;
    protected HttpTaskServer server;
    protected HttpTaskManager taskManager;

    @Test
    public void deleteRequestTest() throws Exception {
        server = new HttpTaskServer("TaskSavedBackup");
        server.start();
        URI url = URI.create(("http://localhost:8078"));
        taskManager = new HttpTaskManager("TaskSavedBackup", url);
        client = HttpClient.newHttpClient();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = Converter
                .registerAll(gsonBuilder)
                .create();

        TaskGenerator generator = new TaskGenerator();
        Task task = generator.generateTask24Hours(LocalDateTime.of
                (2020, 3, 1, 15, 30));
        task.setId(1);
        taskManager.clearAll();
        taskManager.createTask(task);

        HttpRequest request = deleteRequest();
        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        taskManager.getAllTasks();

        Assertions.assertEquals(0, taskManager.getAllTasks());
        server.close();
    }


    private static HttpRequest makeRequest(URI url) {
        return HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
    }

    public static HttpRequest deleteRequest() {
        URI requestURI = URI.create("http://localhost:8080/tasks");
        return makeRequest(requestURI);
    }

}
