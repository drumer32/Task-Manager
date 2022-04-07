package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import managers.TaskManager;
import model.Epic;
import model.Task;
import server.converter.Converter;

import java.util.List;
import java.util.TreeMap;

public class ProcessGetRequest {

    static String response;

    public static String processGetRequest(TaskManager manager, String query, String path) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = Converter
                .registerAll(gsonBuilder)
                .setPrettyPrinting()
                .create();

        switch (path) {
            case "/tasks/load/" -> {
                ///load/<ключ>?API_KEY=.
                int key = query.charAt(0);
                String apiKey = String.valueOf(query.charAt(query.length()-1));
                manager.loadManager(key, apiKey);
            }
            case "/tasks" -> {
                TreeMap<Integer, Task> set = manager.getPrioritizedTasks();
                response = gson.toJson(set);
            }
            case "/tasks/history" -> {
                List<Task> history = manager.history();
                response = gson.toJson(history.size());
            }
            case "/tasks/task" -> {
                List<Task> tasks = manager.getAllTasks();
                response = gson.toJson(tasks);
            }
            case "/tasks/task/" -> {
                List<Task> tasks = manager.getAllTasks();
                response = gson.toJson(tasks);
                if (query != null) {
                    int id = Integer.parseInt(String.valueOf(query.charAt(query.length()-1)));
                    Task task = manager.getById(id);
                    response = gson.toJson(task);
                } else {
                    response = "Такой задачи нет";
                }
            }
            case "/tasks/epic" -> {
                List<Task> epics = manager.getAllEpic();
                response = gson.toJson(epics);
                if (query != null) {
                    int id = Integer.parseInt(query.substring(3));
                    Epic task = (Epic) manager.getById(id);
                    List<Integer> list = task.getSubTasksIds();
                    response = gson.toJson(list);
                } else {
                    response = "Такого эпика нет";
                }
            }
            case "/tasks/subtask" -> {
                List<Task> subtasks = manager.getSubTaskAll();
                response = gson.toJson(subtasks);
            }
            default -> response = "Неверная команда";
        }
        return response;
    }
}