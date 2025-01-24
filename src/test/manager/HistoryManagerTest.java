package test.manager;

import main.Task.SubTask;
import main.Task.Task;
import main.Task.TaskStatus;
import main.manager.HistoryManager;
import main.manager.InMemoryHistoryManager;
import main.manager.Managers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HistoryManagerTest {
    private HistoryManager manager;

    @BeforeEach
    public void updateHistoryManager() {
        manager = Managers.getDefaultHistory();
    }

    @Test
    public void add_shouldAddTaskInHistory() {
        Task task = new Task("Task_1", "DD", 1, TaskStatus.IN_PROGRESS);
        SubTask subTask = new SubTask("SubTask_1", "DD", 2, TaskStatus.NEW, 1);

        manager.add(task);
        manager.add(subTask);
        List<Task> expectedList = List.of(task, subTask);
        List<Task> actualList = manager.getHistory();

        Assertions.assertIterableEquals(expectedList, actualList);
    }

    @Test
    public void add_repeatAdd() {
        Task task1 = new Task("Task_1", "DD", 1, TaskStatus.IN_PROGRESS);
        Task task2 = new Task("Task_2", "DD", 2, TaskStatus.IN_PROGRESS);

        manager.add(task1);
        manager.add(task2);
        manager.add(task1);
        List<Task> expectedList = List.of(task2, task1);
        List<Task> actualList = manager.getHistory();

        Assertions.assertIterableEquals(expectedList, actualList);
    }

    @Test
    public void remove_shouldDeleteTaskInHistory() {
        Task task1 = new Task("Task_1", "DD", 1, TaskStatus.IN_PROGRESS);
        Task task2 = new Task("Task_1", "DD", 2, TaskStatus.IN_PROGRESS);

        manager.add(task1);
        manager.add(task2);
        manager.remove(444);
        manager.remove(2);

        Assertions.assertIterableEquals(List.of(task1), manager.getHistory());
    }

    @Test
    public void remove_shouldRemoveTasFromMiddleOfHistory() {
        Task task1 = new Task("Task_1", "DD", 1, TaskStatus.IN_PROGRESS);
        Task task2 = new Task("Task_2", "DD", 2, TaskStatus.IN_PROGRESS);
        Task task3 = new Task("Task_3", "DD", 3, TaskStatus.IN_PROGRESS);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        manager.remove(2);
        List<Task> expectedList = List.of(task1 , task3);
        List<Task> actualList = manager.getHistory();

        Assertions.assertIterableEquals(expectedList, actualList);
    }

    @Test
    public void remove_shouldRemoveTasFromBeginOfHistory() {
        Task task1 = new Task("Task_1", "DD", 1, TaskStatus.IN_PROGRESS);
        Task task2 = new Task("Task_2", "DD", 2, TaskStatus.IN_PROGRESS);
        Task task3 = new Task("Task_3", "DD", 3, TaskStatus.IN_PROGRESS);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        manager.remove(1);
        List<Task> expectedList = List.of(task2 , task3);
        List<Task> actualList = manager.getHistory();

        Assertions.assertIterableEquals(expectedList, actualList);
    }

    @Test
    public void remove_shouldRemoveTasFromEndOfHistory() {
        Task task1 = new Task("Task_1", "DD", 1, TaskStatus.IN_PROGRESS);
        Task task2 = new Task("Task_2", "DD", 2, TaskStatus.IN_PROGRESS);
        Task task3 = new Task("Task_3", "DD", 3, TaskStatus.IN_PROGRESS);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        manager.remove(3);
        List<Task> expectedList = List.of(task1 , task2);
        List<Task> actualList = manager.getHistory();

        Assertions.assertIterableEquals(expectedList, actualList);
    }


}
