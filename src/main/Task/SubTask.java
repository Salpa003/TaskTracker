package main.Task;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task{
    long epicID;
    public SubTask(String name, String description, LocalDateTime startTime, Duration duration, long epicID) {
        super(name,description,startTime,duration);
        this.epicID = epicID;
    }

    public SubTask(String name, String description,LocalDateTime startTime, Duration duration, LocalDateTime endTime,
                   long ID, TaskStatus status, long epicID) {
        super(name, description, startTime,duration,endTime,ID, status);
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
}
