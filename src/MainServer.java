import managers.http.HttpTaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import server.HttpTaskServer;
import support.TaskGenerator;

import java.net.URI;
import java.time.LocalDateTime;

public class MainServer {
    public static void main(String[] args) throws Exception {
        HttpTaskServer server = new HttpTaskServer("TaskSavedBackup");
        server.start();
        URI url = URI.create(("http://localhost:8078/"));
        HttpTaskManager taskManager = new HttpTaskManager("TaskSavedBackup", url);

        TaskGenerator taskGenerator = new TaskGenerator();

        Task task = taskGenerator.generateTask24Hours(LocalDateTime.of(2020, 3, 1, 15, 30));
        Task task2 = taskGenerator.generateTask24Hours(LocalDateTime.of(2021, 3, 3, 15, 30));
        Epic epic1 = taskGenerator.generateEpic();
        Epic epic2 = taskGenerator.generateEpic();
        SubTask subTask = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2019, 3, 12, 15, 30));
        SubTask subTask2 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 3, 15, 15, 30));
        SubTask subTask3 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2018, 3, 18, 15, 30));

        taskManager.createTask(task);
        taskManager.createTask(task2);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        final Integer epic1id = epic1.getId();
        final Integer epic2id = epic2.getId();
        final Integer taskId = task.getId();
        final Integer taskId2 = task2.getId();
    }
}
