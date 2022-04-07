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

public class getRequestTest {

    protected HttpClient client;
    protected Gson gson;
    protected HttpTaskServer server;
    protected HttpTaskManager taskManager;

    @Test
    public void getRequestTest()throws Exception {
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
        taskManager.createTask(task);

        String json = gson.toJson(taskManager.getPrioritizedTasks());
        HttpRequest request = getRequest();
        String json2 = getRequestBody(request);
        Assertions.assertEquals(json, json2);
        server.close();
    }

    private String getRequestBody(HttpRequest request) {
        String json = null;
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                json = response.body();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return json;
    }
    private static HttpRequest makeRequest(URI url) {
        return HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
    }

    public static HttpRequest getRequest() {
        URI requestURI = URI.create("http://localhost:8080/tasks");
        return makeRequest(requestURI);
    }
}
