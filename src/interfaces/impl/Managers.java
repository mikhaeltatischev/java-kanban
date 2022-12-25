package interfaces.impl;

import interfaces.TaskManager;

public class Managers {

    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}
