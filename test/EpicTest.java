import managers.InMemoryTaskManager;
import managers.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.IdGenerator;
import support.Status;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static support.Status.*;
import static support.TaskType.EPIC;
import static support.TaskType.SUBTASK;

/**
 * Пустой список подзадач.
 * Все подзадачи со статусом NEW.
 * Все подзадачи со статусом DONE.
 * Подзадачи со статусами NEW и DONE.
 * Подзадачи со статусом IN_PROGRESS.
 */

public class EpicTest {

    TaskManager taskManager = new InMemoryTaskManager();
    private final Epic epic = new Epic(1, EPIC, "name", NEW, "description");

    @BeforeEach
    public void createEpicForTest () {
        taskManager.createEpic(epic);
    }

    @Test
    public void nullSubtask () {
        Assertions.assertEquals(0, epic.getSubTasks().size());
    }

    @Test
    public void allNewSubtasks () {
        SubTask subTaskNew = new SubTask(2, SUBTASK,"", NEW, "");
        SubTask subTaskNew2 = new SubTask(3, SUBTASK , "", NEW, "");
        SubTask subTaskInProgress = new SubTask(4, SUBTASK,"", IN_PROGRESS, "");
        taskManager.createSubTask(subTaskNew, epic.getId());
        taskManager.createSubTask(subTaskNew2, epic.getId());
        taskManager.createSubTask(subTaskInProgress, epic.getId());
        List<Task> subtasksNew = new ArrayList<>();
        for (Task task : taskManager.getSubTaskAll()) {
            if (task.getStatus().equals(NEW)) {
                subtasksNew.add(task);
            }
        }
        Assertions.assertEquals(2, subtasksNew.size());
    }

    @Test
    public void allInProgressSubtasks () {
        SubTask subTaskNew = new SubTask(2, SUBTASK,"", NEW, "");
        SubTask subTaskInProgress = new SubTask(3, SUBTASK , "", IN_PROGRESS, "");
        SubTask subTaskInProgress2 = new SubTask(4, SUBTASK,"", IN_PROGRESS, "");
        taskManager.createEpic(new Epic(1, EPIC, "name", NEW, "description"));
        taskManager.createSubTask(subTaskNew, epic.getId());
        taskManager.createSubTask(subTaskInProgress2, epic.getId());
        taskManager.createSubTask(subTaskInProgress, epic.getId());
        List<Task> subtasksInProgress = new ArrayList<>();
        for (Task task : taskManager.getSubTaskAll()) {
            if (task.getStatus().equals(IN_PROGRESS)) {
                subtasksInProgress.add(task);
            }
        }
        Assertions.assertEquals(2, subtasksInProgress.size());
    }

    @Test
    public void allDoneSubtasks () {
        SubTask subTaskNew = new SubTask(2, SUBTASK,"", NEW, "", epic.getId());
        SubTask subTaskDone = new SubTask(3, SUBTASK , "", DONE, "", epic.getId());
        SubTask subTaskDone2 = new SubTask(4, SUBTASK,"", DONE, "", epic.getId());
        taskManager.createSubTask(subTaskNew, epic.getId());
        taskManager.createSubTask(subTaskDone, epic.getId());
        taskManager.createSubTask(subTaskDone2, epic.getId());
        List<Task> subtasksDone = new ArrayList<>();
        for (Task task : taskManager.getSubTaskAll()) {
            if (task.getStatus().equals(DONE)) {
                subtasksDone.add(task);
            }
        }
        Assertions.assertEquals(2, subtasksDone.size());
    }

    @Test
    public void allNewAndDoneSubtasks () {
        SubTask subTaskNew = new SubTask(2, SUBTASK,"", NEW, "", epic.getId());
        SubTask subTaskDone = new SubTask(3, SUBTASK , "", DONE, "", epic.getId());
        SubTask subTaskInProgress = new SubTask(4, SUBTASK,"", DONE, "", epic.getId());
        taskManager.createSubTask(subTaskNew, epic.getId());
        taskManager.createSubTask(subTaskDone, epic.getId());
        taskManager.createSubTask(subTaskInProgress, epic.getId());
        List<Task> subtasksDone = new ArrayList<>();
        for (Task task : taskManager.getSubTaskAll()) {
            if (task.getStatus().equals(DONE) ||
            task.getStatus().equals(NEW)) {
                subtasksDone.add(task);
            }
        }
        Assertions.assertEquals(3, subtasksDone.size());
    }
}
