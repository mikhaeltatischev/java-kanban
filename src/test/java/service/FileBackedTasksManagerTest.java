package service;

import exception.ManagerSaveException;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.impl.FileBackedTasksManager;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    private String path;

    @Before
    public void beforeEach() {
        path = "src/test/resources/test.txt";
        try {
            super.taskManager = new FileBackedTasksManager(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
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
    public void testThrowManagerSaveException() throws IOException {
        Task task = new Task("task", "task");
        String file = "src//test//file//nonExistentFile.txt";

        try {
            taskManager = new FileBackedTasksManager(file);
            taskManager.addTask(task);
        } catch (ManagerSaveException e) {
            assertEquals("Файла не существует", e.getMessage());
        }
    }

    @Test
    public void testRemoveAllTasks() {
        Task task = new Task("task", "task");
        Epic epic = new Epic("java/model", "java/model");
        Subtask sub = new Subtask("sub", "sub", epic);

        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSub(sub);

        taskManager.removeAllTasks();

        assertEquals(0, taskManager.getAllTasks().size());
        assertEquals(0, taskManager.getHistoryManager().getHistory().size());
    }
}
