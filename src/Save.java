import managers.FileBackedTaskManager;
import managers.InMemoryTaskManager;
import managers.Managers;
import managers.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import support.TaskGenerator;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static support.Status.DONE;

public class Save{

    private static final Integer IN_FILE = 2;

    public static void main(String[] args) throws IOException {

        final TaskManager fileBackedTaskManager = Managers.getDefault(IN_FILE);
        TaskGenerator taskGenerator = new TaskGenerator();

        //fileBackedTaskManager.loadFromFile("TaskSavedBackup");

        //1. СОЗДАНИЕ ЗАДАЧ

        Task task = taskGenerator.generateTask24Hours(LocalDateTime.of(2022, 3, 1, 15, 30));
        Task task2 = taskGenerator.generateTask24Hours(LocalDateTime.of(2022, 3, 3, 15, 30));
        Epic epic1 = taskGenerator.generateEpic();
        Epic epic2 = taskGenerator.generateEpic();
        SubTask subTask = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 3, 12, 15, 30));
        SubTask subTask2 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 3, 15, 15, 30));
        SubTask subTask3 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 3, 18, 15, 30));

        fileBackedTaskManager.createTask(task);
        fileBackedTaskManager.createTask(task2);
        fileBackedTaskManager.createEpic(epic1);
        fileBackedTaskManager.createEpic(epic2);
        final Integer epic1id = epic1.getId();
        final Integer epic2id = epic2.getId();
        final Integer taskId = task.getId();
        final Integer taskId2 = task2.getId();
        //СОЗДАНИЕ САБТАСОК
        fileBackedTaskManager.createSubTask(subTask, epic1id);
        fileBackedTaskManager.createSubTask(subTask2, epic1id);
        fileBackedTaskManager.createSubTask(subTask3, epic1id);
        //АЙДИ САБТАСОК
        final Integer subtask1Id = subTask.getId();
        final Integer subtask2Id = subTask2.getId();
        final Integer subtask3Id = subTask3.getId();

        //НАЧАЛО ВЫВОДА
        System.out.println(task.getTaskName() + " " + task.getTaskDescription() +
                " id: " + task.getId() + " " + task.getStatus());
        System.out.println(task2.getTaskName() + " " + task2.getTaskDescription() +
                " id: " + task2.getId());
        System.out.println("");
        System.out.println(epic1.getTaskName() + " " + epic1.getTaskDescription() +
                " id: " + epic1.getId());
        System.out.println(subTask.getTaskName() + " " + subTask.getTaskDescription() +
                " id: " + subTask.getId());
        System.out.println(subTask2.getTaskName() + " " + subTask2.getTaskDescription() +
                " id: " + subTask2.getId());
        System.out.println(subTask3.getTaskName() + " " + subTask3.getTaskDescription() +
                " id: " + subTask3.getId());
        System.out.println("");
        System.out.println(epic2.getTaskName() + " " + epic2.getTaskDescription() +
                " id: " + epic2.getId());
        System.out.println("");

        //2 и 3. Запрос задач и вывод истории
        Epic epic1test = fileBackedTaskManager.findEpicById(epic1id);
        System.out.println("Кол-во записей в истории " + fileBackedTaskManager.history().size());
        Epic epic2test = fileBackedTaskManager.findEpicById(epic2id);
        System.out.println("Кол-во записей в истории " + fileBackedTaskManager.history().size());
        epic2test = fileBackedTaskManager.findEpicById(epic1id);
        Task task1test = fileBackedTaskManager.findById(taskId);
        Task task2test = fileBackedTaskManager.findById(taskId2);
        task2test = fileBackedTaskManager.findById(taskId2);
        System.out.println("Кол-во записей в истории " + fileBackedTaskManager.history().size());
        SubTask subtask1test = fileBackedTaskManager.findSubTaskById(subtask1Id);
        SubTask subtask2test = fileBackedTaskManager.findSubTaskById(subtask2Id);
        SubTask subtask3test = fileBackedTaskManager.findSubTaskById(subtask3Id);
        subtask3test = fileBackedTaskManager.findSubTaskById(subtask3Id);
        System.out.println("кол-во записей в истории " + fileBackedTaskManager.history().size());

        System.out.println("");

        fileBackedTaskManager.printHistory();
        fileBackedTaskManager.getAllTasks();
        fileBackedTaskManager.getAllEpic();
        fileBackedTaskManager.getSubTaskAll();
    }
}

