package managers;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getDefaultSaved() {
        return new FileBackedTaskManager("TaskSavedBackup");
    }
}
