package interfaces.impl;

import interfaces.HistoryManager;
import interfaces.TaskManager;

public class Managers {

    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
