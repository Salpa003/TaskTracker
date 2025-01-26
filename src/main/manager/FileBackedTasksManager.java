package main.manager;

import main.Task.*;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class FileBackedTasksManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTasksManager(String path) {
        file = new File(path);

        Map<Long, Task> tasks = new HashMap<>();
        Map<Long, SubTask> subTasks = new HashMap<>();
        Map<Long, Epic> epics = new HashMap<>();

        try (Reader reader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            bufferedReader.readLine();
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                String[] p = line.split(",");
                if (line.isBlank() || p.length <= 1)
                    continue;
                if (!(p[1].equals(TaskType.TASK.toString())
                        || p[1].equals(TaskType.SUBTASK.toString())
                        || p[1].equals(TaskType.EPIC.toString()))) {
                    List<Task> list = new ArrayList<>();
                    list.addAll(tasks.values());
                    list.addAll(subTasks.values());
                    list.addAll(epics.values());

                    for (String str : p) {
                        long id = Long.parseLong(str);
                        for (Task task : list) {
                            if (task.getID() == id) super.addToHistory(task);
                        }
                    }
                    continue;
                }

                if (p[1].equals(TaskType.TASK.toString())) {
                    Task task = new Task(p[2], p[4],p[5].equals("null")?null: LocalDateTime.parse(p[5],
                            DateTimeFormatter.ofPattern("HH:mm d.M.yyyy")),
                            p[6].equals("null")?null:  Duration.ofSeconds(Long.parseLong(p[6].substring(0, p[6].length() - 1))),
                            p[7].equals("null")?null:LocalDateTime.parse(p[7],
                            DateTimeFormatter.ofPattern("HH:mm d.M.yyyy")),
                            Long.parseLong(p[0]), TaskStatus.valueOf(p[3]));
                    tasks.put(task.getID(), task);
                } else if (p[1].equals(TaskType.SUBTASK.toString())) {
                    SubTask subTask = new SubTask(p[2], p[4],p[5].equals("null")?null: LocalDateTime.parse(p[5],
                            DateTimeFormatter.ofPattern("HH:mm d.M.yyyy")),
                            p[6].equals("null")?null: Duration.ofSeconds(Long.parseLong(p[6].substring(0, p[6].length() - 1))),
                            p[7].equals("null")?null:LocalDateTime.parse(p[7],
                            DateTimeFormatter.ofPattern("HH:mm d.M.yyyy")),
                            Long.parseLong(p[0]), TaskStatus.valueOf(p[3]), Long.parseLong(p[8]));
                    subTasks.put(subTask.getID(), subTask);
                } else if (p[1].equals(TaskType.EPIC.toString())) {
                    Epic epic = new Epic(p[2], p[4],p[5].equals("null")?null: LocalDateTime.parse(p[5],
                            DateTimeFormatter.ofPattern("HH:mm d.M.yyyy")),
                            p[6].equals("null")?null:   Duration.ofSeconds(Long.parseLong(p[6].substring(0, p[6].length() - 1))),
                            p[7].equals("null")?null:LocalDateTime.parse(p[7],
                            DateTimeFormatter.ofPattern("HH:mm d.M.yyyy")),
                            Long.parseLong(p[0]), TaskStatus.valueOf(p[3]));
                    epics.put(epic.getID(), epic);
                }
                for (Epic epic : epics.values()) {
                    for (SubTask subTask : subTasks.values()) {
                        if (subTask.getEpicID() == epic.getID()) epic.addSubTask(subTask);
                    }
                }

            }
            super.setEpics(epics);
            super.setSubTasks(subTasks);
            super.setTasks(tasks);
        } catch (IOException e) {
            StackTraceElement[] elements = e.getStackTrace();
            System.out.println("Произошла ошибка при чтение файла !" + elements[elements.length - 1].getLineNumber());
        }
    }

    private void save() {
        try (Writer writer = new FileWriter(file)) {
            writer.write(ObjectToStringOfCSV());
        } catch (IOException e) {
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            StackTraceElement element = stackTraceElements[stackTraceElements.length - 1];
            System.out.printf("Произошла ошибка при записи файла! класс %s строка %d\n",
                    element.getClassName(), element.getLineNumber());
        }
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        super.addToHistory(task);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        super.addToHistory(subTask);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        super.addToHistory(epic);
        save();
    }

    @Override
    public void updateTask(Task newTask) {
        super.updateTask(newTask);
        save();
    }

    @Override
    public void updateSubTask(SubTask newSubTask) {
        super.updateSubTask(newSubTask);
        save();
    }

    @Override
    public void updateEpic(Epic newEpic) {
        super.updateEpic(newEpic);
        save();
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        super.updateEpicStatus(epic);
        save();
    }

    private String ObjectToStringOfCSV() {
        StringBuilder builder = new StringBuilder();
        builder.append("id,type,name,status,description,startTime,duration,endTime,epicID\n");

        List<Task> tasks = new ArrayList<Task>();
        tasks.addAll(getTasks());
        tasks.addAll(getEpics());
        tasks.addAll(getSubTasks());

        for (Task task : tasks) {
            String e = task instanceof SubTask ? String.valueOf(((SubTask) task).getEpicID()) : " ";

            String o = String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s\n",
                    task.getID(), task.getType(), task.getName(), task.getStatus(), task.getDescription(),
                    task.getStartTime() == null ? null : task.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm d.M.yyyy")),
                    task.getDuration() == null ? null : task.getDuration().toSeconds() + "S",
                    task.getEndTime() == null ? null : task.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm d.M.yyyy")),
                    e);
            builder.append(o);
        }
        builder.append("\n");
        List<Task> history = super.getHistory();
        for (Task task : history) {
            builder.append(task.getID()).append(",");
        }
        builder.delete(builder.length() - 1, builder.length());
        return builder.toString();
    }

    @Override
    public void removeTask(long id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeSubTask(long id) {
        super.removeSubTask(id);
        save();
    }

    @Override
    public void removeEpic(long id) {
        super.removeEpic(id);
        save();
    }

    @Override
    public Optional<Task> getTask(long id) {
        Optional<Task> task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Optional<SubTask> getSubTask(long id) {
        Optional<SubTask> subTask = super.getSubTask(id);
        save();
        return subTask;
    }

    @Override
    public Optional<Epic> getEpic(long id) {
        Optional<Epic> epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }
}
