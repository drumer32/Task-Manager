package managers;

public class Managers {
    public static TaskManager getDefault(Integer number) {
        TaskManager result = null;
        if (number == 1) {
            result = new InMemoryTaskManager();
        } else if (number == 2) {
            result = new FileBackedTaskManager("TaskSavedBackup");
        } else {
            System.out.println("ERROR!!! ВВЕДИТЕ IN_MEMORY ИЛИ IN_FILE");
        }
        return result;
    }
}