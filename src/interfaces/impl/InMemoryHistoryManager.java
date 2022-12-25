package interfaces.impl;

import interfaces.HistoryManager;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static List<Task> listViewedTasks = new ArrayList<>();

    public void add(Task task) {
        listViewedTasks.add(task);

        if (listViewedTasks.size() > 10) {
            listViewedTasks.remove(0);
        }
    }

    public List<Task> getHistory() {
        for (Task task : listViewedTasks) {
            System.out.println(task.getName());
        }

        return listViewedTasks;
    }
}
