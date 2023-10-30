package service;

import model.Task;
import org.junit.Test;
import service.HistoryManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

abstract class HistoryManagerTest<T extends HistoryManager> {
    T taskManager;

    @Test
    public void testAdd() {
        Task task = new Task("task", "task");

        taskManager.add(task);

        assertEquals(1, taskManager.getHistory().size());
    }

    @Test
    public void testRemove() {
        Task task = new Task("task", "task");

        taskManager.add(task);
        taskManager.removeAllTasks();

        assertEquals(0, taskManager.getHistory().size());
    }

    @Test
    public void testAllRemove() {
        Task task1 = new Task("task1", "task");
        Task task2 = new Task("task2", "task");
        Task task3 = new Task("task3", "task");

        taskManager.add(task1);
        taskManager.add(task2);
        taskManager.add(task3);
        taskManager.removeAllTasks();

        assertEquals(0, taskManager.getHistory().size());
    }

    @Test
    public void testGetHistory() {
        List<Task> listTasks = new ArrayList<>();

        Task task1 = new Task("task1", "task");
        Task task2 = new Task("task2", "task");
        Task task3 = new Task("task3", "task");

        task1.setId(1);
        task2.setId(2);
        task3.setId(3);
        taskManager.add(task1);
        taskManager.add(task2);
        taskManager.add(task3);
        listTasks.add(task1);
        listTasks.add(task2);
        listTasks.add(task3);

        assertEquals(listTasks, taskManager.getHistory());
    }

    @Test
    public void testGetHistoryWithEmptyHistoryOfTasks() {
        List<Task> listTasks = new ArrayList<>();

        assertEquals(listTasks, taskManager.getHistory());
    }

    @Test
    public void testHistoryWithDuplicates() {
        List<Task> listTasks = new ArrayList<>();

        Task task1 = new Task("task1", "task");
        Task task2 = new Task("task2", "task");

        task1.setId(1);
        task2.setId(2);
        taskManager.add(task1);
        taskManager.add(task2);
        taskManager.add(task1);
        taskManager.add(task2);
        listTasks.add(task1);
        listTasks.add(task2);

        assertEquals(listTasks, taskManager.getHistory());
    }

    @Test
    public void testRemoveFromStartHistory() {
        List<Task> listTasks = new ArrayList<>();

        Task task1 = new Task("task1", "task");
        Task task2 = new Task("task2", "task");
        Task task3 = new Task("task3", "task");

        task1.setId(1);
        task2.setId(2);
        task3.setId(3);
        taskManager.add(task1);
        taskManager.add(task2);
        taskManager.add(task3);
        listTasks.add(task2);
        listTasks.add(task3);

        taskManager.remove(1);

        assertEquals(listTasks, taskManager.getHistory());
    }

    @Test
    public void testRemoveFromAverageHistory() {
        List<Task> listTasks = new ArrayList<>();

        Task task1 = new Task("task1", "task");
        Task task2 = new Task("task2", "task");
        Task task3 = new Task("task3", "task");

        task1.setId(1);
        task2.setId(2);
        task3.setId(3);
        taskManager.add(task1);
        taskManager.add(task2);
        taskManager.add(task3);
        listTasks.add(task1);
        listTasks.add(task3);

        taskManager.remove(2);

        assertEquals(listTasks, taskManager.getHistory());
    }

    @Test
    public void testRemoveFromEndHistory() {
        List<Task> listTasks = new ArrayList<>();

        Task task1 = new Task("task1", "task");
        Task task2 = new Task("task2", "task");
        Task task3 = new Task("task3", "task");

        task1.setId(1);
        task2.setId(2);
        task3.setId(3);
        taskManager.add(task1);
        taskManager.add(task2);
        taskManager.add(task3);
        listTasks.add(task1);
        listTasks.add(task2);

        taskManager.remove(3);

        assertEquals(listTasks, taskManager.getHistory());
    }
}
