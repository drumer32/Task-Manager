package server;

import managers.TaskManager;

public class ProcessDeleteRequest {

    static String response;

    public static String processDeleteRequest(TaskManager manager, String query, String path) {
        switch (path) {
            case "/tasks" -> {
                manager.clearAll();
                response = "Все задачи удалены";
            }
            case "/tasks/task/" -> {
                int id = Integer.parseInt(query.substring(3));
                manager.deleteById(id);
                response = "Задача " + id + " удалена";
            }
        }
        return response;
    }
}
