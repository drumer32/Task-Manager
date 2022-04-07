package managers.http;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import exceptions.ManagerSaveException;
import managers.filebacked.FileBackedTaskManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

/**
 * Здравствуйте, Евгений! Я старался, как мог =) Наверное, тут немного сумбурно и функционал не богатый,
 * но в Insomnia работает.
 */

public class HttpTaskManager extends FileBackedTaskManager {

    private final KVClient client;

    public HttpTaskManager(String filename, URI url) {
        super(filename);
        this.client = new KVClient(url);
    }

    public void saveManager(int key, String apiKey) throws IOException {
        String json;
        Gson gson = new Gson();
        FileReader reader = new FileReader(getFilename());
        BufferedReader br = new BufferedReader(reader);
        ArrayList<String> values = new ArrayList<>();
        try {
            while (br.ready()) {
                String line = br.readLine();
                values.add(line);
            }
            br.close();
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка чтения файла");
        }
        json = gson.toJson(values);
        client.saveManager(json, key, apiKey);
    }

    public void loadManager(int key, String apiKey) {
        String manager1 = client.loadManager(key, apiKey);
        String manager = String.valueOf(JsonParser.parseString(manager1));
        String[] values = manager.split("/n");
        ArrayList<String> valuesInArr = new ArrayList<>(List.of(values));
        clearAll();
        taskFromString(valuesInArr);
        epicFromString(valuesInArr);
        subtaskFromString(valuesInArr);
        historyFromString(valuesInArr);
    }

    public HttpClient getClient() {
        return client.getHttpClient();
    }

    @Override
    public String getFilename() {
        return super.getFilename();
    }
}
