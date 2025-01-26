package main.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable<Task> {
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
        this.ID = generateID();
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime.plus(duration);
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.startTime = null;
        this.duration = null;
        this.endTime = null;
        this.ID = generateID();
        this.status = TaskStatus.NEW;
    }

    public Task(String name, String description, LocalDateTime startTime, Duration duration,
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
        return this.ID == task.ID && Objects.equals(this.name, task.name)
                && Objects.equals(this.description, task.description)
                && this.status == task.status && (duration == null && task.duration == null) ? true : Objects.equals(this.duration, task.duration)
                && (startTime == null && task.startTime == null) ? true : Objects.equals(this.startTime, task.startTime)
                && (endTime == null && task.endTime == null) ? true : Objects.equals(this.endTime, task.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, ID, status, duration, startTime, endTime);
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

    private static long generateID;

    public static long generateID() {
        return generateID++;
    }

    @Override
    public int compareTo(Task o) {
        if (startTime == null)
            return 1;
        if (o.getStartTime() == null)
            return -1;
        return startTime.compareTo(o.getStartTime());
    }
}
