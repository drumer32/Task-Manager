package managers;

import managers.filebacked.FileBackedTaskManager;
import managers.http.HttpTaskManager;
import managers.in_memory.InMemoryTaskManager;

import java.net.URI;

public class Managers {
    public static TaskManager getDefault(Integer number) {
        TaskManager result = null;
        String filename = "";
        URI url = null;
        if (number == 1) {
            result = new InMemoryTaskManager();
        } else if (number == 2) {
            result = new FileBackedTaskManager(filename);
        } else if (number == 3) {
            result = new HttpTaskManager(filename, url);
        } else {
            System.out.println("ERROR!!! ВВЕДИТЕ IN_MEMORY ИЛИ IN_FILE");
        }
        return result;
    }
}