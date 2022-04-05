package server;

import managers.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import support.Status;
import support.TaskType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class ProcessPostRequest {
    private static String response = "null";

    public static String processPostRequest(TaskManager manager, InputStream body, String path) {
        //{"id":1,
        //"type":"TASK",
        // "name":"TaskName",
        // "status":"NEW",
        // "description":"TaskDescription",
        // "startTime":"2020-03-01T15:30",
        // "duration":"PT24H"}
        if (path.equals("/tasks/task")) {
            String text = new BufferedReader(
                    new InputStreamReader(body, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            StringBuilder str = new StringBuilder();
            String[] contents = text.split(",");
            for (String s : contents) {
                String content = s;
                content = content.replace("\"", "")
                        .replace("{", "")
                        .replace("}", "");
                int index = content.indexOf(":") + 1;
                content = content.substring(index);
                str.append(content).append(",");
            }
            String bodyTask = str.toString();
            //1,TASK,TaskName,NEW,TaskDescription,2020-03-01T15:30,PT24H,
            String[] arrTasks = bodyTask.split(",");
            //[1],[TASK],[TaskName],[NEW],[TaskDescription],[2020-03-01T15:30],[PT24H],[]
            if (arrTasks[1].equals(TaskType.TASK.toString())) {
                LocalDateTime startTime = LocalDateTime.parse(arrTasks[5]);
                Duration duration = Duration.parse(arrTasks[6]);
                Task task = new Task(Integer.parseInt(arrTasks[0]), TaskType.valueOf(arrTasks[1]),
                        arrTasks[2], Status.valueOf(arrTasks[3]), arrTasks[4], startTime, duration);
                manager.createTask(task);
                response = "Задача " + task.getTaskName() + " создана";
            } else if (arrTasks[1].equals(TaskType.EPIC.toString())) {
                Epic epic = new Epic(Integer.parseInt(arrTasks[0]), TaskType.valueOf(arrTasks[1]),
                            arrTasks[2], Status.valueOf(arrTasks[3]), arrTasks[4]);
                    String startTime;
                    if (arrTasks[5].equals("null")) {
                        startTime = null;
                    } else {
                        startTime = arrTasks[5];
                    }
                    Duration duration;
                    if (arrTasks[6].equals("null")) {
                        duration = null;
                    } else {
                        duration = Duration.parse(arrTasks[6]);
                    }
                    epic.setStartTime(startTime);
                    epic.setDurationOfHours(duration);
                    manager.createEpic(epic);
                    response = "Эпик " + epic.getId() + epic.getTaskName() + " добавлен";
            } else if (arrTasks[1].equals(TaskType.SUBTASK.toString())) {
                    SubTask subTask = new SubTask(Integer.parseInt(arrTasks[0]), TaskType.valueOf(arrTasks[1]),
                            arrTasks[2], Status.valueOf(arrTasks[3]), arrTasks[4], Integer.parseInt(arrTasks[7]));
                    String startTime = arrTasks[5];
                    Duration duration = Duration.parse(arrTasks[6]);
                    subTask.setStartTime(startTime);
                    subTask.setDurationOfHours(duration);
                    manager.createSubTask(subTask, Integer.parseInt(arrTasks[7]));
                    response = "Задача " + subTask.getId() + subTask.getTaskName() + " добавлена";
            } else response = "При создании задачи возникла ошибка.";
        }
        return response;
    }
}