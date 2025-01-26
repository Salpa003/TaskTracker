package main.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class SubTask extends Task  {
    long epicID;

    public SubTask(String name, String description,long epicID) {
        super(name, description);
        this.epicID = epicID;
    }

    public SubTask(String name, String description, LocalDateTime startTime, Duration duration, long epicID) {
        super(name, description, startTime, duration);
        this.epicID = epicID;
    }

    public SubTask(String name, String description, LocalDateTime startTime, Duration duration, LocalDateTime endTime,
                   long ID, TaskStatus status, long epicID) {
        super(name, description, startTime, duration, endTime, ID, status);
        this.epicID = epicID;
    }

    public long getEpicID() {
        return epicID;
    }

    public void setEpicID(long id) {
        epicID = id;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return epicID == subTask.epicID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicID);
    }
}
