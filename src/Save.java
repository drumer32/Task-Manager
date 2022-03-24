import managers.FileBackedTaskManager;
import managers.InMemoryTaskManager;
import managers.Managers;
import managers.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.time.Duration;

import static support.Status.DONE;

public class Save{

    private static final Integer IN_FILE = 2;

    public static void main(String[] args) throws IOException {

        final TaskManager fileBackedTaskManager = Managers.getDefault(IN_FILE);

        fileBackedTaskManager.loadFromFile("TaskSavedBackup");

//        //1. СОЗДАНИЕ ЗАДАЧ
//
//        Task task = new Task
//                ("Task1name", "Task1description", Duration.ofMinutes(2000));
//        Task task2 = new Task
//                ("Task2name", "Task2description", Duration.ofMinutes(2000));
//
//        final Task taskNew = fileBackedTaskManager.createTask(task);
//        final Task task2New = fileBackedTaskManager.createTask(task2);
//        final Integer taskId = taskNew.getId();
//        final Integer taskId2 = task2New.getId();
//
//
//        //СОЗДАНИЕ ЭПИКОВ
//
//        Epic epic1 = new Epic
//                ("Epic1name", "Epic1description", Duration.ofMinutes(2000));
//        Epic epic2 = new Epic
//                ("Epic2name", "Epic2description", Duration.ofMinutes(2000));
//
//        final Epic epic1new = fileBackedTaskManager.createEpic(epic1);
//        final Epic epic2new = fileBackedTaskManager.createEpic(epic2);
//
//        final Integer epic1id = epic1new.getId();
//        final Integer epic2id = epic2new.getId();
//
//        //СОЗДАНИЕ САБТАСКОВ
//
//        SubTask subTask = new SubTask("SubTask1", "Subtask1description", Duration.ofMinutes(2000));
//        SubTask subTask2 = new SubTask("SubTask2", "Subtask2description", Duration.ofMinutes(2000));
//        SubTask subTask3 = new SubTask("SubTask3", "Subtask3description", Duration.ofMinutes(2000));
//
//        final SubTask subTaskNew = fileBackedTaskManager.createSubTask(subTask, epic1id);
//        final SubTask subTask2New = fileBackedTaskManager.createSubTask(subTask2, epic1id);
//        final SubTask subTask3New = fileBackedTaskManager.createSubTask(subTask3, epic1id);
//
//        final Integer subtask1Id = subTaskNew.getId();
//        final Integer subtask2Id = subTask2New.getId();
//        final Integer subtask3Id = subTask3New.getId();
//
//        //НАЧАЛО ВЫВОДА
//        System.out.println(taskNew.getTaskName() + " " + taskNew.getTaskDescription() +
//                " id: " + taskNew.getId() + " " + taskNew.getStatus());
//        System.out.println(task2New.getTaskName() + " " + task2New.getTaskDescription() +
//                " id: " + task2New.getId());
//        System.out.println("");
//        System.out.println(epic1new.getTaskName() + " " + epic1new.getTaskDescription() +
//                " id: " + epic1new.getId());
//        System.out.println(subTaskNew.getTaskName() + " " + subTaskNew.getTaskDescription() +
//                " id: " + subTaskNew.getId());
//        System.out.println(subTask2New.getTaskName() + " " + subTask2New.getTaskDescription() +
//                " id: " + subTask2New.getId());
//        System.out.println(subTask3New.getTaskName() + " " + subTask3New.getTaskDescription() +
//                " id: " + subTask3New.getId());
//        System.out.println("");
//        System.out.println(epic2new.getTaskName() + " " + epic2new.getTaskDescription() +
//                " id: " + epic2new.getId());
//        System.out.println("");
//
//        //2 и 3. Запрос задач и вывод истории
//        Epic epic1test = fileBackedTaskManager.findEpicById(epic1id);
//        System.out.println("Кол-во записей в истории " + fileBackedTaskManager.history().size());
//        Epic epic2test = fileBackedTaskManager.findEpicById(epic2id);
//        System.out.println("Кол-во записей в истории " + fileBackedTaskManager.history().size());
//        epic2test = fileBackedTaskManager.findEpicById(epic1id);
//        Task task1test = fileBackedTaskManager.findById(taskId);
//        Task task2test = fileBackedTaskManager.findById(taskId2);
//        task2test = fileBackedTaskManager.findById(taskId2);
//        System.out.println("Кол-во записей в истории " + fileBackedTaskManager.history().size());
//        SubTask subtask1test = fileBackedTaskManager.findSubTaskById(subtask1Id);
//        SubTask subtask2test = fileBackedTaskManager.findSubTaskById(subtask2Id);
//        SubTask subtask3test = fileBackedTaskManager.findSubTaskById(subtask3Id);
//        subtask3test = fileBackedTaskManager.findSubTaskById(subtask3Id);
//        System.out.println("кол-во записей в истории " + fileBackedTaskManager.history().size());
//
//        System.out.println("");
//        Task taskUpdated = new Task("Task1name", "Task1description", 1, DONE);
//        fileBackedTaskManager.updateTask(taskUpdated);

        fileBackedTaskManager.printHistory();
        fileBackedTaskManager.getAllTasks();
        fileBackedTaskManager.getAllEpic();
        fileBackedTaskManager.getSubTaskAll();
    }
}

