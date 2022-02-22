package managers;
import model.Task;

import java.io.FileNotFoundException;
import java.util.List;

public interface HistoryManager {

    void add(Task task);

    void remove(Integer id);

    List<Task> getHistory();
}
