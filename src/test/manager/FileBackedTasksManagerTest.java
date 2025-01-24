package test.manager;

import main.Task.Epic;
import main.Task.SubTask;
import main.Task.Task;
import main.Task.TaskStatus;
import main.manager.Managers;
import main.manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class FileBackedTasksManagerTest {
    private TaskManager manager;

    @BeforeEach
    public void updateManager() {
        try (Writer writer = new FileWriter(new File("src/CSV.txt"))) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("Error");
        }
        manager = Managers.getFileBackedManager();
    }

    @Test
    public void readAndWriteToFile() {
        Task task = new Task("T1","D",1, TaskStatus.NEW);
        Epic epic = new Epic("E1","D",2,TaskStatus.NEW);
        SubTask subTask = new SubTask("S1","D",3,TaskStatus.DONE,epic.getID());

        manager.addTask(task);
        manager.addEpic(epic);
        manager.addSubTask(subTask);

        List<Task> expectedHistory = manager.getHistory();
        List<Task> expectedTasks = manager.getTasks();
        List<SubTask> expectedSubTasks = manager.getSubTasks();
        List<Epic> expectedEpics = manager.getEpics();

        TaskManager manager2 = Managers.getFileBackedManager();

        Assertions.assertIterableEquals(expectedHistory, manager2.getHistory());
        Assertions.assertIterableEquals(expectedTasks, manager2.getTasks());
        Assertions.assertIterableEquals(expectedSubTasks, manager2.getSubTasks());
        Assertions.assertIterableEquals(expectedEpics, manager2.getEpics());
    }

}
