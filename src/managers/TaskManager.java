package managers;

import model.Epic;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

public interface TaskManager {

    List<Task> getAllTasks();

    List<Task> getAllEpic();

    List<Task> getSubTaskAll();

    List<Integer> getSubTaskByEpic(Epic epic);

    List<Integer> getSubTaskByEpicId(Integer epicId);

    Task getById(Integer id);

    Task findById(Integer id);

    SubTask findSubTaskById(Integer id);

    Epic findEpicById(Integer id);

    //	СОЗДАНИЕ новой задачи, эпика и подзадачи
    TreeMap<Integer, Task> getPrioritizedTasks();

    Task createTask(Task task);

    Epic createEpic(Epic epic);

    SubTask createSubTask(SubTask subTask, Integer epicId);

    void updateTask(Task taskUpdated);

    void updateEpic(Epic epicUpdated);

    void updateSubTask(SubTask SubTaskUpdated);

    void deleteTaskById(Integer id);

    void deleteSubTaskById(Integer id);

    void deleteEpicById(Integer id);

    void clearAll();

    List<Task> history();

    void printHistory();

    void loadFromFile(String taskSavedBackup) throws IOException;
}
