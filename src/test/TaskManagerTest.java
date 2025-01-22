package test;

import main.Task.Epic;
import main.Task.SubTask;
import main.Task.Task;
import main.Task.TaskStatus;
import main.manager.FileBackedTasksManager;
import main.manager.Managers;
import main.manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Optional;

class TaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    public void updateTaskManager() {
        try (Writer writer = new FileWriter(new File("src/CSV.txt"))) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("Error");
        }
        taskManager = Managers.getFileBackedManager();
    }

    @Test
    void getTasks_shouldReturnTasks() {
        Task task1 = new Task("Task_1", "DD", 1, TaskStatus.IN_PROGRESS);
        Task task2 = new Task("Task_2", "DD", 2, TaskStatus.NEW);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        List<Task> expectedList = List.of(task1, task2);
        List<Task> actualList = taskManager.getTasks();
        Assertions.assertIterableEquals(expectedList, actualList);
    }

    @Test
    void getSubTasks_shouldReturnSubtasks() {
        SubTask subTask1 = new SubTask("SubTask_1", "DD", 1, TaskStatus.NEW, 1);
        SubTask subTask2 = new SubTask("SubTask_2", "DD", 2, TaskStatus.DONE, 3);

        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);

        List<SubTask> expectedList = List.of(subTask1, subTask2);
        List<SubTask> actualList = taskManager.getSubTasks();
        Assertions.assertIterableEquals(expectedList, actualList);
    }

    @Test
    void getEpics_shouldReturnEpics() {
        Epic epic1 = new Epic("Epic_1", "DD", 12, TaskStatus.NEW);
        Epic epic2 = new Epic("Epic_2", "DD", 47, TaskStatus.DONE);

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        List<Epic> expectedList = List.of(epic1, epic2);
        List<Epic> actualList = taskManager.getEpics();
        Assertions.assertIterableEquals(expectedList, actualList);
    }

    @Test
    void removeAllTasks_shouldDeleteAllTasks() {
        Task task1 = new Task("Task_1", "DD", 1, TaskStatus.IN_PROGRESS);
        Task task2 = new Task("Task_2", "DD", 2, TaskStatus.NEW);

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.removeAllTasks();

        Assertions.assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void removeAllSubTasks_shouldDeleteAllSubtasks() {
        SubTask task1 = new SubTask("SubTask_1", "DD", 1, TaskStatus.IN_PROGRESS, 12);
        SubTask task2 = new SubTask("SubTask_2", "DD", 2, TaskStatus.NEW, 78);

        taskManager.addSubTask(task1);
        taskManager.addSubTask(task2);
        taskManager.removeAllSubtasks();

        Assertions.assertTrue(taskManager.getSubTasks().isEmpty());
    }

    @Test
    void removeAllEpics_shouldDeleteAllEpics() {
        Epic epic1 = new Epic("Epic_1", "DD", 12, TaskStatus.NEW);
        Epic epic2 = new Epic("Epic_2", "DD", 47, TaskStatus.DONE);

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.removeAllEpics();

        Assertions.assertTrue(taskManager.getEpics().isEmpty());
    }

    @Test
    void getTask_shouldReturnTaskByID() {
        Task expectedTask = new Task("Task_1", "DD", 1, TaskStatus.IN_PROGRESS);

        taskManager.addTask(expectedTask);
        Optional<Task> actualTask = taskManager.getTask(expectedTask.getID());
        Optional<Task> emptyTask = taskManager.getTask(-1);
        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedTask, actualTask.get()),
                () -> Assertions.assertTrue(emptyTask.isEmpty())
        );
    }

    @Test
    void getSubtask_shouldReturnSubtaskByID() {
        SubTask expectedSubTask = new SubTask("SubTask_1", "DD", 1, TaskStatus.IN_PROGRESS, 4);

        taskManager.addSubTask(expectedSubTask);
        Optional<SubTask> actualSubTask = taskManager.getSubTask(expectedSubTask.getID());
        Optional<SubTask> emptySubTask = taskManager.getSubTask(-1);
        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedSubTask, actualSubTask.get()),
                () -> Assertions.assertTrue(emptySubTask.isEmpty())
        );
    }

    @Test
    void getEpic_shouldReturnEpicByID() {
        Epic expectedEpic = new Epic("Epic_1", "DD", 1, TaskStatus.IN_PROGRESS);

        taskManager.addEpic(expectedEpic);
        Optional<Epic> actualEpic = taskManager.getEpic(expectedEpic.getID());
        Optional<Epic> emptyEpic = taskManager.getEpic(-1);
        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedEpic, actualEpic.get()),
                () -> Assertions.assertTrue(emptyEpic.isEmpty())
        );
    }

    @Test
    void updateTask_shouldUpdateTask() {
        Task task = new Task("Task_1", "DD", 1, TaskStatus.NEW);
        Task expectedTask = new Task("Task_2", "DD", 1, TaskStatus.DONE);

        taskManager.addTask(task);
        task.setStatus(TaskStatus.DONE);
        task.setName("Task_2");
        taskManager.updateTask(task);

        Assertions.assertEquals(expectedTask, taskManager.getTask(task.getID()).get());
    }

    @Test
    void updateSubTask_shouldUpdateSubTask() {
        SubTask subTask = new SubTask("SubTask_1", "DD", 1, TaskStatus.NEW, 4);
        SubTask expectedSubTask = new SubTask("SubTask_2", "DD", 1, TaskStatus.DONE, 4);

        taskManager.addSubTask(subTask);
        subTask.setStatus(TaskStatus.DONE);
        subTask.setName("SubTask_2");
        taskManager.updateSubTask(subTask);

        Assertions.assertEquals(expectedSubTask, taskManager.getSubTask(subTask.getID()).get());
    }

    @Test
    void updateEpic_shouldUpdateEpic() {
        Epic epic = new Epic("Epic_1", "DD", 1, TaskStatus.NEW);
        Epic expectedEpic = new Epic("Epic_2", "DD", 1, TaskStatus.DONE);

        taskManager.addEpic(epic);
        epic.setStatus(TaskStatus.DONE);
        epic.setName("Epic_2");
        taskManager.updateEpic(epic);

        Assertions.assertEquals(expectedEpic, taskManager.getEpic(epic.getID()).get());
    }

    @Test
    void removeTask_shouldDeleteTask() {
        Task task1 = new Task("Task_1", "DD", 1, TaskStatus.NEW);
        Task task2 = new Task("Task_2", "DD", 2, TaskStatus.IN_PROGRESS);

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.removeTask(task1.getID());

        Assertions.assertTrue(taskManager.getTask(task2.getID()).isPresent());
        Assertions.assertTrue(taskManager.getTask(task1.getID()).isEmpty());
    }

    @Test
    void removeSubTask_shouldDeleteSubTask() {
        SubTask task1 = new SubTask("Task_1", "DD", 1, TaskStatus.NEW, 4);
        SubTask task2 = new SubTask("Task_2", "DD", 2, TaskStatus.IN_PROGRESS, 7);

        taskManager.addSubTask(task1);
        taskManager.addSubTask(task2);
        taskManager.removeSubTask(task1.getID());

        Assertions.assertTrue(taskManager.getSubTask(task2.getID()).isPresent());
        Assertions.assertTrue(taskManager.getSubTask(task1.getID()).isEmpty());
    }

    @Test
    void removeEpic_shouldDeleteEpic() {
        Epic task1 = new Epic("Task_1", "DD", 1, TaskStatus.NEW);
        Epic task2 = new Epic("Task_2", "DD", 2, TaskStatus.IN_PROGRESS);

        taskManager.addEpic(task1);
        taskManager.addEpic(task2);
        taskManager.removeEpic(task1.getID());

        Assertions.assertTrue(taskManager.getEpic(task2.getID()).isPresent());
        Assertions.assertTrue(taskManager.getEpic(task1.getID()).isEmpty());
    }

    @Test
    void getSubTasksWithEpicID_shouldReturnSubTasksByEpicID() {
        Epic epic = new Epic("Epic_1","DD",3,TaskStatus.NEW);
        SubTask subTask1 = new SubTask("SubTask_1","DD",1,TaskStatus.NEW,3);
        SubTask subTask2 = new SubTask("SubTask_2","DD",2,TaskStatus.NEW,3);
        SubTask subTask3 = new SubTask("SubTask_3","DD",3,TaskStatus.NEW,4);

        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        List<SubTask> expectedList = List.of(subTask1, subTask2);
        List<SubTask> actualList = taskManager.getSubTasksWithEpicID(epic.getID());

        Assertions.assertIterableEquals(expectedList, actualList);
    }

    @Test
    void updateEpicStatus_shouldUpdateEpicStatus() {
        Epic epic = new Epic("Epic_1","DD");
        SubTask subTask1 = new SubTask("SubTask_1","DD",1,TaskStatus.NEW,epic.getID());
        SubTask subTask2 = new SubTask("SubTask_2","DD",2,TaskStatus.NEW,epic.getID());

        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.updateEpicStatus(epic);
        Assertions.assertEquals(TaskStatus.NEW,epic.getStatus());

        subTask1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateEpicStatus(epic);
        Assertions.assertEquals(TaskStatus.IN_PROGRESS,epic.getStatus());

        subTask1.setStatus(TaskStatus.DONE);
        taskManager.updateEpicStatus(epic);
        Assertions.assertEquals(TaskStatus.IN_PROGRESS,epic.getStatus());

        subTask2.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateEpicStatus(epic);
        Assertions.assertEquals(TaskStatus.IN_PROGRESS,epic.getStatus());

        subTask2.setStatus(TaskStatus.DONE);
        taskManager.updateEpicStatus(epic);
        Assertions.assertEquals(TaskStatus.DONE,epic.getStatus());
    }

    @Test
    void getHistory_shouldReturnHistory() {
        Task task1 = new Task("Task_1","DD",1,TaskStatus.NEW);
        Task task2 = new Task("Task_2","DD",2,TaskStatus.NEW);
        Epic epic = new Epic("Epic_1","DD",3,TaskStatus.NEW);
        SubTask subTask1 = new SubTask("SubTask_1","DD",4,TaskStatus.NEW,epic.getID());
        SubTask subTask2 = new SubTask("SubTask_2","DD",5,TaskStatus.NEW,epic.getID());

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);

        Assertions.assertTrue(taskManager.getHistory().isEmpty());

        taskManager.getEpic(epic.getID());
        taskManager.getTask(task2.getID());
        taskManager.getSubTask(subTask2.getID());
        taskManager.getTask(task1.getID());
        taskManager.getSubTask(subTask1.getID());

        List<Task> expectedList = List.of(epic, task2, subTask2, task1, subTask1);
        List<Task> actualList = taskManager.getHistory();
        Assertions.assertIterableEquals(expectedList, actualList);
    }

}
