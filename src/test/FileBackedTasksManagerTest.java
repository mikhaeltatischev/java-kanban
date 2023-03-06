package test;

import exception.ManagerSaveException;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.FileBackedTasksManager;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    private String path;

    @BeforeEach
    public void beforeEach() {
        path = "src//test//file//FileForMethods.txt";
        try {
            super.taskManager = new FileBackedTasksManager(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void afterEach() {
        super.taskManager = null;
    }

    @Test
    public void taskManagerShouldThrowExceptionWithTwoIntersectionTasks() {
        Task task = new Task("task", "task");
        Task task1 = new Task("task1", "task1");

        task.setDuration(20L);
        task1.setDuration(30L);

        task.setStartTime(LocalDateTime.now());
        task1.setStartTime(LocalDateTime.now().plusMinutes(10L));
        task.calculateEndTime();
        task1.calculateEndTime();

        RuntimeException e = assertThrows(RuntimeException.class, () -> {
            taskManager.addTask(task);
            taskManager.addTask(task1);
        });

        assertEquals("exception.IntersectionIntervalException: Пересечение выполнения задач!", e.getMessage());
    }

    @Test
    public void testLoadFromFileAndSaveInFileWithThreeTasks() {
        String newPath = "src//test//file//fileWithThreeTasks.txt";

        try {
            taskManager = taskManager.load(newPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(3, taskManager.getAllTasks().size());
    }

    @Test
    public void testLoadFromFileWithEmptyFile() {
        try {
            taskManager = taskManager.load("src//test//file//EmptyTasksFile.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    public void testThrowManagerSaveException() throws IOException {
        Task task = new Task("task", "task");
        String file = "src//test//file//nonExistentFile.txt";

        try {
            taskManager = new FileBackedTasksManager(file);
            taskManager.addTask(task);
        } catch (ManagerSaveException e) {
            Assertions.assertEquals("Файла не существует", e.getMessage());
        }
    }

    @Test
    public void testRemoveAllTasks() {
        Task task = new Task("task", "task");
        Epic epic = new Epic("epic", "epic");
        Subtask sub = new Subtask("sub", "sub", epic);

        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSub(sub);

        taskManager.removeAllTasks();

        assertEquals(0, taskManager.getAllTasks().size());
        assertEquals(0, taskManager.getHistoryManager().getHistory().size());
    }
}
