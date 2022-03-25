import managers.FileBackedTaskManager;
import managers.Managers;
import managers.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import support.TaskGenerator;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static support.Status.DONE;


public class Main {
    private static final Integer IN_MEMORY = 1;

    public static void main(String[] args) {
        final TaskManager taskManager = Managers.getDefault(IN_MEMORY);

        TaskGenerator taskGenerator = new TaskGenerator();

        Task task = taskGenerator.generateTask24Hours(LocalDateTime.of(2020, 3, 1, 15, 30));
        Task task2 = taskGenerator.generateTask24Hours(LocalDateTime.of(2021, 3, 3, 15, 30));
        Epic epic1 = taskGenerator.generateEpic();
        Epic epic2 = taskGenerator.generateEpic();
        SubTask subTask = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2019, 3, 12, 15, 30));
        SubTask subTask2 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 3, 15, 15, 30));
        SubTask subTask3 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2018, 3, 18, 15, 30));

            taskManager.createTask(task);
            taskManager.createTask(task2);
            taskManager.createEpic(epic1);
            taskManager.createEpic(epic2);
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

        //НАЧАЛО ВЫВОДА
        System.out.println(task.getType() + " " + task.getTaskName() + " " + task.getTaskDescription() +
                " id: " + task.getId() + " st/time " + task.getStartTime()
                + " Dur " + task.getDuration().toString() + "end " + task.getEndTime());
        System.out.println(task2.getType() + " " + task2.getTaskName() + " " + task2.getTaskDescription() +
                " id: " + task2.getId());
        System.out.println("");
        System.out.println(epic1.getType() + " " + epic1.getTaskName() + " " + epic1.getTaskDescription() +
                " id: " + epic1.getId());
        System.out.println(subTask.getType() + " " + subTask.getTaskName() + " " + subTask.getTaskDescription() +
                " id: " + subTask.getId());
        System.out.println(subTask2.getTaskName() + " " + subTask2.getTaskDescription() +
                " id: " + subTask2.getId());
        System.out.println(subTask3.getTaskName() + " " + subTask3.getTaskDescription() +
                " id: " + subTask3.getId() + " " + subTask3.getEpicId());
        System.out.println("");
        System.out.println(epic2.getTaskName() + " " + epic2.getTaskDescription() +
                " id: " + epic2.getId());
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
        System.out.println(taskManager.getPrioritizedTasks());
        System.out.println(epic1.getStartTime() + " " + epic1.getDuration() + " " + epic1.getEndTime());

//        System.out.println("");
//        taskManager.printHistory();
//        System.out.println("");
//
//        taskManager.deleteSubTaskById(subtask1Id);
//        taskManager.getSubTaskAll();
//
//        System.out.println("");
//        System.out.println("Кол-во записей в истории " + taskManager.history().size());
        taskManager.printHistory();
    }
}
