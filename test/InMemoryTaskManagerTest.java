import managers.InMemoryTaskManager;
import managers.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.*;
import support.TaskGenerator;
import java.time.LocalDateTime;

import static support.Status.DONE;

/**
 * Здравствуйте! Спасибо большое за ревью!
 * Только так получилось сделать BeforeAll, объявлять переменные пришлось вне метода.
 * Иначе все загорается красным и нет доступа к переменным:)))
 * Просто такая запись получается в два раза длиннее предыдущей. Это правильнее с технической стороны?
 */

public class InMemoryTaskManagerTest {
    TaskManager taskManager = new InMemoryTaskManager();
    private static Task task;
    private static Task task2;
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
        epic1 = taskGenerator.generateEpic();
        epic2 = taskGenerator.generateEpic();
        subTask = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 3, 12, 15, 30));
        subTask2 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 4, 15, 15, 30));
        subTask3 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 5, 18, 15, 30));
    }

    @BeforeEach
    public void createTasks () {
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
    public void subtasksHaveEpicTest() {
        Assertions.assertEquals(epic1.getId(), subTask.getEpicId());
    }

    @Test
    public void checkEpicStatus() {
        subTask.setStatus(DONE);
        subTask2.setStatus(DONE);
        subTask3.setStatus(DONE);
        taskManager.updateSubTask(subTask);
        taskManager.updateSubTask(subTask2);
        taskManager.updateSubTask(subTask3);
        Assertions.assertEquals(DONE, epic1.getStatus());
    }

    @Test
    public void getAllTasksTest() {
        Assertions.assertEquals(2, taskManager.getAllTasks().size());
    }

    @Test
    public void getAllEpicTest() {
        Assertions.assertEquals(2, taskManager.getAllEpic().size());
    }

    @Test
    public void getSubTaskAllTest() {
        Assertions.assertEquals(3, taskManager.getSubTaskAll().size());
    }

    @Test
    public void getSubTaskByEpicTest() {
        Assertions.assertEquals(3, taskManager.getSubTaskByEpic(epic1).size());
    }

    @Test
    public void getSubTaskByEpicIdTest() {
        Assertions.assertEquals(3, taskManager.getSubTaskByEpicId(epic1.getId()).size());
    }

    @Test
    public void findByIdTest() {
        Assertions.assertEquals(1, taskManager.findById(1).getId());
    }

    @Test
    public void findSubTaskByIdTest() {
        Assertions.assertEquals(6, taskManager.findSubTaskById(6).getId());
    }

    @Test
    public void findEpicByIdTest() {
        Assertions.assertEquals(3, taskManager.findEpicById(3).getId());
    }

    @Test
    public void updateTaskTest() {
        Task taskUpdated = new Task(task.getId(),"TASK UPDATED");
        taskManager.updateTask(taskUpdated);
        Assertions.assertEquals("TASK UPDATED", task.getTaskDescription());
    }

    @Test
    public void updateEpicTest() {
        Epic epicUpdated = new Epic(epic1.getId(),"EPIC UPDATED");
        taskManager.updateEpic(epicUpdated);
        Assertions.assertEquals("EPIC UPDATED", epic1.getTaskDescription());
    }

    @Test
    public void updateSubTaskTest() {
        SubTask subTaskUpdated = new SubTask(subTask.getId(), "SUBTASK UPDATED");
        taskManager.updateSubTask(subTaskUpdated);
        Assertions.assertEquals("SUBTASK UPDATED", subTask.getTaskDescription());
    }

    @Test
    public void deleteTaskByIdTest() {
        taskManager.deleteTaskById(task.getId());
        Assertions.assertEquals(1, taskManager.getAllTasks().size());
    }

    @Test
    public void deleteSubTaskByIdTest() {
        taskManager.deleteSubTaskById(subTask.getId());
        Assertions.assertEquals(2, taskManager.getSubTaskAll().size());
    }

    @Test
    public void deleteEpicByIdTest() {
        taskManager.deleteEpicById(epic1.getId());
        Assertions.assertEquals(1, taskManager.getAllEpic().size());
    }
}
