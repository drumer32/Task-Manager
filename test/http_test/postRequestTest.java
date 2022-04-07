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

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public class postRequestTest {

    protected HttpClient client;
    protected Gson gson;
    protected HttpTaskServer server;
    protected HttpTaskManager taskManager;

    @Test
    public void postRequestTest() throws Exception {
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

        String json = gson.toJson(task);
        HttpRequest request = postRequest(json);
        client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(task, taskManager.findById(1));
        server.close();
    }

        private static HttpRequest makeRequest(URI url, String json) {
            return HttpRequest.newBuilder()
                    .uri(url)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
        }

        public static HttpRequest postRequest(String json) {
            URI requestURI = URI.create("http://localhost:8080/tasks/task");
            return makeRequest(requestURI, json);
        }

    }

