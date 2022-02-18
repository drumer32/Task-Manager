package managers;

import model.Epic;
import support.IdGenerator;
import model.SubTask;
import model.Task;

import java.util.*;

import static support.Status.NEW;

public class InMemoryTaskManager implements TaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap <>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();

    HistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    IdGenerator idGenerator = new IdGenerator();

    //	ПОЛУЧЕНИЕ списка всех задач.
    @Override
    public List<Task> getAll() {
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
    public HashMap<Integer, SubTask>  getSubTaskByEpic(Epic epic) {
        return epics.get(epic.getId()).getSubTasks();
    }
    
    @Override
    public HashMap<Integer, SubTask>  getSubTaskByEpicId(Integer epicId) {
        return epics.get(epicId).getSubTasks();
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

    //	СОЗДАНИЕ новой задачи, эпика и подзадачи

    @Override
    public Task createTask(Task task) {
        int id = idGenerator.generateId();
        final Task value = new Task(task.getType(), task.getTaskName(), task.getTaskDescription(), id, NEW);
        if (tasks.containsKey(task.getId())) {
            System.out.println("Такая задача существует id = " + task.getId());
            return null;
        }
        tasks.put(id, value);
        return value;
    }

    @Override
    public Epic createEpic(Epic epic) {
        int id = idGenerator.generateId();
        Epic epicNew = new Epic(epic.getType(), epic.getTaskName(), epic.getTaskDescription(), id, NEW);
        epics.put(id, epicNew);
        return epicNew;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        int epicId = subTask.getEpicId();
        if (!epics.containsKey(epicId)) {
            System.out.println("Не найден эпик id = " + epicId);
            return null;
        }
        int id = idGenerator.generateId();;
        final SubTask subTaskNew = new SubTask(subTask.getType(), subTask.getTaskName(),
                subTask.getTaskDescription(), id, NEW, epicId);
        subTasks.put(id, subTaskNew);
        final Epic epic = epics.get(epicId);
        epic.addSubTask(subTask);
        return subTaskNew;
        }

    //	Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде параметра.
    @Override
    public void updateTask(Task taskUpdated) {
        final Task taskSaved = tasks.get(taskUpdated.getId());
        if (taskSaved == null) {
            // Ошибка
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
            // Ошибка
            return;
        }
        epicUpdated.setTaskDescription(epicUpdated.getTaskDescription());
        epicUpdated.setTaskName(epicUpdated.getTaskName());
    }

    @Override
    public void updateSubTask(SubTask SubTaskUpdated) {
        final SubTask SubTaskSaved = subTasks.get(SubTaskUpdated.getId());
        if (SubTaskSaved == null) {
            // Ошибка
            return;
        }
        SubTaskSaved.setTaskDescription(SubTaskUpdated.getTaskDescription());
        SubTaskSaved.setTaskName(SubTaskUpdated.getTaskName());
        SubTaskSaved.setStatus(SubTaskUpdated.getStatus());
    }

    // Удаление ранее добавленных задач — всех и по идентификатору.
    @Override
    public void deleteTaskById(Integer id) {
        final Task task = tasks.remove(id);
        if (task == null) {
            return;
        }
        inMemoryHistoryManager.remove(id);
        System.out.println("Задача id " + id + " удалена");
    }

    @Override
    public void deleteSubTaskById(Integer id) {
        final SubTask subTask = subTasks.remove(id);
        if (subTask == null) {
            return;
        }
        final Epic epic = epics.get(subTask.getEpicId());
        epic.getSubTasks().remove(subTask);
        inMemoryHistoryManager.remove(id);
        System.out.println("Подзадача id " + id + " удалена");
    }

    public Set<Integer> keyFinder(Integer id) {
        Epic epic = epics.get(id);
        Integer key = 0;
        Set<Integer> keys = new HashSet<>();
        for (Integer subtaskInEpic : getSubTaskByEpic(epic).keySet()) {
            for (SubTask subTask : subTasks.values()) {
                if (subTask.getEpicId().equals(subtaskInEpic)) {
                    key = subTask.getId();
                    keys.add(key);
                }
            }
        } return keys;
    }

    @Override
    public void deleteEpicById(Integer id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            return;
        }
        for (Integer key : keyFinder(id)) {
            subTasks.remove(key);
            inMemoryHistoryManager.remove(key);
        }
        epics.remove(id);
        inMemoryHistoryManager.remove(id);
        System.out.println("Эпик id " + id + " удален");
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
