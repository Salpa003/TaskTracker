package main.manager;

import main.Task.Epic;
import main.Task.SubTask;
import main.Task.Task;
import main.Task.TaskStatus;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    public HistoryManager historyManager = Managers.getDefaultHistory();
    private Map<Long, Task> tasks = new HashMap<>();
    private Map<Long, SubTask> subTasks = new HashMap<>();
    private Map<Long, Epic> epics = new HashMap<>();
    private final Set<Task> priorityTasks = new TreeSet<>();

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
    public Optional<Task> getTask(long id) { // хорошая практика возврщать из хранилища сущность завернутую в Optional скину статьи почитай применить
        Task task = tasks.get(id);
        historyManager.add(task);
        return Optional.ofNullable(task);
    }

    @Override
    public Optional<SubTask> getSubTask(long id) {  // хорошая практика возврщать из хранилища сущность завернутую в Optional скину статьи почитай применить
        SubTask subTask = subTasks.get(id);
        historyManager.add(subTask);
        return Optional.ofNullable(subTask);
    }

    @Override
    public Optional<Epic> getEpic(long id) {  // хорошая практика возврщать из хранилища сущность завернутую в Optional скину статьи почитай применить
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return Optional.ofNullable(epic);
    }

    @Override
    public void addTask(Task task) {
        if (task == null)
            return;
        tasks.put(task.getID(), task);
        priorityTasks.add(task);
    }

    @Override
    public void addSubTask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicID());
        if (subTask==null|| epic == null)
            return;
        subTasks.put(subTask.getID(), subTask);
        priorityTasks.add(subTask);
        epic.addSubTask(subTask);
    }

    @Override
    public void addEpic(Epic epic) {
        if (epic == null)
            return;
        epics.put(epic.getID(), epic);
        priorityTasks.add(epic);
    }

    @Override
    public void updateTask(Task newTask) {
        boolean isTaskExist = tasks.containsKey(newTask.getID());
        if (isTaskExist) {
            tasks.put(newTask.getID(), newTask);
        }
    }

    @Override
    public void updateSubTask(SubTask newTask) {
        boolean isTaskExist = tasks.containsKey(newTask.getID());
        if (isTaskExist) {
            subTasks.put(newTask.getID(), newTask);
        }
    }

    @Override
    public void updateEpic(Epic newTask) {
        boolean isTaskExist = tasks.containsKey(newTask.getID());
        if (isTaskExist) { // можно улучшить читаемость если Boolean isTaskExist =  tasks.containsKey(newTask.getID())
            epics.put(newTask.getID(), newTask);
        }
    }

    @Override
    public void removeTask(long id) {
      priorityTasks.remove(tasks.remove(id));
        historyManager.remove(id);
    }

    @Override
    public void removeSubTask(long id) {
        if (subTasks.containsKey(id)) {
            priorityTasks.remove(subTasks.remove(id));
            historyManager.remove(id);
        }
    }

    @Override
    public void removeEpic(long id) {
        Epic epic = epics.remove(id);
        priorityTasks.remove(epic);
        historyManager.remove(id);
        if (epic == null) return;

        for (Long ID : epic.getSubTasks().keySet()) {
            removeSubTask(ID);
        }
    }

    @Override
    public List<SubTask> getSubTasksWithEpicID(long id) {
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
    @Override
    public Set<Task> getPrioritizedTasks() {
        return priorityTasks;
    }
}
