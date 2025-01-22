package main;

import main.Task.Epic;
import main.Task.SubTask;
import main.Task.Task;
import main.Task.TaskStatus;
import main.manager.Managers;
import main.manager.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getFileBackedManager();


        Task task1 = new Task("Иследование потребностей пользователей", "Провести опрос ");
        task1.setStatus(TaskStatus.IN_PROGRESS);

        Task task2 = new Task("Создание прототипа интерфейса", "Разработать несколько выриантов");

        Epic epic = new Epic("Повышение опыта персонала", "Разработка нового функционала");
        SubTask subTask1 = new SubTask("Иследование потребностей", "Узнать потребности для улучшения", epic.getID());
        SubTask subTask2 = new SubTask("Создание прототипа интерфейса", "бла бла", epic.getID());

        manager.addTask(task1);
        manager.addTask(task2);
        manager.addEpic(epic);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);

        manager.getSubTask(subTask2.getID());
        manager.getTask(task1.getID());
        manager.getTask(task2.getID());
        manager.getTask(task1.getID());
        manager.getEpic(epic.getID());
        manager.getSubTask(subTask1.getID());


        System.out.println(manager.getHistory());
    }
}