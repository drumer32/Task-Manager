import managers.inmemory.InMemoryTaskManager;
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
import java.util.ArrayList;
import java.util.List;

import static support.Status.*;

public class EpicTest {

    TaskManager taskManager = new InMemoryTaskManager();
    TaskGenerator taskGenerator = new TaskGenerator();
    private final Epic epic = taskGenerator.generateEpic();

    @BeforeEach
    public void createEpicForTest () {
        taskManager.createEpic(epic);
    }
    @AfterEach
    public void clear() {
        taskManager.clearAll();
    }

    @Test
    public void nullSubtask () {
        Assertions.assertEquals(0, epic.getSubTasksIds().size());
    }

    @Test
    public void allNewSubtasks () {
        SubTask subTaskNew = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2020, 3, 24, 15, 30));
        SubTask subTaskNew2 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2021, 3, 31, 15, 30));
        SubTask subTaskInProgress = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 3, 20, 15, 30));
        taskManager.createSubTask(subTaskNew, epic.getId());
        taskManager.createSubTask(subTaskNew2, epic.getId());
        taskManager.createSubTask(subTaskInProgress, epic.getId());
        subTaskInProgress.setStatus(IN_PROGRESS);
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
        SubTask subTaskNew = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 3, 24, 15, 30));
        SubTask subTaskInProgress2 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 3, 31, 15, 30));
        SubTask subTaskInProgress = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 3, 20, 15, 30));
        taskManager.createSubTask(subTaskNew, epic.getId());
        taskManager.createSubTask(subTaskInProgress2, epic.getId());
        taskManager.createSubTask(subTaskInProgress, epic.getId());
        subTaskInProgress.setStatus(IN_PROGRESS);
        subTaskInProgress2.setStatus(IN_PROGRESS);
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
        SubTask subTaskNew = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2020, 3, 24, 15, 30));
        SubTask subTaskDone = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2021, 3, 31, 15, 30));
        SubTask subTaskDone2 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 3, 20, 15, 30));
        taskManager.createSubTask(subTaskNew, epic.getId());
        taskManager.createSubTask(subTaskDone, epic.getId());
        taskManager.createSubTask(subTaskDone2, epic.getId());
        subTaskDone.setStatus(DONE);
        subTaskDone2.setStatus(DONE);
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
        SubTask subTaskNew = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2020, 3, 24, 15, 30));
        SubTask subTaskDone = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2021, 3, 31, 15, 30));
        SubTask subTaskDone2 = taskGenerator.generateSubtask24Hours(LocalDateTime.of(2022, 3, 20, 15, 30));
        taskManager.createSubTask(subTaskNew, epic.getId());
        taskManager.createSubTask(subTaskDone, epic.getId());
        taskManager.createSubTask(subTaskDone2, epic.getId());
        subTaskDone.setStatus(DONE);
        subTaskDone2.setStatus(DONE);
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
