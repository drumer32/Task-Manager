package model;

import managers.FileBackedTaskManager;
import support.Status;
import support.TaskType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Epic extends Task {

    HashMap<Integer, SubTask> subtasks = new HashMap<>();

    public Epic(Integer id, TaskType type, String taskName, Status status, String taskDescription) {
        super(taskName, taskDescription, id, status);
        this.type = type;
    }

    public Epic(String taskName, String taskDescription) {
        super(taskDescription, taskName);
    }

    public Epic(Task task, HashMap<Integer, SubTask> subtasks) {
        super(task);
        this.subtasks = subtasks;
    }

    @Override
    public void setStatus(Status status) {
        super.setStatus(findStatus());
    }

    private Status findStatus() {
        return status;
    }

    public void addSubTask(SubTask subTask) {
        Integer epicId = subTask.getEpicId();
        subtasks.put(epicId, subTask);
    }

    @Override
    public TaskType getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }

    public HashMap<Integer, SubTask> getSubTasks() {
        return subtasks;
    }
//    public ArrayList<SubTask> getSubTasksNew() {
//        ArrayList<SubTask> allNew = new ArrayList<>();
//        for (SubTask subTask : subtasks.values()) {
//            if (subTask.getStatus().equals(Status.NEW)) {
//                System.out.println("true");
//                allNew.add(subTask);
//            } else {
//                System.out.println("not true");
//            }
//        } return allNew;
//    }
//    public ArrayList<SubTask> getSubTasksInProgress() {
//        ArrayList<SubTask> allInProgress = new ArrayList<>();
//        for (SubTask subTask : subtasks.values()) {
//            if (subTask.getStatus().equals(Status.IN_PROGRESS)) {;
//                allInProgress.add(subTask);
//            }
//        } return allInProgress;
//    }
//    public ArrayList<SubTask> getSubTasksDone() {
//        ArrayList<SubTask> allDone = new ArrayList<>();
//        for (SubTask subTask : subtasks.values()) {
//            if (subTask.getStatus().equals(Status.DONE)) {
//                allDone.add(subTask);
//            }
//        } return allDone;
//    }

    public void deleteSubTasks() {
        subtasks.clear();
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