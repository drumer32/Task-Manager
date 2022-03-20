package model;

import managers.FileBackedTaskManager;
import support.Status;
import support.TaskType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Epic extends Task {

    HashMap<Integer, SubTask> subtasks = new HashMap<>();

    public Epic(Integer id, TaskType type, String taskName, Status status, String taskDescription) {
        super(taskName, taskDescription, id, status);
        this.type = type;
    }

    public Epic(String taskName, String taskDescription) {
        super(null, taskDescription, taskName);
    }

    public Epic(Task task, HashMap<Integer, SubTask> subtasks) {
        super(task);
        this.subtasks = subtasks;
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

    @Override
    public String toString() {
        return super.toString();
    }


}