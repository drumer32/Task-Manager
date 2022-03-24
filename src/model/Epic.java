package model;

import managers.FileBackedTaskManager;
import support.Status;
import support.TaskType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Epic extends Task {

    private List<Integer> subtasksIds = new ArrayList<>();
    private final List<Status> statuses = new ArrayList<>();
    private HashMap<Integer, SubTask> subtasks = new HashMap<>();

    public Epic(Integer id, TaskType type, String taskName, Status status, String taskDescription) {
        super(taskName, taskDescription, id, status);
        this.type = type;
    }

    public Epic(String taskName, String taskDescription, Duration duration) {
        super(taskDescription, taskName, duration);
    }

    public Epic(Task task, List<Integer> subtasksIds) {
        super(task);
        this.subtasksIds = subtasksIds;
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
        subtasksIds.add(subTask.getId());
        statuses.add(subTask.getStatus());
        subtasks.put(id, subTask);
    }

    public List<Integer> getSubTasksIds() {
        return subtasksIds;
    }

    public HashMap<Integer, SubTask> getSubtasks() {
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
        subtasksIds.clear();
    }

    @Override
    public Duration getDuration() {
        if (getStartTime() == null) {
            return Duration.ZERO;
        }
        LocalDateTime lastSubtaskFinish = LocalDateTime.MIN;
        for (SubTask subtask : getSubtasks().values()) {
            if (subtask.getStartTime() == null) {
                break;
            }
            if (subtask.getFinishTime().isAfter(lastSubtaskFinish)) {
                lastSubtaskFinish = subtask.getFinishTime();
            }
        }
        return Duration.between(getStartTime(), lastSubtaskFinish);
    }

    @Override
    public LocalDateTime getStartTime() {
        LocalDateTime startFirstSubtask = LocalDateTime.MAX;
        for (SubTask subtask : subtasks.values()) {
            if (subtask.getStartTime() != null &&
                    startFirstSubtask.isAfter(subtask.getStartTime())) {
                startFirstSubtask = subtask.getStartTime();
            }
        }
        if (startFirstSubtask.isEqual(LocalDateTime.MAX)) {
            return null;
        } else {
            return startFirstSubtask;
        }

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