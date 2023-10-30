package service;

import org.junit.Before;
import service.impl.InMemoryHistoryManager;

public class InMemoryHistoryManagerTest extends HistoryManagerTest<InMemoryHistoryManager> {
    @Before
    public void beforeEach() {
        super.taskManager = new InMemoryHistoryManager();
    }
}
