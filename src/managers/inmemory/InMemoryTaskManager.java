package managers.inmemory;

import managers.HistoryManager;
import managers.TaskManager;
import model.Epic;
import support.IdGenerator;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

import static support.Status.*;
import static support.TaskType.*;

public class InMemoryTaskManager implements TaskManager {

    protected HashMap<Integer, Task> tasks = new HashMap <>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, SubTask> subTasks = new HashMap<>();
    protected TreeMap<Integer, Task> sortedTasks = new TreeMap<>();

    HistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    IdGenerator idGenerator = new IdGenerator();

    //	ПОЛУЧЕНИЕ списка всех задач.
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Task> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Task> getSubTaskAll() {
        return new ArrayList<>(subTasks.values());
    }
    //	ПОЛУЧЕНИЕ списка всех подзадач определённого эпика.
    @Override
    public List<Integer> getSubTaskByEpic(Epic epic) {
        return epics.get(epic.getId()).getSubTasksIds();
    }
    
    @Override
    public List<Integer> getSubTaskByEpicId(Integer epicId) {
        return epics.get(epicId).getSubTasksIds();
    }

    @Override
    public Task getById(Integer id) {
        Task task = null;
        if (sortedTasks.containsKey(id)) {
            task = sortedTasks.get(id);
        }
        return task;
    }

    //	Получение задачи любого типа ПО ИДЕНТИФИКАТОРУ.
    @Override
    public Task findById(Integer id) {
        final Task task = tasks.get(id);
        inMemoryHistoryManager.add(task);
        return task;
    }

    @Override
    public SubTask findSubTaskById(Integer id) {
        final SubTask subTask = subTasks.get(id);
        inMemoryHistoryManager.add(subTask);
        return subTask;
    }

    @Override
    public Epic findEpicById(Integer id) {
        final Epic epic = epics.get(id);
        inMemoryHistoryManager.add(epic);
        return epic;
    }

    @Override
    public TreeMap<Integer, Task> getPrioritizedTasks() {
        sortedTasks.putAll(tasks);
        sortedTasks.putAll(epics);
        sortedTasks.putAll(subTasks);
        return sortedTasks;
    }

    private final Predicate<Task> noIntersection = newTask -> {

        LocalDateTime newTaskStart = newTask.getStartTime();
        LocalDateTime newTaskFinish = newTask.getEndTime();

        for (Task task : sortedTasks.values()) {

            LocalDateTime taskStart = task.getStartTime();
            LocalDateTime taskFinish = task.getEndTime();

            if (newTaskStart.isBefore(taskStart) && newTaskFinish.isAfter(taskStart)) {
                return false;
            }

            if (newTaskStart.isBefore(taskFinish) && newTaskFinish.isAfter(taskFinish)) {
                return false;
            }

            if ((newTaskStart.isBefore(taskStart) && newTaskFinish.isBefore(taskStart)) &&
                    (newTaskStart.isBefore(taskFinish) && newTaskFinish.isBefore(taskFinish))) {
                break;
            }
        }
        return true;
    };

    @Override
    public Task createTask(Task task) {
        Integer id = idGenerator.generateId();
        task.setId(id);
        task.setStatus(NEW);
        task.setType(TASK);
        if (tasks.containsKey(task.getId())) {
            System.out.println("Такая задача существует id = " + task.getId());
            return null;
        }
        if (noIntersection.test(task)) {
            tasks.put(id, task);
            sortedTasks.put(id, task);
        } else {
            System.out.println("Время уже занято");
        }
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        Integer id = idGenerator.generateId();
        epic.setId(id);
        epic.setStatus(NEW);
        epic.setType(EPIC);
        epic.setDurationOfHours(Duration.ZERO);
        epic.setStartTime(String.valueOf(LocalDateTime.now()));
        if (epics.containsKey(epic.getId())) {
            System.out.println("Такая задача существует id = " + epic.getId());
            return null;
        }
        epics.put(id, epic);
        sortedTasks.put(id, epic);
        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask, Integer epicId) {
        if (!epics.containsKey(epicId)) {
            System.out.println("Не найден эпик id = " + epicId);
            return null;
        }
        int id = idGenerator.generateId();
        subTask.setId(id);
        subTask.setStatus(NEW);
        subTask.setType(SUBTASK);
        subTask.setEpicId(epicId);
        if (noIntersection.test(subTask)) {
            subTasks.put(id, subTask);
            sortedTasks.put(id, subTask);
            final Epic epic = epics.get(epicId);
            epic.addSubTask(subTask);
        } else {
            System.out.println("Время уже занято");
        }
        return subTask;
    }

