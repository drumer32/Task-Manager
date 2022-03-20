import managers.HistoryManager;
import managers.InMemoryHistoryManager;
import managers.Managers;
import managers.TaskManager;
import model.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
/**
 * TODO
* Пустая история задач.
* Дублирование.
* Удаление из истории: начало, середина, конец.
 */

public class HistoryManagerTest {

    HistoryManager historyManager = new InMemoryHistoryManager();
    Task task = new Task(1,"testName", "testDescription");
    Task task2 = new Task(2, "testName2", "testDescription2");
    Task task3 = new Task(3, "testName3", "testDescription3");

    @Test
    public void add() {
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }
    @Test
    public void sameHistoryNode() {
        historyManager.add(task);
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "Дублирование отсутствует");
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
