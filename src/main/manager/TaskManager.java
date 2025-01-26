package main.manager;

import main.Task.Epic;
import main.Task.SubTask;
import main.Task.Task;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TaskManager {
     List<Task> getTasks(); // по умолчанию в interface методы public можно не указывать
     List<SubTask> getSubTasks();
     List<Epic> getEpics();
     void removeAllTasks();
     void removeAllSubtasks();
     void removeAllEpics();
     Optional<Task> getTask(long id);
     Optional<SubTask> getSubTask(long id);
     Optional<Epic> getEpic(long id);
     void addTask(Task task);
     void addSubTask(SubTask subTask);
     void addEpic(Epic epic);
     void updateTask(Task newTask);
     void updateSubTask(SubTask newSubTask);
     void updateEpic(Epic newEpic);
     void removeTask(long id);
     void removeSubTask(long id);
     void removeEpic(long id);
     List<SubTask> getSubTasksWithEpicID(long id);
     void updateEpicStatus(Epic epic);
     List<Task> getHistory();
     Set<Task> getPrioritizedTasks();
}
