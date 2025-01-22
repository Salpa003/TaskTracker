package main.Task;

public class SubTask extends Task{
    long epicID;
    public SubTask(String name, String description, long epicID) {
        super(name,description);
        this.epicID = epicID;
    }

    public SubTask(String name, String description, long ID, TaskStatus status, long epicID) {
        super(name, description, ID, status);
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
