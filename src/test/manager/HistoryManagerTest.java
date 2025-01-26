package test.manager;

import main.Task.SubTask;
import main.Task.Task;
import main.manager.HistoryManager;
import main.manager.Managers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class HistoryManagerTest {
    private HistoryManager manager;
    private LocalDateTime ldt = LocalDateTime.now();
    private Duration duration = Duration.of(35, ChronoUnit.HOURS);

    @BeforeEach
    public void updateHistoryManager() {
        manager = Managers.getDefaultHistory();
    }

    @Test
    public void add_shouldAddTaskInHistory() {
        Task task = new Task("Task_1", "DD", ldt, duration);
        SubTask subTask = new SubTask("SubTask_1", "DD", ldt, duration, 1);

        manager.add(task);
        manager.add(subTask);
        List<Task> expectedList = List.of(task, subTask);
        List<Task> actualList = manager.getHistory();

        Assertions.assertIterableEquals(expectedList, actualList);
    }

    @Test
    public void add_repeatAdd() {
        Task task1 = new Task("Task_1", "DD", ldt, duration);
        Task task2 = new Task("Task_2", "DD", ldt, duration);

        manager.add(task1);
        manager.add(task2);
        manager.add(task1);
        List<Task> expectedList = List.of(task2, task1);
        List<Task> actualList = manager.getHistory();

        Assertions.assertIterableEquals(expectedList, actualList);
    }

    @Test
    public void remove_shouldDeleteTaskInHistory() {
        Task task1 = new Task("Task_1", "DD", ldt, duration);
        Task task2 = new Task("Task_1", "DD", ldt, duration);

        manager.add(task1);
        manager.add(task2);
        manager.remove(-1);
        manager.remove(task2.getID());

        Assertions.assertIterableEquals(List.of(task1), manager.getHistory());
    }

    @Test
    public void remove_shouldRemoveTasFromMiddleOfHistory() {
        Task task1 = new Task("Task_1", "DD", ldt, duration);
        Task task2 = new Task("Task_2", "DD", ldt, duration);
        Task task3 = new Task("Task_3", "DD", ldt, duration);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        manager.remove(task2.getID());
        List<Task> expectedList = List.of(task1, task3);
        List<Task> actualList = manager.getHistory();

        Assertions.assertIterableEquals(expectedList, actualList);
    }

    @Test
    public void remove_shouldRemoveTasFromBeginOfHistory() {
        Task task1 = new Task("Task_1", "DD", ldt, duration);
        Task task2 = new Task("Task_2", "DD", ldt, duration);
        Task task3 = new Task("Task_3", "DD", ldt, duration);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        manager.remove(task1.getID());
        List<Task> expectedList = List.of(task2, task3);
        List<Task> actualList = manager.getHistory();

        Assertions.assertIterableEquals(expectedList, actualList);
    }

    @Test
    public void remove_shouldRemoveTasFromEndOfHistory() {
        Task task1 = new Task("Task_1", "DD", ldt, duration);
        Task task2 = new Task("Task_2", "DD", ldt, duration);
        Task task3 = new Task("Task_3", "DD", ldt, duration);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        manager.remove(task3.getID());
        List<Task> expectedList = List.of(task1, task2);
        List<Task> actualList = manager.getHistory();

        Assertions.assertIterableEquals(expectedList, actualList);
    }


}
