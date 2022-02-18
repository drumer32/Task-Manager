import managers.Managers;
import managers.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;

/**
 * TODO
 * Создать файл, который хранит список созданных и просмотренных объектов
 * Автосохранение (Override) TaskManagerBackup после каждого действия
 * История просмотров в файле хранит только ID задачи
 * Восстановление созданных задач и истории просмотров из файла
 *
 */

public class Main {
    public static void main(String[] args) {
        final TaskManager taskManager = Managers.getDefault();

        //1. СОЗДАНИЕ ЗАДАЧ

        Task task = new Task("Task1name", "Task1description");
        Task task2 = new Task("Task2name", "Task2description");

        final Task taskNew = taskManager.createTask(task);
        final Task task2New = taskManager.createTask(task2);
        final Integer taskId = taskNew.getId();
        final Integer taskId2 = task2New.getId();


        //СОЗДАНИЕ ЭПИКОВ

        Epic epic1 = new Epic("Epic1name", "Epic1description");
        Epic epic2 = new Epic("Epic2name", "Epic2description");

        final Epic epic1new = taskManager.createEpic(epic1);
        final Epic epic2new = taskManager.createEpic(epic2);

        final Integer epic1id = epic1new.getId();
        final Integer epic2id = epic2new.getId();

        //СОЗДАНИЕ САБТАСКОВ

        SubTask subTask = new SubTask("SubTask1", "Subtask1description", epic1id);
        SubTask subTask2 = new SubTask("SubTask2", "Subtask2description", epic1id);
        SubTask subTask3 = new SubTask("SubTask3", "Subtask3description", epic1id);

        final SubTask subTaskNew = taskManager.createSubTask(subTask);
        final SubTask subTask2New = taskManager.createSubTask(subTask2);
        final SubTask subTask3New = taskManager.createSubTask(subTask3);

        final Integer subtask1Id = subTaskNew.getId();
        final Integer subtask2Id = subTask2New.getId();
        final Integer subtask3Id = subTask3New.getId();

        //НАЧАЛО ВЫВОДА
        System.out.println(taskNew.getType() + " " + taskNew.getTaskName() + " " + taskNew.getTaskDescription() +
                " id: " + taskNew.getId());
        System.out.println(task2New.getTaskName() + " " + task2New.getTaskDescription() +
                " id: " + task2New.getId());
        System.out.println("");
        System.out.println(epic1new.getTaskName() + " " + epic1new.getTaskDescription() +
                " id: " + epic1new.getId());
        System.out.println(subTaskNew.getTaskName() + " " + subTaskNew.getTaskDescription() +
                " id: " + subTaskNew.getId());
        System.out.println(subTask2New.getTaskName() + " " + subTask2New.getTaskDescription() +
                " id: " + subTask2New.getId());
        System.out.println(subTask3New.getTaskName() + " " + subTask3New.getTaskDescription() +
                " id: " + subTask3New.getId());
        System.out.println("");
        System.out.println(epic2new.getTaskName() + " " + epic2new.getTaskDescription() +
                " id: " + epic2new.getId());
        System.out.println("");

        //2 и 3. Запрос задач и вывод истории
        Epic epic1test = taskManager.findEpicById(epic1id);
        System.out.println("Кол-во записей в истории " + taskManager.history().size());
        Epic epic2test = taskManager.findEpicById(epic2id);
        System.out.println("Кол-во записей в истории " + taskManager.history().size());
        epic2test = taskManager.findEpicById(epic1id);
        Task task1test = taskManager.findById(taskId);
        Task task2test = taskManager.findById(taskId2);
        task2test = taskManager.findById(taskId2);
        System.out.println("Кол-во записей в истории " + taskManager.history().size());
        SubTask subtask1test = taskManager.findSubTaskById(subtask1Id);
        SubTask subtask2test = taskManager.findSubTaskById(subtask2Id);
        SubTask subtask3test = taskManager.findSubTaskById(subtask3Id);
        subtask3test = taskManager.findSubTaskById(subtask3Id);
        System.out.println("кол-во записей в истории " + taskManager.history().size());

        System.out.println("");
        taskManager.printHistory();
        System.out.println("");

        taskManager.deleteTaskById(taskId);
        taskManager.deleteEpicById(epic1id);

        System.out.println("");
        System.out.println("Кол-во записей в истории " + taskManager.history().size());
        taskManager.printHistory();
    }
}
