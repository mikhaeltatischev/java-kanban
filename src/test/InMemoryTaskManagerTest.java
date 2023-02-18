package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.InMemoryTaskManager;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    public void beforeEach() {
        super.taskManager = new InMemoryTaskManager();
    }

    @AfterEach
    public void afterEach() {
        super.taskManager = null;
    }

    @Test
    public void taskManagerShouldContainOneTask() {
        super.taskManagerShouldContainOneTask();
    }

    @Test
    public void taskManagerShouldContainOneEpic() {
        super.taskManagerShouldContainOneEpic();
    }

    @Test
    public void taskManagerShouldContainOneSubtask() {
        super.taskManagerShouldContainOneSubtask();
    }

    @Test
    public void taskManagerShouldContainThreeTasks() {
        super.taskManagerShouldContainThreeTasks();
    }

    @Test
    public void taskManagerShouldContainNullTasks() {
        super.taskManagerShouldContainNullTasks();
    }

    @Test
    public void taskManagerShouldRemoveTaskWithOneId() {
        super.taskManagerShouldRemoveTaskWithOneId();
    }

    @Test
    public void testGetTaskByIdWithEmptyTaskManager() {
        super.testGetTaskByIdWithEmptyTaskManager();
    }

    @Test
    public void testGetAllTasksWithEmptyTaskManager() {
        super.testGetAllTasksWithEmptyTaskManager();
    }

    @Test
    public void removeEpicWithSubtask() {
        super.removeEpicWithSubtask();
    }
}
