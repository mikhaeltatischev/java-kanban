package service;

import model.Task;

import java.util.List;

public interface HistoryManager {
    int add(Task task);

    void remove(int id);

    List<Task> getHistory();

    void removeAllTasks();
}
