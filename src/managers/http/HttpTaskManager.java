package managers.http;

import managers.filebacked.FileBackedTaskManager;

import java.net.URI;
import java.net.http.HttpClient;

public class HttpTaskManager extends FileBackedTaskManager {

    private final KVClient client;

    public HttpTaskManager(String filename, URI url) {
        super(filename);
        this.client = new KVClient(url);
    }

    public void saveManager(String key, String json) {
        client.saveManager(key, json);
    }

    public String loadManager(String key) {
        return client.loadManager(key);
    }

    public HttpClient getClient() {
        return client.getHttpClient();
    }
}
