package main.Task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    List<Long> subTasks = new ArrayList<>();

    public Epic (String name, String description) {
        super(name,description);
    }

    public Epic(String name, String description, long ID, TaskStatus status) {
        super(name, description, ID, status);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask.getID());
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    public List<Long> getSubTasks() {
        return subTasks;
    }
}
