package test.manager;

import main.Task.*;
import main.manager.Managers;
import main.manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {
    private TaskManager taskManager;
    private LocalDateTime ldt = LocalDateTime.now();
    private Duration duration = Duration.of(35, ChronoUnit.HOURS);

    @BeforeEach
    public void updateTaskManager() {
        try (Writer writer = new FileWriter(new File("src/CSV.txt"))) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("Error");
        }
        taskManager = Managers.getDefault();
    }

    @Test
    void getTasks_shouldReturnTasks() {
        Task task1 = new Task("Task_1", "DD", ldt, duration);
        Task task2 = new Task("Task_2", "DD", ldt, duration);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        List<Task> expectedList = List.of(task1, task2);
        List<Task> actualList = taskManager.getTasks();
        assertIterableEquals(expectedList, actualList);
    }

    @Test
    void getSubTasks_shouldReturnSubtasks() {
        Epic epic = new Epic("E", "D");
        SubTask subTask1 = new SubTask("SubTask_1", "DD", ldt, duration, epic.getID());
        SubTask subTask2 = new SubTask("SubTask_2", "DD", ldt, duration, epic.getID());

        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);

        List<SubTask> expectedList = List.of(subTask1, subTask2);
        List<SubTask> actualList = taskManager.getSubTasks();
        assertIterableEquals(expectedList, actualList);
    }

    @Test
    void getEpics_shouldReturnEpics() {
        Epic epic1 = new Epic("Epic_1", "DD", ldt, duration);
        Epic epic2 = new Epic("Epic_2", "DD", ldt, duration);

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        List<Epic> expectedList = List.of(epic1, epic2);
        List<Epic> actualList = taskManager.getEpics();
        assertIterableEquals(expectedList, actualList);
    }

    @Test
    void removeAllTasks_shouldDeleteAllTasks() {
        Task task1 = new Task("Task_1", "DD", ldt, duration);
        Task task2 = new Task("Task_2", "DD", ldt, duration);

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.removeAllTasks();

        assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void removeAllSubTasks_shouldDeleteAllSubtasks() {
        SubTask task1 = new SubTask("SubTask_1", "DD", ldt, duration, 12);
        SubTask task2 = new SubTask("SubTask_2", "DD", ldt, duration, 78);

        taskManager.addSubTask(task1);
        taskManager.addSubTask(task2);
        taskManager.removeAllSubtasks();

        assertTrue(taskManager.getSubTasks().isEmpty());
    }

    @Test
    void removeAllEpics_shouldDeleteAllEpics() {
        Epic epic1 = new Epic("Epic_1", "DD", ldt, duration);
        Epic epic2 = new Epic("Epic_2", "DD", ldt, duration);

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.removeAllEpics();

        assertTrue(taskManager.getEpics().isEmpty());
    }

    @Test
    void getTask_shouldReturnTaskByID() {
        Task expectedTask = new Task("Task_1", "DD", ldt, duration);

        taskManager.addTask(expectedTask);
        Optional<Task> actualTask = taskManager.getTask(expectedTask.getID());
        Optional<Task> emptyTask = taskManager.getTask(-1);
        assertAll(
                () -> assertEquals(expectedTask, actualTask.get()),
                () -> assertTrue(emptyTask.isEmpty())
        );
    }

    @Test
    void getSubtask_shouldReturnSubtaskByID() {
        Epic epic = new Epic("E", "D");
        SubTask expectedSubTask = new SubTask("SubTask_1", "DD", ldt, duration, epic.getID());

        taskManager.addEpic(epic);
        taskManager.addSubTask(expectedSubTask);
        Optional<SubTask> actualSubTask = taskManager.getSubTask(expectedSubTask.getID());
        Optional<SubTask> emptySubTask = taskManager.getSubTask(-1);
        assertAll(
                () -> assertEquals(expectedSubTask, actualSubTask.get()),
                () -> assertTrue(emptySubTask.isEmpty())
        );
    }

    @Test
    void getEpic_shouldReturnEpicByID() {
        Epic expectedEpic = new Epic("Epic_1", "DD", ldt, duration);

        taskManager.addEpic(expectedEpic);
        Optional<Epic> actualEpic = taskManager.getEpic(expectedEpic.getID());
        Optional<Epic> emptyEpic = taskManager.getEpic(-1);
        assertAll(
                () -> assertEquals(expectedEpic, actualEpic.get()),
                () -> assertTrue(emptyEpic.isEmpty())
        );
    }

    @Test
    void updateTask_shouldUpdateTask() {
        Task task = new Task("Task_1", "DD", ldt, duration);
        Task expectedTask = new Task("Task_2", "DD", ldt, duration);

        taskManager.addTask(task);
        task.setStatus(TaskStatus.DONE);
        task.setName("Task_2");
        taskManager.updateTask(task);
        expectedTask.setID(task.getID());
        expectedTask.setStatus(TaskStatus.DONE);

        assertEquals(expectedTask, taskManager.getTask(task.getID()).get());
    }

    @Test
    void updateSubTask_shouldUpdateSubTask() {
        Epic epic = new Epic("E", "D");
        SubTask subTask = new SubTask("SubTask_1", "DD", ldt, duration, epic.getID());
        SubTask expectedSubTask = new SubTask("SubTask_2", "DD", ldt, duration, epic.getID());

        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);
        subTask.setName("SubTask_2");
        taskManager.updateSubTask(subTask);
        expectedSubTask.setID(subTask.getID());

        assertEquals(expectedSubTask, taskManager.getSubTask(subTask.getID()).get());
    }

    @Test
    void updateEpic_shouldUpdateEpic() {
        Epic epic = new Epic("Epic_1", "DD", ldt, duration);
        Epic expectedEpic = new Epic("Epic_2", "DD", ldt, duration);

        taskManager.addEpic(epic);
        epic.setStatus(TaskStatus.DONE);
        epic.setName("Epic_2");
        taskManager.updateEpic(epic);
        expectedEpic.setID(epic.getID());
        expectedEpic.setStatus(TaskStatus.DONE);

        assertEquals(expectedEpic, taskManager.getEpic(epic.getID()).get());
    }

    @Test
    void removeTask_shouldDeleteTask() {
        Task task1 = new Task("Task_1", "DD", ldt, duration);
        Task task2 = new Task("Task_2", "DD", ldt, duration);

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.removeTask(task1.getID());

        assertTrue(taskManager.getTask(task2.getID()).isPresent());
        assertTrue(taskManager.getTask(task1.getID()).isEmpty());
    }

    @Test
    void removeSubTask_shouldDeleteSubTask() {
        Epic epic = new Epic("F", "D");
        SubTask task1 = new SubTask("Task_1", "DD", ldt, duration, epic.getID());
        SubTask task2 = new SubTask("Task_2", "DD", ldt, duration, epic.getID());

        taskManager.addEpic(epic);
        taskManager.addSubTask(task1);
        taskManager.addSubTask(task2);
        taskManager.removeSubTask(task1.getID());

        assertTrue(taskManager.getSubTask(task2.getID()).isPresent());
        assertTrue(taskManager.getSubTask(task1.getID()).isEmpty());
    }

    @Test
    void removeEpic_shouldDeleteEpic() {
        Epic task1 = new Epic("Task_1", "DD", ldt, duration);
        Epic task2 = new Epic("Task_2", "DD", ldt, duration);

        taskManager.addEpic(task1);
        taskManager.addEpic(task2);
        taskManager.removeEpic(task1.getID());

        assertTrue(taskManager.getEpic(task2.getID()).isPresent());
        assertTrue(taskManager.getEpic(task1.getID()).isEmpty());
    }

    @Test
    void getSubTasksWithEpicID_shouldReturnSubTasksByEpicID() {
        Epic epic = new Epic("Epic_1", "DD", ldt, duration);
        SubTask subTask1 = new SubTask("SubTask_1", "DD", ldt, duration, epic.getID());
        SubTask subTask2 = new SubTask("SubTask_2", "DD", ldt, duration, epic.getID());
        SubTask subTask3 = new SubTask("SubTask_3", "DD", ldt, duration, -1);

        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        List<SubTask> expectedList = List.of(subTask2, subTask1);
        List<SubTask> actualList = taskManager.getSubTasksWithEpicID(epic.getID());

        assertTrue(actualList.containsAll(expectedList));
    }

    @Test
    void updateEpicStatus_shouldUpdateEpicStatus() {
        Epic epic = new Epic("Epic_1", "DD", ldt, duration);
        SubTask subTask1 = new SubTask("SubTask_1", "DD", ldt, duration, epic.getID());
        SubTask subTask2 = new SubTask("SubTask_2", "DD", ldt, duration, epic.getID());

        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.updateEpicStatus(epic);
        assertEquals(TaskStatus.NEW, epic.getStatus());

        subTask1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateEpicStatus(epic);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());

        subTask1.setStatus(TaskStatus.DONE);
        taskManager.updateEpicStatus(epic);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());

        subTask2.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateEpicStatus(epic);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());

        subTask2.setStatus(TaskStatus.DONE);
        taskManager.updateEpicStatus(epic);
        assertEquals(TaskStatus.DONE, epic.getStatus());
    }

    @Test
    void getHistory_shouldReturnHistory() {
        Task task1 = new Task("Task_1", "DD", ldt, duration);
        Task task2 = new Task("Task_2", "DD", ldt, duration);
        Epic epic = new Epic("Epic_1", "DD", ldt, duration);
        SubTask subTask1 = new SubTask("SubTask_1", "DD", ldt, duration, epic.getID());
        SubTask subTask2 = new SubTask("SubTask_2", "DD", ldt, duration, epic.getID());

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);

        assertTrue(taskManager.getHistory().isEmpty());

        taskManager.getEpic(epic.getID());
        taskManager.getTask(task2.getID());
        taskManager.getSubTask(subTask2.getID());
        taskManager.getTask(task1.getID());
        taskManager.getSubTask(subTask1.getID());

        List<Task> expectedList = List.of(epic, task2, subTask2, task1, subTask1);
        List<Task> actualList = taskManager.getHistory();
        assertIterableEquals(expectedList, actualList);
    }

    @Test
    void correctCalculationEndTime() {
        Task task = new Task("Task", "D",
                LocalDateTime.of(2025, 1, 1, 1, 1, 1),
                Duration.ofHours(1).plusMinutes(1).plusSeconds(1));
        SubTask subTask = new SubTask("SubTask", "D",
                LocalDateTime.of(2025, 1, 1, 1, 1, 1),
                Duration.ofHours(1).plusMinutes(1).plusSeconds(1), 1);
        Epic epic = new Epic("Epic", "D",
                LocalDateTime.of(2025, 1, 1, 1, 1, 1),
                Duration.ofHours(1).plusMinutes(1).plusSeconds(1));

        LocalDateTime end = task.getStartTime().plus(task.getDuration());

        assertEquals(end, task.getEndTime());
        assertEquals(end, subTask.getEndTime());
        assertEquals(end, epic.getEndTime());
    }

    @Test
    void correctCalculationEndTimeForEpic() {
        Epic epic = new Epic("Epic", "D",
                LocalDateTime.of(2025, 1, 1, 1, 1, 1),
                Duration.ofHours(1).plusMinutes(1).plusSeconds(1));
        SubTask subTask1 = new SubTask("SubTask_1", "D",
                LocalDateTime.of(2024, 1, 1, 1, 1, 1),
                Duration.ofHours(1).plusMinutes(1).plusSeconds(1), epic.getID());
        SubTask subTask2 = new SubTask("SubTask_2", "D",
                LocalDateTime.of(2025, 1, 1, 1, 1, 1),
                Duration.ofHours(4), epic.getID());

        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        LocalDateTime start = subTask1.getStartTime();
        LocalDateTime end = subTask2.getEndTime();

        assertEquals(start, epic.getStartTime());
        assertEquals(end, epic.getEndTime());
    }

    @Test
    void getPrioritizedTasks() {
        Task task = new Task("Task", "D",
                LocalDateTime.of(2025, 1, 12, 12, 0, 0), Duration.ZERO);
        Task task2 = new Task("Task2", "D",
                LocalDateTime.of(2026, 1, 12, 12, 0, 0), Duration.ZERO);
        Epic epic = new Epic("Epic", "D",
                LocalDateTime.of(2025, 1, 7, 12, 0, 0), Duration.ZERO);
        SubTask subTask = new SubTask("SubTask", "D",
                LocalDateTime.of(2025, 1, 14, 12, 0, 0), Duration.ZERO, epic.getID());
        SubTask subTask2 = new SubTask("SubTask2", "DD",
                LocalDateTime.of(2025, 1, 1, 12, 1, 0), Duration.ZERO, epic.getID());

        taskManager.addTask(task);
        taskManager.addTask(task2);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);
        taskManager.addSubTask(subTask2);

        List<Task> expectedSet = List.of(subTask2, epic, task, subTask, task2);
        Set<Task> actualSet = taskManager.getPrioritizedTasks();

        assertIterableEquals(expectedSet, actualSet);
    }

    @Test
    void findIntersectionTasksByTime() {
        Task task = new Task("Task", "D",
                LocalDateTime.of(1, 1, 1, 0, 0, 0), Duration.ofDays(7));
        Task task2 = new Task("Task2", "D",
                LocalDateTime.of(1, 1, 2, 0, 0, 0), Duration.ofDays(2));
        Epic epic = new Epic("Epic", "D",
                LocalDateTime.of(1, 1, 11, 0, 0, 1), Duration.ZERO);


        taskManager.addTask(task);
        taskManager.addTask(task2);
        taskManager.addEpic(epic);

        List<Task> expectedList = List.of(task, task2);
        List<Task> actualList = taskManager.findAndFixIntersectionTasksByTime();

        System.out.println(expectedList);
        System.out.println(actualList);
        assertIterableEquals(expectedList, actualList );
    }

    @Test
    void fixIntersectionTasks() {
        Task task1 = new Task("Task1", "D",
                LocalDateTime.of(1, 1, 1, 12, 0, 0),
                Duration.of(2, ChronoUnit.DAYS));
        Task task2 = new Epic("Task2", "D",
                LocalDateTime.of(1, 1, 2, 1, 0, 0),
                Duration.of(10, ChronoUnit.HOURS));
        Task task3 = new Task("Task3", "D",
                LocalDateTime.of(1, 1, 3, 1, 0, 0),
                Duration.of(2, ChronoUnit.DAYS));
        Task task4 = new Epic("Task4", "D");

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);

        taskManager.findAndFixIntersectionTasksByTime();

        assertEquals(task2.getStartTime(), task1.getEndTime().plusMinutes(15));
        assertEquals(task3.getStartTime(), task2.getEndTime().plusMinutes(15));
    }
}
