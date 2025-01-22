package main.Task;

import main.manager.InMemoryTaskManager;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private long ID;
    private TaskStatus status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        status = TaskStatus.NEW;
        ID = InMemoryTaskManager.generateID();
    }

    public Task(String name, String description, long ID, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public TaskType getType() {
        return TaskType.TASK;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "name = " + name + " | " +
                "description = " + description + " | ID " + ID + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return ID == task.ID && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, ID, status);
    }
}
