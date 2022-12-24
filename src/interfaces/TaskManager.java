package interfaces;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    void addTask(Task task);

    void addSub(Subtask subTask);

    void addEpic(Epic epicTask);

    ArrayList<Task> getAllTasks();

    void removeAllTasks();

    Task getTaskById(int id);

    void updateTask(Task task, int id);

    void updateSub(Subtask task, int id);

    void updateEpic(Epic task, int id);

    void removeTask();

    void changeStatusTaskToInProgress(Task task);

    void changeStatusSubtaskToInProgress(Subtask subtask);

    void changeStatusTaskDone(Task task);

    void changeStatusSubtaskToDone(Subtask subtask);

    List<Task> getHistory();
}
