package service.impl;

import service.HistoryManager;
import service.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;


public class InMemoryTaskManager implements TaskManager {
    private static int id = 1;
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Subtask> subTasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    private HistoryManager historyManager = Managers.getDefaultHistory();

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public int addTask(Task task) {
        tasks.put(id, task);
        task.setId(id++);
        return task.getId();
    }

    @Override
    public int addSub(Subtask subTask) {
        subTasks.put(id, subTask);
        subTask.setId(id++);
        return subTask.getId();
    }

    @Override
    public int addEpic(Epic epicTask) {
        epicTask.setId(id);
        epics.put(id++, epicTask);
        return epicTask.getId();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> list = new ArrayList<>();

        for (Task task : tasks.values()) {
            list.add(task);
        }

        for (Epic task : epics.values()) {
            list.add(task);
        }

        for (Subtask task : subTasks.values()) {
            list.add(task);
        }

        if (list.size() == 0) {
            System.out.println("Нет не одной задачи");
        }

        return list;
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
        epics.clear();
        subTasks.clear();
        historyManager.removeAllTasks();
        System.out.println("Все задачи удаленны");
    }

    @Override
    public Task getTaskById(int id) {
        Task taskById = null;

        for (Task task : tasks.values()) {
            if (task.getId() == id) {
                taskById = tasks.get(task.getId());
                historyManager.add(task);
                return taskById;
            }
        }

        for (Epic task : epics.values()) {
            if (task.getId() == id) {
                taskById = epics.get(task.getId());
                historyManager.add(task);
                return taskById;
            }
        }

        for (Subtask task : subTasks.values()) {
            if (task.getId() == id) {
                taskById = subTasks.get(task.getId());
                historyManager.add(task);
                return taskById;
            }
        }

        if (taskById != null) {
            System.out.println("Задача с идентификатором - " + id + " - " + taskById);
        } else {
            System.out.println("Задачи не существует");
        }

        return taskById;
    }

    @Override
    public void updateTask(Task task, int taskId) {
        if (tasks.containsValue(taskId)) {
            tasks.put(taskId, task);
        }

        System.out.println("Задача обновлена");
    }

    @Override
    public void updateSub(Subtask subtask, int subtaskId) {
        if (subTasks.containsValue(subtaskId)) {
            subTasks.put(subtaskId, subtask);
        }

        System.out.println("Задача обновлена");
    }

    @Override
    public void updateEpic(Epic epic, int epicId) {
        if (epics.containsValue(epicId)) {
            epics.put(epicId, epic);
        }

        epic.changeStatus();
        System.out.println("Задача обновлена");
    }

    @Override
    public void removeTask(int removeTaskId) {
        for (Integer id : tasks.keySet()) {
            if (id == removeTaskId) {
                tasks.remove(id);
                historyManager.remove(id);
                System.out.println("Задача удалена");
                return;
            }
        }

        for (Integer id : epics.keySet()) {
            if (id == removeTaskId) {
                ArrayList<Subtask> subtasksForEpic = epics.get(id).getSubTasksForEpic();
                for (Subtask subtask : subtasksForEpic) {
                    subTasks.remove(subtask.getId());
                    historyManager.remove(subtask.getId());
                }
                epics.remove(id);
                historyManager.remove(id);
                System.out.println("Задача и подзадачи удаленны");
                return;
            }
        }

        for (Integer id : subTasks.keySet()) {
            if (id == removeTaskId) {
                subTasks.remove(id);
                historyManager.remove(id);
                System.out.println("Задача удалена");
                return;
            }
        }
    }

    public Epic getEpicId(int id) {
        Epic task = epics.get(id);

        return task;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Subtask> getSubTasks() {
        return subTasks;
    }
}
