package manager;

import Task.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    public HistoryManager historyManager = Managers.getDefaultHistory();
    static long ID = 1;
    private  Map<Long, Task> tasks = new HashMap<>();
    private  Map<Long, SubTask> subTasks = new HashMap<>();
    private  Map<Long, Epic> epics = new HashMap<>();

    public static long generateID() {
        return ID++;
    }

    public void setTasks(Map<Long, Task> tasks) {
        this.tasks = tasks;
    }

    public void setSubTasks(Map<Long, SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    public void setEpics(Map<Long, Epic> epics) {
        this.epics = epics;
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        subTasks.clear();
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Task getTask(long id) { // хорошая практика возврщать из хранилища сущность завернутую в Optional скину статьи почитай применить 
       historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override 
    public SubTask getSubtask(long id) {  // хорошая практика возврщать из хранилища сущность завернутую в Optional скину статьи почитай применить 
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public Epic getEpic(long id) {  // хорошая практика возврщать из хранилища сущность завернутую в Optional скину статьи почитай применить 
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public void addTask(Task task) {
      //  task.setID(generateID());  // почему закомментировал? в итоговом коде не должны быть закомментированные строки, если не нужны удаляй 
        tasks.put(task.getID(), task);
    }

    @Override
    public void addSubTask(SubTask subTask) {
      //  subTask.setID(generateID());
        subTasks.put(subTask.getID(), subTask);
        epics.get(subTask.getEpicID()).addSubTask(subTask);
    }

    @Override
    public void addEpic(Epic epic) {
       // epic.setID(generateID());
        epics.put(epic.getID(), epic);
    }

    @Override
    public void updateTask(Task newTask) {
        if (tasks.containsKey(newTask.getID())) { // можно улучшить читаемость если Boolean isTaskExist =  tasks.containsKey(newTask.getID())
      //if (isTaskExist)
            tasks.put(newTask.getID(), newTask);
        }
    }

    @Override
    public void updateSubtask(SubTask newSubTask) {
        if (subTasks.containsKey(newSubTask.getID())) {// можно улучшить читаемость если Boolean isTaskExist =  tasks.containsKey(newTask.getID())
      //if (isTaskExist)
            subTasks.put(newSubTask.getID(), newSubTask);
        }
    }

    @Override
    public void updateEpic(Epic newEpic) {
        if (epics.containsKey(newEpic.getID())) {// можно улучшить читаемость если Boolean isTaskExist =  tasks.containsKey(newTask.getID())
      //if (isTaskExist)
            epics.put(newEpic.getID(), newEpic);
        }
    }

    @Override
    public void removeTask(long id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeSubTask(long id) {
        if (subTasks.containsKey(id)) {
            subTasks.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void removeEpic(long id) {
       Epic epic= epics.remove(id);
        historyManager.remove(id);
        if (epic == null) return;

        for (Long ID : epic.getSubtaskId()) {
             removeSubTask(ID);
        }
    }

    @Override
    public List<SubTask> getSubtasksWithEpicID(long id) {
        ArrayList<SubTask> sub = new ArrayList<>();
        for (SubTask a : subTasks.values()) {
            if (a.getEpicID() == id)
                sub.add(a);
        }
        return sub;
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        boolean isDone = true;
        boolean isNew = true;

        ArrayList<SubTask> subTask = new ArrayList<>();
        for (SubTask task : subTasks.values()) {
            if (task.getEpicID() == epic.getID())
                subTask.add(task);
        }

        for (var task : subTask) {
            if (task.getStatus() != TaskStatus.DONE) {
                isDone = false;
            }
            if (task.getStatus() != TaskStatus.NEW) {
                isNew = false;
            }
            if (subTasks.isEmpty()) {
                isNew = true;
            }
        }
        if (isDone) { // молодец, вынесение результата выражения в отдельную переменную и ее использование в if улучшает читаемость 
            epic.setStatus(TaskStatus.DONE);
        } else if (isNew) {
            epic.setStatus(TaskStatus.NEW);
        } else if (!isDone && !isNew) {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    public void addToHistory(Task task) {
        historyManager.add(task);
    }

}
