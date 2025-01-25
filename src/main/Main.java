package main;

import main.Task.Epic;
import main.Task.SubTask;
import main.Task.Task;
import main.Task.TaskStatus;
import main.manager.Managers;
import main.manager.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getFileBackedManager();


//        Task task1 = new Task("Иследование потребностей пользователей", "Провести опрос ",
//                LocalDateTime.now(), Duration.of(35, ChronoUnit.HOURS));
//        task1.setStatus(TaskStatus.IN_PROGRESS);
//
//        Task task2 = new Task("Создание прототипа интерфейса", "Разработать несколько выриантов",
//                LocalDateTime.now(), Duration.of(35, ChronoUnit.HOURS));
//
//        Epic epic = new Epic("Повышение опыта персонала", "Разработка нового функционала",
//                LocalDateTime.now(), Duration.of(35, ChronoUnit.HOURS));
//        SubTask subTask1 = new SubTask("Иследование потребностей", "Узнать потребности для улучшения",
//                LocalDateTime.now(), Duration.of(35, ChronoUnit.HOURS),epic.getID());
//        SubTask subTask2 = new SubTask("Создание прототипа интерфейса", "бла бла",
//                LocalDateTime.now(), Duration.of(35, ChronoUnit.HOURS),epic.getID());
//
//        manager.addTask(task1);
//        manager.addTask(task2);
//        manager.addEpic(epic);
//        manager.addSubTask(subTask1);
//        manager.addSubTask(subTask2);
//
//        manager.getSubTask(subTask2.getID());
//        manager.getTask(task1.getID());
//        manager.getTask(task2.getID());
//        manager.getTask(task1.getID());
//        manager.getEpic(epic.getID());
//        manager.getSubTask(subTask1.getID());
        List<Epic> epics = manager.getEpics();
        Epic epic = epics.get(0);
        System.out.println(epic.getSubTasks());

       // System.out.println(manager.getHistory());
    }
}