import managers.InMemoryTaskManager;
import managers.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.TaskGenerator;
import java.time.LocalDateTime;

import static support.Status.DONE;

public class InMemoryTaskManagerTest {

    //начало beforeAll
    TaskManager taskManager = new InMemoryTaskManager();
    TaskGenerator taskGenerator = new TaskGenerator();

    Task task = taskGenerator.generateTask24Hours(LocalDateTime.of(2022, 1, 1, 15, 30));
    Task task2 = taskGenerator.generateTask24Hours(LocalDateTime.of(2022, 2, 3, 15, 30));
    Epic epic1 = taskGenerator.generateEpic();
    Epic epic2 = taskGenerator.generateEpic();
    SubTask subTask = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 3, 12, 15, 30));
    SubTask subTask2 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 4, 15, 15, 30));
    SubTask subTask3 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 5, 18, 15, 30));
    Task task3 = taskGenerator.generateTask24Hours(LocalDateTime.of(2022, 3, 6, 15, 30));
// конец BeforeAll
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
