import managers.InMemoryTaskManager;
import managers.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.Status;

import static support.Status.DONE;

public class InMemoryTaskManagerTest {

    TaskManager taskManager = new InMemoryTaskManager();
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
    public void subtasksHaveEpicTest() {
        Assertions.assertEquals(epic1.getId(), subTask.getEpicId());
    }

    @Test
    public void checkEpicStatus() {
        SubTask subTaskUpd = new SubTask(subTask.getId(), subTask.getType(), subTask.getTaskName(),
                DONE, subTask.getTaskDescription(), subTask.getEpicId());
        SubTask subTask2Upd = new SubTask(subTask2.getId(), subTask2.getType(), subTask2.getTaskName(),
                DONE, subTask2.getTaskDescription(), subTask2.getEpicId());
        SubTask subTask3Upd = new SubTask(subTask3.getId(), subTask3.getType(), subTask3.getTaskName(),
                DONE, subTask3.getTaskDescription(), subTask3.getEpicId());
        taskManager.updateSubTask(subTaskUpd);
        taskManager.updateSubTask(subTask2Upd);
        taskManager.updateSubTask(subTask3Upd);
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
        Task taskUpdated = new Task(task.getId(),"UPDATED NAME");
        taskManager.updateTask(taskUpdated);
        Assertions.assertEquals("UPDATED NAME", task.getTaskName());
    }

    @Test
    public void updateEpicTest() {
        Epic epicUpdated = new Epic(epic1.getId(),"EPIC UPDATED");
        taskManager.updateEpic(epicUpdated);
        Assertions.assertEquals("EPIC UPDATED", epic1.getTaskName());
    }

    @Test
    public void updateSubTaskTest() {
        SubTask subTaskUpdated = new SubTask(subTask.getId(), "SUBTASK UPDATED");
        taskManager.updateSubTask(subTaskUpdated);
        Assertions.assertEquals("SUBTASK UPDATED", subTask.getTaskName());
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
