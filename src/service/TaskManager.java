package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;

public interface TaskManager {

    int addTask(Task task);

    int addSub(Subtask subTask);

    int addEpic(Epic epicTask);

    ArrayList<Task> getAllTasks();

    void removeAllTasks();

    Task getTaskById(int id);

    void updateTask(Task task, int id);

    void updateSub(Subtask task, int id);

    void updateEpic(Epic task, int id);

    void removeTask(int id);

    HistoryManager getHistoryManager();
}
