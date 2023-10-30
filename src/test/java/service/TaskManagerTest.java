package service;

import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class TaskManagerTest<T extends TaskManager> {

    T taskManager;

    @Test
    public void taskManagerShouldContainOneTask() {
        Task task = new Task("task", "task");

        final int id = taskManager.addTask(task);

        final Task currentTask = taskManager.getTaskById(id);

        assertNotNull(currentTask);
        assertEquals(task, currentTask);
    }

    @Test
    public void taskManagerShouldContainOneEpic() {
        Epic task = new Epic("task", "task");

        final int id = taskManager.addTask(task);

        final Task currentTask = taskManager.getTaskById(id);

        assertNotNull(currentTask);
        assertEquals(task, currentTask);
    }

    @Test
    public void taskManagerShouldContainOneSubtask() {
        Epic epic = new Epic("task", "task");
        Subtask task = new Subtask("task", "task", epic);

        final int id = taskManager.addTask(task);

        final Task currentTask = taskManager.getTaskById(id);

        assertNotNull(currentTask);
        assertEquals(task, currentTask);
        assertEquals(epic, task.getEpicTask());
    }

    @Test
    public void taskManagerShouldContainThreeTasks() {
        Task task = new Task("task", "task");
        Epic epic = new Epic("java/model", "epicTask");
        Subtask subtask = new Subtask("subtask", "task", epic);

        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSub(subtask);

        assertEquals(3, taskManager.getAllTasks().size());
    }

    @Test
    public void taskManagerShouldContainNullTasks() {
        Task task = new Task("task", "task");
        Epic epic = new Epic("java/model", "epicTask");
        Subtask subtask = new Subtask("subtask", "task", epic);

        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSub(subtask);
        taskManager.removeAllTasks();

        assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    public void taskManagerShouldRemoveTaskWithOneId() {
        Task task1 = new Task("task", "task");
        Task task2 = new Task("task", "task");

        final int id = taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.removeTask(id);

        assertNull(taskManager.getTaskById(id));
    }

    @Test
    public void testGetTaskByIdWithEmptyTaskManager() {
        assertNull(taskManager.getTaskById(1));
    }

    @Test
    public void testGetAllTasksWithEmptyTaskManager() {
        assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    public void removeEpicWithSubtask() {
        Epic epic = new Epic("java/model", "java/model");
        Subtask sub = new Subtask("sub", "sub", epic);

        final int idEpic = taskManager.addEpic(epic);
        final int idSub = taskManager.addSub(sub);

        taskManager.removeTask(idEpic);

        assertNull(taskManager.getTaskById(idSub));
    }
}
