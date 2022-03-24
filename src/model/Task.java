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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import static support.Status.NEW;
import static support.TaskType.TASK;

public class Task implements Comparable<Task>{
    private String taskName;
    private String taskDescription;
    protected Integer id;
    protected Status status;
    protected TaskType type;
    private Duration duration;
    private LocalDateTime startTime;

    public Task() {
    }

    public Task(Integer id, String taskName) {
        this(id, TASK, taskName, NEW, "");
    }

    public Task(Integer id, String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.id = id;
        this.status = NEW;
        this.type = TASK;
        this.duration = Duration.ZERO;
    }

    public Task(String taskName, String taskDescription, Duration duration) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = NEW;
        this.type = TASK;
        this.duration = duration;
    }


    public Task(Integer id, TaskType type, String taskName, Status status, String taskDescription) {
        this.type = TASK;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.id = id;
        this.status = status;
        this.duration = Duration.ZERO;
    }

    public Task(Task task) {
        this.taskName = task.taskName;
        this.taskDescription = task.taskDescription;
        this.id = task.id;
        this.status = task.status;
        this.type = task.type;
        this.duration = task.duration;
    }

    public Task(String taskName, String taskDescription, Integer id, Status status) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.id = id;
        this.status = status;
        this.duration = Duration.ZERO;
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

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        if (startTime != null) {
            this.startTime = LocalDateTime.parse(startTime);
        } else {
            this.startTime = LocalDateTime.now();
        }
    }

    public LocalDateTime getFinishTime() {
        if (getStartTime() == null) {
            return null;
        }
        return getStartTime().plus(getDuration());
    }

    public void setDurationOfHours(Duration durationOfHours) {
        this.duration = durationOfHours;
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
                taskDescription + "," +
                startTime + "," +
                duration
                ;
    }

    @Override
    public int compareTo(Task o) {
        if (this.getId() == o.getId()) {
            return 0;
        }
        if (this.getStartTime() == null && o.getStartTime() == null) {
            return 1;
        }
        if (this.getStartTime() != null && o.getStartTime() == null) {
            return -1;
        }
        if (this.getStartTime() == null && o.getStartTime() != null) {
            return 1;
        }

        if (this.getStartTime().isEqual(o.getStartTime())) {
            return 1;
        }

        return this.getStartTime().compareTo(o.getStartTime());
    }
}
