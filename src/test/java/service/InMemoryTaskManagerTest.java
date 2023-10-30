package service;

import org.junit.After;
import org.junit.Before;
import service.impl.InMemoryTaskManager;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @Before
    public void beforeEach() {
        super.taskManager = new InMemoryTaskManager();
    }

    @After
    public void afterEach() {
        super.taskManager = null;
    }
}
