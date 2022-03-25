package support;
import model.Epic;
import model.SubTask;
import model.Task;

import java.time.Duration;
import java.time.LocalDateTime;

//ускоряет процесс внутренних проверок и тестирования
public class TaskGenerator {

    public Task generateTask24Hours(LocalDateTime startTime) {
        return new Task
                ("TaskName", "TaskDescription", startTime, Duration.ofHours(24));
    }
    public Epic generateEpic() {
        return new Epic
                ("EpicName", "EpicDescription");
    }

    public SubTask generateSubtask24Hours(LocalDateTime startTime) {
        return new SubTask
                ("SubtaskName", "SubtaskDescription", startTime, Duration.ofHours(24));
    }
}
