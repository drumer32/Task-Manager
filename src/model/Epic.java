package model;

import managers.FileBackedTaskManager;
import support.Status;
import support.TaskType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Epic extends Task {

    List<Integer> subtasks = new ArrayList<>();
    List<Status> statuses = new ArrayList<>();

    public Epic(Integer id, TaskType type, String taskName, Status status, String taskDescription) {
        super(taskName, taskDescription, id, status);
        this.type = type;
    }

    public Epic(String taskName, String taskDescription) {
        super(taskDescription, taskName);
    }

    public Epic(Task task, List<Integer> subtasks) {
        super(task);
        this.subtasks = subtasks;
    }

    public Epic(Integer id, String taskName) {
        super(id, taskName);
    }

    public Status getStatus() {
        return status;
    }

    public List<Status> getStatuses () {
        return statuses;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void addSubTask(SubTask subTask) {
        subtasks.add(subTask.getId());
        statuses.add(subTask.getStatus());
    }

    public List<Integer> getSubTasks() {
        return subtasks;
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