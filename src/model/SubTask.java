package model;
import support.Status;
import support.TaskType;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class SubTask extends Task {
    Integer epicId;

    public SubTask(Integer epicId) {
        this.epicId = epicId;
    }

    public SubTask(Integer id, TaskType type, String name, Status status,
                   String description, Integer epicId,
                   LocalDateTime startTime, Duration duration) {
        super(id, type, name, status, description, startTime, duration);
        this.epicId = epicId;
    }


    public SubTask(Task task) {
        super(task);
    }

    public SubTask(Integer id, String description) {
        super(id, description);
    }

    public SubTask(String name, String description, LocalDateTime startTime, Duration duration) {
        super(name, description, startTime, duration);
    }

    //Конструктор для сборки из строки
    public SubTask(Integer id, TaskType type, String taskName, Status status, String taskDescription, Integer epicId) {
        super(id, type, taskName, status, taskDescription);
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