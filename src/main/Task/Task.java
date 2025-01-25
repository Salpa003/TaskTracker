package main.Task;

import main.manager.InMemoryTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private long ID;
    private TaskStatus status;
    private Duration duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Task(String name, String description, LocalDateTime startTime, Duration duration) {
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
        this.ID = InMemoryTaskManager.generateID();
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime.plus(duration);
    }

    public Task(String name, String description,LocalDateTime startTime, Duration duration,
                LocalDateTime endTime, long ID, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = endTime;
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
        return ID == task.ID && Objects.equals(name, task.name) && Objects.equals(description, task.description)
                && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, ID, status);
    }

    public Duration getDuration() {
        if (duration == null)
            return Duration.ZERO;
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
