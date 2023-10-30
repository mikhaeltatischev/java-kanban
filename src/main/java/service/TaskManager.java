package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    int addTask(Task task);

    int addSub(Subtask subTask);

    int addEpic(Epic epicTask);

    ArrayList<Task> getAllTasks();

    void removeAllTasks();

    Task getTaskById(int id);

    void updateTask(Task task);

    void updateSub(Subtask task);

    void updateEpic(Epic task);

    void removeTask(int id);

    HistoryManager getHistoryManager();

    List<Subtask> getEpicSubTasks(int epicId);

}