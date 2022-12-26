package service.impl;

import service.HistoryManager;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    public static final int HISTORY_SIZE = 10;

    private List<Task> lastViewedTasks = new ArrayList<>();

    public void add(Task task) {
        lastViewedTasks.add(task);

        if (lastViewedTasks.size() > HISTORY_SIZE) {
            lastViewedTasks.remove(0);
        }
    }

    public List<Task> getHistory() {
        for (Task task : lastViewedTasks) {
            System.out.println(task.getName());
        }

        return lastViewedTasks;
    }
}
