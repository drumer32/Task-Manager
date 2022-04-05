package model;

import support.Status;
import support.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Epic extends Task {

    private List<Integer> subtasksIds = new ArrayList<>();
    private final List<Status> statuses = new ArrayList<>();
    private final TreeSet<LocalDateTime> subtasksByTime = new TreeSet<>();

    //id,type,name,status,description,startTime,duration
    public Epic(Integer id, TaskType type, String taskName, Status status,
                String taskDescription) {
        super(id, type, taskName, status, taskDescription);
    }

    public Epic(String taskName, String taskDescription) {
        super(taskDescription, taskName);
    }

    public Epic(Integer id, String taskDescription) {
        super(id, taskDescription);
    }

    public Epic(String taskName, String taskDescription, LocalDateTime startTime, Duration duration) {
        super(taskDescription, taskName, startTime, duration);
    }

    public Epic(Task task, List<Integer> subtasksIds) {
        super(task);
        this.subtasksIds = subtasksIds;
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
        subtasksByTime.add(subTask.getStartTime());
        subtasksByTime.add(subTask.getEndTime());
        setStartTime(String.valueOf(subtasksByTime.first()));
        setDurationOfHours(getDuration());
    }


    public List<Integer> getSubTasksIds() {
        return subtasksIds;
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
        if (!subtasksByTime.isEmpty()) {
            return Duration.between(subtasksByTime.first(), subtasksByTime.last());
        } else {
            return Duration.ZERO;
        }
    }

    @Override
    public LocalDateTime getStartTime() {
        if (!subtasksByTime.isEmpty()) {
            return subtasksByTime.first();
        } else {
            return LocalDateTime.now();
        }
    }

    @Override
    public LocalDateTime getEndTime() {
        return getStartTime().plus(getDuration());
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