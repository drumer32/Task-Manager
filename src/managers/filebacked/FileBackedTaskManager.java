package managers.filebacked;

import exceptions.ManagerSaveException;
import managers.TaskManager;
import managers.in_memory.InMemoryTaskManager;
import model.*;
import support.Status;
import support.TaskType;

import java.io.*;
import java.time.Duration;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    protected final String filename;

    public FileBackedTaskManager(String filename) {
        this.filename = filename;
    }

    public void save() {
        try {
            StringBuilder taskSaved = new StringBuilder();
            taskSaved.append("id,type,name,status,description,startTime,duration,epic");
            taskSaved.append("\n");

            for (Task a : tasks.values()) {
                taskSaved.append(a.toString());
                taskSaved.append("\n");
            }
            for (Task a : epics.values()) {
                taskSaved.append(a.toString());
                taskSaved.append("\n");
            }
            for (Task a : subTasks.values()) {
                taskSaved.append(a.toString());
                taskSaved.append("\n");
            }
            taskSaved.append("\n");
            for (Task a : history()) {
                taskSaved.append(a.getId().toString());
                taskSaved.append(",");
            }

            try (FileWriter writer = new FileWriter(filename)) {
                writer.write(String.valueOf(taskSaved));//перезапись файла
            } catch (FileNotFoundException e) {
                throw new ManagerSaveException("Файл не найден");
            } catch (IOException e) {
                throw new ManagerSaveException("Ошибка чтения файла");
            }
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getFilename() {
        return filename;
    }

    public void loadFromFile(String filename) throws IOException {
        FileReader reader = new FileReader(filename);
        BufferedReader br = new BufferedReader(reader);
        ArrayList<String> values = new ArrayList<>();
        try {
            while (br.ready()) {
                String line = br.readLine();
                values.add(line);
            }
            br.close();
        } catch (IOException e) {
            throw new IOException("Ошибка чтения файла");
        }
        clearAll();
        taskFromString(values);
        epicFromString(values);
        subtaskFromString(values);
        historyFromString(values);
    }

    @Override
    public void saveManager(int key, String apiKey) throws IOException {
        System.out.println("not for this manager");
    }

    @Override
    public void loadManager(int key, String apiKey) {
        System.out.println("not for this manager");
    }

    public void taskFromString(ArrayList<String> values) {
        String[] arrTasks;
        List<String> savedTasks = values.subList(1, (values.size() - 2));
        for (String a : savedTasks) {
            arrTasks = a.split(",");
            if (arrTasks[1].equals(TaskType.TASK.toString())) {
                Task task = new Task(Integer.parseInt(arrTasks[0]), TaskType.valueOf(arrTasks[1]),
                        arrTasks[2], Status.valueOf(arrTasks[3]), arrTasks[4]);
                String startTime = arrTasks[5];
                Duration duration = Duration.parse(arrTasks[6]);
                task.setStartTime(startTime);
                task.setDurationOfHours(duration);
                tasks.put(Integer.parseInt(arrTasks[0]), task);
            }
        }
    }
    public void epicFromString(ArrayList<String> values) {
        String[] arrTasks;
        List<String> savedTasks = values.subList(1, (values.size() - 2));
        for (String a : savedTasks) {
            arrTasks = a.split(",");
            if (arrTasks[1].equals(TaskType.EPIC.toString())) {
                Epic epic = new Epic(Integer.parseInt(arrTasks[0]), TaskType.valueOf(arrTasks[1]),
                        arrTasks[2], Status.valueOf(arrTasks[3]), arrTasks[4]);
                String startTime;
                if (arrTasks[5].equals("null")) {
                    startTime = null;
                } else {
                    startTime = arrTasks[5];
                }
                Duration duration;
                if (arrTasks[6].equals("null")) {
                    duration = null;
                } else {
                    duration = Duration.parse(arrTasks[6]);
                }
                epic.setStartTime(startTime);
                epic.setDurationOfHours(duration);
                epics.put(Integer.parseInt(arrTasks[0]), epic);
            }
        }
    }
    public void subtaskFromString(ArrayList<String> values) {
        String[] arrTasks;
        List<String> savedTasks = values.subList(1, (values.size() - 2));
        for (String a : savedTasks) {
            arrTasks = a.split(",");
            if (arrTasks[1].equals(TaskType.SUBTASK.toString())) {
                SubTask subTask = new SubTask(Integer.parseInt(arrTasks[0]), TaskType.valueOf(arrTasks[1]),
                        arrTasks[2], Status.valueOf(arrTasks[3]), arrTasks[4], Integer.parseInt(arrTasks[7]));
                String startTime = arrTasks[5];
                Duration duration = Duration.parse(arrTasks[6]);
                subTask.setStartTime(startTime);
                subTask.setDurationOfHours(duration);
                subTasks.put(Integer.parseInt(arrTasks[0]), subTask);
            }
        }
    }

    public void historyFromString(ArrayList<String> values) {
        String histId2 = values.get(values.size()-1);
        String[] histId = histId2.split(",");
        for (String a : histId) {
            Integer id = Integer.parseInt(a);
            if (tasks.containsKey(id)) {
                findById(id);
            } else if (epics.containsKey(id)) {
                findEpicById(id);
            } else if (subTasks.containsKey(id)) {
                findSubTaskById(id);
            } else {
                System.out.println("Ошибка при восстановлении истории просмотров");
            }
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public List<Task> getAllEpic() {
        return super.getAllEpic();
    }

    @Override
    public List<Task> getSubTaskAll() {
        return super.getSubTaskAll();
    }

    @Override
    public List<Integer> getSubTaskByEpic(Epic epic) {
        return super.getSubTaskByEpic(epic);
    }

    @Override
    public List<Integer>  getSubTaskByEpicId(Integer epicId) {
        return super.getSubTaskByEpicId(epicId);
    }

    @Override
    public Task getById(Integer id) {
        return super.getById(id);
    }

    @Override
    public void deleteById(Integer id) {
        super.deleteById(id);
    }

    @Override
    public Task findById(Integer id) {
        final Task task = super.findById(id);
        save();
        return task;
    }

    @Override
    public SubTask findSubTaskById(Integer id) {
        final SubTask subTask = super.findSubTaskById(id);
        save();
        return subTask;
    }

    @Override
    public Epic findEpicById(Integer id) {
        final Epic epic = super.findEpicById(id);
        save();
        return epic;
    }

    @Override
    public TreeMap<Integer, Task> getPrioritizedTasks() {
        return super.getPrioritizedTasks();
    }

    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        save();
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        super.createEpic(epic);
        save();
        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask, Integer epicId) {
        super.createSubTask(subTask, epicId);
        save();
        return subTask;
    }

    @Override
    public void updateTask(Task taskUpdated) {
        super.updateTask(taskUpdated);
        save();
    }

    @Override
    public void updateEpic(Epic epicUpdated) {
        super.updateEpic(epicUpdated);
        save();
    }

    @Override
    public void updateSubTask(SubTask SubTaskUpdated) {
        super.updateSubTask(SubTaskUpdated);
        save();
    }

    @Override
    public void deleteTaskById(Integer id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteSubTaskById(Integer id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(Integer id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void clearAll() {
        super.clearAll();
    }

    @Override
    public List<Task> history() {
        return super.history();
    }

    @Override
    public void printHistory() {
        super.printHistory();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
