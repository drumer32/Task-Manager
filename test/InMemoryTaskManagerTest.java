import managers.InMemoryTaskManager;
import managers.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void findByIdTest(Integer id) {
        taskManager.findById(id);
    }


    @Test
    public void findSubTaskByIdTest(Integer id) {
        taskManager.findSubTaskById(id);
    }


    @Test
    public void findEpicByIdTest(Integer id) {
        taskManager.findEpicById(id);
    }


    public void createTaskTest(Task task) {
        taskManager.createTask(task);
    }


    public void createEpicTest(Epic epic) {
        taskManager.createEpic(epic);
    }


    public void createSubTaskTest(SubTask subTask, Integer epicId) {
        taskManager.createSubTask(subTask, epicId);
    }


    public void updateTaskTest(Task taskUpdated) {
        taskManager.updateTask(taskUpdated);
    }


    public void updateEpicTest(Epic epicUpdated) {
        taskManager.updateEpic(epicUpdated);
    }


    public void updateSubTaskTest(SubTask SubTaskUpdated) {
        taskManager.updateSubTask(SubTaskUpdated);
    }


    public void deleteTaskByIdTest(Integer id) {
        taskManager.deleteTaskById(id);
    }


    public void deleteSubTaskByIdTest(Integer id) {
        taskManager.deleteSubTaskById(id);
    }


    public void deleteEpicByIdTest(Integer id) {
        taskManager.deleteEpicById(id);
    }

    public void historyTest() {
        taskManager.history();
    }
}
