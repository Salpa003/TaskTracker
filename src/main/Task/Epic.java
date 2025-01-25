package main.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Epic extends Task {
    Map<Long, SubTask> subTasks = new HashMap<>();

    public Epic(String name, String description, LocalDateTime startTime, Duration duration) {
        super(name, description, startTime, duration);
    }
    public Epic(String name, String description, LocalDateTime startTime, Duration duration, LocalDateTime endTime, long ID, TaskStatus status) {
        super(name, description, startTime, duration, endTime, ID, status);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void addSubTask(SubTask subTask) {
        subTasks.put(subTask.getID(), subTask);
        updateTimes();
    }

    public void removeSubTask(Long id) {
        if (subTasks.remove(id) != null)
            updateTimes();
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    public Map<Long, SubTask> getSubTasks() {
        return subTasks;
    }

    public void updateTimes() {
        Duration duration = Duration.ZERO;
        SubTask beforeTask = new SubTask("ST1", "D", LocalDateTime.MAX, Duration.ZERO, 1);
        SubTask afterTask = new SubTask("ST2", "D", LocalDateTime.MIN, Duration.ZERO, 2);
        for (SubTask subTask : subTasks.values()) {
            duration = duration.plus(subTask.getDuration());
            if (subTask.getStartTime().isBefore(beforeTask.getStartTime())) {
                beforeTask = subTask;
            }
            if (subTask.getEndTime().isAfter(afterTask.getEndTime())) {
                afterTask = subTask;
            }
        }
        this.setStartTime(beforeTask.getStartTime());
        this.setDuration(duration);
        this.setEndTime(afterTask.getEndTime().plus(afterTask.getDuration()));
    }
}
