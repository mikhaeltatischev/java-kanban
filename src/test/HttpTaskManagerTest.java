package test;

import com.google.gson.Gson;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.KVServer;
import service.impl.HttpTaskManager;
import service.impl.Managers;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    private KVServer kvServer;
    private Gson gson;

    @BeforeEach
    void beforeEach() throws IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        taskManager = Managers.getDefault();
        gson = Managers.getDefaultGson();
    }

    @AfterEach
    void afterEach() {
        kvServer.stop();
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
    public void testLoadAndSaveWithThreeTasks() throws IOException, InterruptedException {
        HttpTaskManager secondTaskManager;
        Task task = new Task("Task", "task", 1, LocalDateTime.now().minusMinutes(100L), 5L);
        Epic epic = new Epic("Epic", "epic", 2);
        Subtask subtask = new Subtask("Subtask", "subtask", 3, LocalDateTime.now(), 2L, epic);
        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSub(subtask);

        secondTaskManager = taskManager.load("http://localhost:8078");

        assertEquals(3, secondTaskManager.getAllTasks().size());
        assertEquals(0, secondTaskManager.getHistoryManager().getHistory().size());
    }

    @Test
    public void testLoadEmptyData() {
        taskManager.load("http://localhost:8078");

        assertEquals(0, taskManager.getAllTasks().size());
        assertEquals(0, taskManager.getHistoryManager().getHistory().size());
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
