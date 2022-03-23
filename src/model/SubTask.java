package model;

import managers.FileBackedTaskManager;
import support.Status;
import support.TaskType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static support.Status.NEW;
import static support.TaskType.SUBTASK;

public class SubTask extends Task {
    Integer epicId;

    public SubTask(Integer epicId) {
        this.epicId = epicId;
    }

    public SubTask(Integer id, TaskType type, String name, Status status, String description, Integer epicId) {
        super(name, description, id, status);
        this.type = type;
        this.epicId = epicId;
    }

    public SubTask(Integer id, TaskType type, String name, Status status, String description) {
        super(name, description, id, status);
        this.type = type;
    }

    public SubTask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public SubTask(String name, String description) {
        super(name, description);
    }

    public SubTask(Integer id, String taskName) {
        super(id, taskName);
    }


    public SubTask(Task task, Integer epicId) {
        super(task);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }
    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
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
    @Override
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return Objects.equals(epicId, subTask.epicId);
    }

    @Override
    public int hashCode() {
        return epicId.hashCode();
    }

    @Override
    public String toString() {
        return super.toString()
                +","+ epicId
                ;
    }

}