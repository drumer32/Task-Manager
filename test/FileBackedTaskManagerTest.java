import managers.FileBackedTaskManager;
import managers.HistoryManager;
import managers.Managers;
import managers.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FileBackedTaskManagerTest extends HistoryManagerTest {
    String filename = "TaskSavedBackup";
    TaskManager taskManager = new FileBackedTaskManager(filename);

    Task task = new Task("Task1name", "Task1description");
    Task task2 = new Task("Task2name", "Task2description");
    Epic epic1 = new Epic("Epic1name", "Epic1description");
    Epic epic2 = new Epic("Epic2name", "Epic2description");
    SubTask subTask = new SubTask("SubTask1", "Subtask1description");
    SubTask subTask2 = new SubTask("SubTask2", "Subtask2description");
    SubTask subTask3 = new SubTask("SubTask3", "Subtask3description");

    @BeforeEach
    public void generateTasks () {
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
    }
}
