package managers;

import model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static support.Status.NEW;
import static support.TaskType.*;

public class FileBackedTaskManager extends InMemoryTaskManager {

    String filename;

    public FileBackedTaskManager(String filename) {
        this.filename = filename;
    }

    public void save() {

        StringBuilder taskSaved = new StringBuilder();

        for (Task a : tasks.values()) {
            taskSaved.append(a.toString());
            taskSaved.append("\n");
        }
        for (Task a : subTasks.values()) {
            taskSaved.append(a.toString());
            taskSaved.append("\n");
        }
        for (Task a : epics.values()) {
            taskSaved.append(a.toString());
            taskSaved.append("\n");
        }
        taskSaved.append("\n");
        for (Task a : history()) {
            taskSaved.append(a.getId().toString());
            taskSaved.append(",");
        }

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(String.valueOf(taskSaved));//перезапись файла
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public List<Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public List<Task> getAllEpic() {
        return super.getAllEpic();
    }

    @Override
    public List<Task> getSubTaskAll() {
        return super.getSubTaskAll();
    }

    @Override
    public HashMap<Integer, SubTask> getSubTaskByEpic(Epic epic) {
        return super.getSubTaskByEpic(epic);
    }

    @Override
    public HashMap<Integer, SubTask> getSubTaskByEpicId(Integer epicId) {
        return super.getSubTaskByEpicId(epicId);
    }

    @Override
    public Task findById(Integer id) {
        final Task task = tasks.get(id);
        inMemoryHistoryManager.add(task);
        save();
        return task;
    }

    @Override
    public SubTask findSubTaskById(Integer id) {
        final SubTask subTask = subTasks.get(id);
        inMemoryHistoryManager.add(subTask);
        save();
        return subTask;
    }

    @Override
    public Epic findEpicById(Integer id) {
        final Epic epic = epics.get(id);
        inMemoryHistoryManager.add(epic);
        save();
        return epic;
    }

    @Override
    public Task createTask(Task task) {
        int id = idGenerator.generateId();
        final Task value = new Task(TASK, task.getTaskName(), task.getTaskDescription(), id, NEW);
        if (tasks.containsKey(task.getId())) {
            System.out.println("Такая задача существует id = " + task.getId());
            return null;
        }
        tasks.put(id, value);
        save();
        return value;
    }

    @Override
    public Epic createEpic(Epic epic) {
        int id = idGenerator.generateId();
        Epic epicNew = new Epic(EPIC, epic.getTaskName(), epic.getTaskDescription(), id, NEW);
        epics.put(id, epicNew);
        save();
        return epicNew;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        if (!epics.containsKey(subTask.getEpicId())) {
            System.out.println("Не найден эпик id = " + subTask.getEpicId());
            return null;
        }
        int id = idGenerator.generateId();;
        final SubTask subTaskNew = new SubTask(SUBTASK, subTask.getTaskName(),
                subTask.getTaskDescription(), id, NEW, subTask.getEpicId());
        subTasks.put(id, subTaskNew);
        final Epic epic = epics.get(subTask.getEpicId());
        epic.addSubTask(subTask);
        save();
        return subTaskNew;
    }

    @Override
    public void updateTask(Task taskUpdated) {
        super.updateTask(taskUpdated);
    }

    @Override
    public void updateEpic(Epic epicUpdated) {
        super.updateEpic(epicUpdated);
    }

    @Override
    public void updateSubTask(SubTask SubTaskUpdated) {
        super.updateSubTask(SubTaskUpdated);
    }

    @Override
    public void deleteTaskById(Integer id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteSubTaskById(Integer id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public Set<Integer> keyFinder(Integer id) {
        return super.keyFinder(id);
    }

    @Override
    public void deleteEpicById(Integer id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public List<Task> history() {
        return inMemoryHistoryManager.getHistory();
    }

    @Override
    public void printHistory() {
        super.printHistory();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
