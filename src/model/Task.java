package model;
import java.util.Objects;
import static model.Status.NEW;

public class Task {
    private String taskName;
    private String taskDescription;
    protected Integer id;
    protected Status status;

    public Task() {
    }

    public Task(String taskName, Integer id) {
        this(taskName, "", id, NEW);
    }

    public Task(String taskName, String taskDescription, Integer id) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.id = id;
        this.status = NEW;
    }

    public Task(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.id = null;
        this.status = NEW;
    }

    public Task(String taskName, String taskDescription, Integer id, Status status) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskName, task.taskName)
                && Objects.equals(taskDescription, task.taskDescription)
                && Objects.equals(id, task.id)
                && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, taskDescription, id, status);
    }
}
