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
}
