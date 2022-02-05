package model;

public class SubTask extends Task {
    Integer epicId;

    public SubTask(Integer epicId) {
        this.epicId = epicId;
    }

    public SubTask(String name, String description, Integer id, Status status, Integer epicId) {
        super(name, description, id, status);
        this.epicId = epicId;
    }
    public SubTask(String name, String description, Integer epicId) {
        super(name, description, null);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return epicId.equals(subTask.epicId);
    }

    @Override
    public int hashCode() {
        return epicId.hashCode();
    }
}
