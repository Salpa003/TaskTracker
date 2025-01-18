package manager;

import Task.*;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    public List<Task> getTasks(); // по умолчанию в interface методы public можно не указывать
    public List<SubTask> getSubTasks();
    public List<Epic> getEpics();
    public void removeAllTasks();
    public void removeAllSubtasks();
    public void removeAllEpics();
    public Task getTask(long id);
    public SubTask getSubtask(long id);
    public Epic getEpic(long id);
    public void addTask(Task task);
    public void addSubTask(SubTask subTask);
    public void addEpic(Epic epic);
    public void updateTask(Task newTask);
    public void updateSubtask(SubTask newSubTask);
    public void updateEpic(Epic newEpic);
    public void removeTask(long id);
    public void removeSubTask(long id);
    public void removeEpic(long id);
    public List<SubTask> getSubtasksWithEpicID(long id);
    public void updateEpicStatus(Epic epic);
    public List<Task> getHistory();
}
