package model;

import support.Status;
import support.TaskType;

import java.util.HashMap;

public class Epic extends Task {

    HashMap<Integer, SubTask> subtasks = new HashMap<>();

    public Epic(TaskType type, String taskName, String taskDescription, Integer id, Status status) {
        super(taskName, taskDescription, id, status);
        this.type = type;
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

    @Override
    public TaskType getType() {
        return type;
    }

    public Integer getId() {
        return id;
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

