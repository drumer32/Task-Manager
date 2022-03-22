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
        super(taskDescription, taskName);
    }

    public Epic(Task task, HashMap<Integer, SubTask> subtasks) {
        super(task);
        this.subtasks = subtasks;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void addSubTask(SubTask subTask) {
        Integer epicId = subTask.getEpicId();
        subtasks.put(epicId, subTask);
    }

    @Override
    public TaskType getType() {
        return type;
    }
    public void setType(TaskType type) {
        this.type = type;
    }
    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public void setId(Integer id) {
        this.id = id;
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