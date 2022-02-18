package managers;

import model.Epic;
import model.SubTask;
import model.Task;
import support.TaskType;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private void save() {
        System.out.println("hello");
    }

    @Override
    public List<Task> getAll() {
        return super.getAll();
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
        save();
        return super.findById(id);
    }

    @Override
    public SubTask findSubTaskById(Integer id) {
        save();
        return super.findSubTaskById(id);
    }

    @Override
    public Epic findEpicById(Integer id) {
        save();
        return super.findEpicById(id);
    }

    @Override
    public Task createTask(Task task) {
        save();
        return super.createTask(task);

    }

    @Override
    public Epic createEpic(Epic epic) {
        save();
        return super.createEpic(epic);
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        save();
        return super.createSubTask(subTask);
    }

    @Override
    public void updateTask(Task taskUpdated) {
        super.updateTask(taskUpdated);
        save();
    }

    @Override
    public void updateEpic(Epic epicUpdated) {
        super.updateEpic(epicUpdated);
        save();
    }

    @Override
    public void updateSubTask(SubTask SubTaskUpdated) {
        super.updateSubTask(SubTaskUpdated);
        save();
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
        return super.history();
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
