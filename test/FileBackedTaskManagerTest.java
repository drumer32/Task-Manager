import managers.filebacked.FileBackedTaskManager;
import managers.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.*;
import support.TaskGenerator;

import java.io.IOException;
import java.time.LocalDateTime;

public class FileBackedTaskManagerTest {

    String filename = "TaskSavedBackup";
    TaskManager taskManager = new FileBackedTaskManager(filename);

    private static Task task;
    private static Task task2;
    private static Task task3;
    private static Epic epic1;
    private static Epic epic2;
    private static SubTask subTask;
    private static SubTask subTask2;
    private static SubTask subTask3;

    @BeforeAll
    static void generateTasks() {
        TaskGenerator taskGenerator = new TaskGenerator();
        task = taskGenerator.generateTask24Hours(LocalDateTime.of(2022, 1, 1, 15, 30));
        task2 = taskGenerator.generateTask24Hours(LocalDateTime.of(2022, 2, 3, 15, 30));
        task3 = taskGenerator.generateTask24Hours(LocalDateTime.of(2022,7,3,15,30));
        epic1 = taskGenerator.generateEpic();
        epic2 = taskGenerator.generateEpic();
        subTask = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 3, 12, 15, 30));
        subTask2 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 4, 15, 15, 30));
        subTask3 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 5, 18, 15, 30));
    }


    @BeforeEach
    public void createTasks() {
        //ТАСКИ
        taskManager.createTask(task);
        taskManager.createTask(task2);
        //ЭПИКИ
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        //АЙДИШНИКИ
        final Integer epic1id = epic1.getId();
        //СОЗДАНИЕ САБТАСОК
        taskManager.createSubTask(subTask, epic1id);
        taskManager.createSubTask(subTask2, epic1id);
        taskManager.createSubTask(subTask3, epic1id);
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
