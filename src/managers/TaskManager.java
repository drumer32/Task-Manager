package managers;

import model.Epic;
import model.SubTask;
import model.Task;
import java.util.List;
import java.util.Map;

public interface TaskManager {

    List<Task> getAll();

    List<Task> getAllEpic();

    List<Task> getSubTaskAll();

    Map<Integer, SubTask> getSubTaskByEpic(Epic epic);

    Map<Integer, SubTask>   getSubTaskByEpicId(Integer epicId);

    Task findById(Integer id);

    SubTask findSubTaskById(Integer id);

    Epic findEpicById(Integer id);

    Task createTask(Task task);

    Epic createEpic(Epic epic);

    SubTask createSubTask(SubTask subTask);

    void updateTask(Task taskUpdated);

    void updateEpic(Epic epicUpdated);

    void updateSubTask(SubTask SubTaskUpdated);

    void deleteTaskById(Integer id);

    void deleteSubTaskById(Integer id);

    void deleteEpicById(Integer id);

    List<Task> history();

    void printHistory();
}
