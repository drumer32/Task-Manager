package managers.http;

import exceptions.ManagerSaveException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVClient {
    private final URI url;
    private final HttpClient httpClient;

    public KVClient(URI path) {
        httpClient = HttpClient.newHttpClient();
        this.url = path;
    }

    public void saveManager(String json, int key, String apiKey) {
        URI requestURI = URI.create(this.url + "/save/" + key + "?API_KEY=" + apiKey);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(requestURI)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Saved successfully");
            }
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException(e.getMessage());
            //теперь вроде понял, спасибо)
        }
    }

    public String loadManager(int key, String apiKey) {
        String json = null;
        URI requestURI = URI.create(this.url + "/load/" + key + "?API_KEY=" + apiKey);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(requestURI)
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                json = response.body();
            }
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException(e.getMessage());
        }
        return json;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }
}