    //	Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде параметра.
    @Override
    public void updateTask(Task taskUpdated) {
        final Task taskSaved = tasks.get(taskUpdated.getId());
        if (taskSaved == null) {
            return;
        }
        taskSaved.setTaskDescription(taskUpdated.getTaskDescription());
        taskSaved.setTaskName(taskUpdated.getTaskName());
        taskSaved.setStatus(taskUpdated.getStatus());
    }

    @Override
    public void updateEpic(Epic epicUpdated) {
        final Epic epicSaved = epics.get(epicUpdated.getId());
        if (epicSaved == null) {
            return;
        }
        epicSaved.setTaskDescription(epicUpdated.getTaskDescription());
        epicSaved.setTaskName(epicUpdated.getTaskName());
    }

    @Override
    public void updateSubTask(SubTask subTaskUpdated) {
        if (subTaskUpdated == null) {
            return;
        }
        final SubTask subTaskSaved = subTasks.get(subTaskUpdated.getId());
        subTaskSaved.setTaskDescription(subTaskUpdated.getTaskDescription());
        subTaskSaved.setTaskName(subTaskUpdated.getTaskName());
        subTaskSaved.setStatus(subTaskUpdated.getStatus());
        Epic epic = epics.get(subTaskSaved.getEpicId());
        if (!epic.getStatuses().contains(NEW) || !epic.getStatuses().contains(IN_PROGRESS)) {
            epic.setStatus(DONE);
        }
    }

    // Удаление ранее добавленных задач — всех и по идентификатору.
    @Override
    public void deleteTaskById(Integer id) {
        final Task task = tasks.remove(id);
        if (task == null) {
            System.out.println("Not found");
        }
        inMemoryHistoryManager.remove(id);
        System.out.println("Задача id " + id + " удалена");
    }

    @Override
    public void deleteSubTaskById(Integer id) {
        final SubTask subTask = subTasks.get(id);
        if (subTask == null) {
            System.out.println("Not found");
        }
        final Epic epic = epics.get(subTask.getEpicId());
        epic.getSubTasksIds().remove(subTask.getId());
        subTasks.remove(id);
        inMemoryHistoryManager.remove(id);
        System.out.println("Подзадача id " + id + " удалена");
    }

    @Override
    public void deleteEpicById(Integer id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            System.out.println("Not found");
        }
        if (epic.getSubTasksIds() != null) {
            for (Integer key : epic.getSubTasksIds()) {
                subTasks.remove(key);
                inMemoryHistoryManager.remove(key);
                System.out.println("Подзадача id " + key + " удалена");
            }
        }
        epics.remove(id);
        epic.deleteSubTasks();
        inMemoryHistoryManager.remove(id);
        System.out.println("Эпик id " + id + " удален");
    }

    @Override
    public void clearAll() {
        tasks.clear();
        subTasks.clear();
        epics.clear();
    }

    //Печать истории
    @Override
    public List<Task> history() {
        return inMemoryHistoryManager.getHistory();
    }

    //Печатает историю в виде названий задач. Так красивее, чем просто List<Task>
    @Override
    public void printHistory() {
        System.out.println("Просмотренные задачи: ");
        for (Task task : history()) {
            System.out.println(task.getTaskName());
        }
    }

    @Override
    public void loadFromFile(String taskSavedBackup) throws IOException {
        System.out.println("Функция недоступна для текущего менеджера. " +
                "Запустите FileBackedTaskManager для сохранения истории.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryTaskManager that = (InMemoryTaskManager) o;
        return Objects.equals(idGenerator, that.idGenerator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGenerator);
    }
}
