package model;
import managers.FileBackedTaskManager;
import support.IdGenerator;
import support.Status;
import support.TaskType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import static support.Status.NEW;
import static support.TaskType.TASK;

public class Task {
    private String taskName;
    private String taskDescription;
    protected Integer id;
    protected Status status;
    protected TaskType type;


    public Task() {
    }

    public Task(Integer id, String taskName) {
        this(id,TASK, taskName, NEW, "");
    }

    public Task(Integer id, String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.id = id;
        this.status = NEW;
        this.type = TASK;
    }

    public Task(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.id = null;
        this.status = NEW;
        this.type = TASK;
    }


    public Task(Integer id, TaskType type, String taskName, Status status, String taskDescription) {
        this.type = TASK;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.id = id;
        this.status = status;
    }

    public Task(Task task) {
        this.taskName = task.taskName;
        this.taskDescription = task.taskDescription;
        this.id = task.id;
        this.status = task.status;
        this.type = task.type;
    }

    public Task(String taskName, String taskDescription, Integer id, Status status) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.id = id;
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TaskType getType() {
        return type;
    }
    public void setType(TaskType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskName, task.taskName)
                && Objects.equals(taskDescription, task.taskDescription)
                && Objects.equals(id, task.id)
                && status == task.status
                && type == task.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, taskDescription, id, status);
    }

    @Override
    public String toString() {
        return id + "," +
                type + "," +
                taskName + "," +
                status + "," +
                taskDescription
                ;
    }
}
