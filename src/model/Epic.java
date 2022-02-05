package model;

import java.util.HashMap;

public class Epic extends Task {

    HashMap<Integer, SubTask> subtasks = new HashMap<>();

    public Epic(String taskName, String taskDescription, Integer id, Status status) {
        super(taskName, taskDescription, id, status);
    }

    public Epic(String taskName, String taskDescription) {
        super(taskName, taskDescription, null);
    }

    @Override
    public void setStatus(Status status) {
        super.setStatus(findStatus());
    }

    private Status findStatus() {
        return status;
    }

    public void addSubTask(SubTask subTask) {
        Integer epicId = subTask.getEpicId();
        subtasks.put(epicId, subTask);
    }

    public HashMap<Integer, SubTask> getSubTasks() {
        return subtasks;
    }

    public void deleteSubTasks() {
        subtasks.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic epic = (Epic) o;
        return id.equals(epic.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

