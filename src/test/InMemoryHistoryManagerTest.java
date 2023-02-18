package test;

import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.HistoryManager;
import service.TaskManager;
import service.impl.InMemoryHistoryManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest extends HistoryManagerTest<InMemoryHistoryManager> {
    @BeforeEach
    public void beforeEach() {
        super.taskManager = new InMemoryHistoryManager();
    }

    @Test
    public void testAdd() {
        super.testAdd();
    }

    @Test
    public void testRemove() {
        super.testRemove();
    }

    @Test
    public void testAllRemove() {
        super.testAllRemove();
    }

    @Test
    public void testGetHistory() {
        super.testGetHistory();
    }

    @Test
    public void testGetHistoryWithEmptyHistoryOfTasks() {
        super.testGetHistoryWithEmptyHistoryOfTasks();
    }

    @Test
    public void testHistoryWithDuplicates() {
        super.testHistoryWithDuplicates();
    }

    @Test
    public void testRemoveFromStartHistory() {
        super.testRemoveFromStartHistory();
    }

    @Test
    public void testRemoveFromAverageHistory() {
        super.testRemoveFromAverageHistory();
    }

    @Test
    public void testRemoveFromEndHistory() {
        super.testRemoveFromEndHistory();
    }
}
