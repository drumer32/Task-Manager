import managers.*;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.TaskGenerator;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryManagerTest {

    HistoryManager historyManager = new InMemoryHistoryManager();
    TaskManager taskManager = new InMemoryTaskManager();


    private static Task task;
    private static Task task2;
    private static Task task3;

    @BeforeAll
    static void generateTasks() {
        TaskGenerator taskGenerator = new TaskGenerator();
        task = taskGenerator.generateTask24Hours(LocalDateTime.of(2022, 1, 1, 15, 30));
        task2 = taskGenerator.generateTask24Hours(LocalDateTime.of(2022, 2, 3, 15, 30));
        task3 = taskGenerator.generateTask24Hours(LocalDateTime.of(2022, 7, 3, 15, 30));
    }

    @BeforeEach
    public void createTasks() {
        taskManager.createTask(task);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
    }
    @AfterEach
    public void clear() {
        taskManager.clearAll();
    }

    @Test
    public void add() {
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
    }

    @Test
    public void sameHistoryNode() {
        historyManager.add(task);
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
    }

    @Test
    public void removeFirstNode() {
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task.getId());
        assertEquals(List.of(task3, task2), historyManager.getHistory());
    }

    @Test
    public void removeMiddleNode() {
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task2.getId());
        assertEquals(List.of(task3, task), historyManager.getHistory());
    }

    @Test
    public void removeLastNode() {
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task3.getId());
        assertEquals(List.of(task2, task), historyManager.getHistory());
    }
}
