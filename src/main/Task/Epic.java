package main.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Epic extends Task {
    Map<Long, SubTask> subTasks = new HashMap<>();

    public Epic(String name, String description, LocalDateTime startTime, Duration duration) {
        super(name, description, startTime, duration);
    }

    public Epic(String name, String description) {
        super(name, description);
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
            if (subTask.getDuration() == null || subTask.getStartTime() == null || subTask.getEndTime() == null)
                continue;
            duration = duration.plus(subTask.getDuration());
            if (subTask.getStartTime().isBefore(beforeTask.getStartTime())) {
                beforeTask = subTask;
            }
            if (subTask.getEndTime().isAfter(afterTask.getEndTime())) {
                afterTask = subTask;
            }
        }
        if (beforeTask.getStartTime()==LocalDateTime.MAX && afterTask.getStartTime() == LocalDateTime.MIN) {
            this.setStartTime(null);
            this.setDuration(null);
            this.setEndTime(null);
            return;
        }
        this.setStartTime(beforeTask.getStartTime());
        this.setDuration(duration);
        this.setEndTime(afterTask.getEndTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTasks, epic.subTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasks);
    }
}
