import managers.FileBackedTaskManager;
import managers.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.TaskGenerator;

import java.io.IOException;
import java.time.LocalDateTime;

public class FileBackedTaskManagerTest {
    String filename = "TaskSavedBackup";
    TaskManager taskManager = new FileBackedTaskManager(filename);
    TaskGenerator taskGenerator = new TaskGenerator();

    Task task = taskGenerator.generateTask24Hours(LocalDateTime.of(2022, 4, 1, 15, 30));
    Task task2 = taskGenerator.generateTask24Hours(LocalDateTime.of(2022, 3, 3, 15, 30));
    Epic epic1 = taskGenerator.generateEpic();
    Epic epic2 = taskGenerator.generateEpic();
    SubTask subTask = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2020, 3, 12, 15, 30));
    SubTask subTask2 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2021, 3, 15, 15, 30));
    SubTask subTask3 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 1, 18, 15, 30));
    Task task3 = taskGenerator.generateTask24Hours(LocalDateTime.of(2022, 5, 21, 15, 30));

    @BeforeEach
    public void generateTasks() {
        //ТАСКИ
        taskManager.createTask(task);
        taskManager.createTask(task2);
        //ЭПИКИ
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        //АЙДИШНИКИ
        final Integer epic1id = epic1.getId();
        final Integer epic2id = epic2.getId();
        final Integer taskId = task.getId();
        final Integer taskId2 = task2.getId();
        //СОЗДАНИЕ САБТАСОК
        taskManager.createSubTask(subTask, epic1id);
        taskManager.createSubTask(subTask2, epic1id);
        taskManager.createSubTask(subTask3, epic1id);
        //АЙДИ САБТАСОК
        final Integer subtask1Id = subTask.getId();
        final Integer subtask2Id = subTask2.getId();
        final Integer subtask3Id = subTask3.getId();
    }

    @AfterEach
    public void clearAfterTesting () {
        taskManager.clearAll();
    }

    @Test
    public void loadFromFileTest() throws IOException {
        taskManager.findById(task.getId());
        taskManager.findById(task2.getId());
        taskManager.findEpicById(epic1.getId());
        taskManager.findEpicById(epic2.getId());
        taskManager.findSubTaskById(subTask.getId());
        taskManager.findSubTaskById(subTask2.getId());
        taskManager.findSubTaskById(subTask3.getId());
        taskManager.loadFromFile(filename);
        Assertions.assertFalse(taskManager.getAllTasks().isEmpty());
        Assertions.assertFalse(taskManager.getSubTaskAll().isEmpty());
        Assertions.assertFalse(taskManager.getAllEpic().isEmpty());
    }

    @Test
    public void historyWithoutRepeating() throws IOException {
        taskManager.clearAll();
        taskManager.createTask(task3);
        taskManager.findById(task3.getId());
        taskManager.findById(task3.getId());
        taskManager.clearAll();
        taskManager.loadFromFile(filename);
        Assertions.assertEquals(1, taskManager.getAllTasks().size());
    }
}
