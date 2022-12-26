package service.impl;

import service.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class InMemoryTaskManager implements TaskManager {
    public static int id = 1;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    Scanner scanner = new Scanner(System.in);

    @Override
    public void addTask(Task task) {
        tasks.put(id, task);
        task.setId(id++);
    }

    @Override
    public void addSub(Subtask subTask) {
        subTasks.put(id, subTask);
        subTask.setId(id++);
    }

    @Override
    public void addEpic(Epic epicTask) {
        epicTask.setId(id);
        epics.put(id++, epicTask);
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
        System.out.println("Все задачи удаленны");
    }

    @Override
    public Task getTaskById(int id) {
        Task taskById = null;

        for (Task task : tasks.values()) {
            if (task.getId() == id) {
                taskById = tasks.get(task.getId());
                Managers.getDefaultHistory().add(task);
            }
        }

        for (Epic task : epics.values()) {
            if (task.getId() == id) {
                taskById = epics.get(task.getId());
                Managers.getDefaultHistory().add(task);
            }
        }

        for (Subtask task : subTasks.values()) {
            if (task.getId() == id) {
                taskById = subTasks.get(task.getId());
                Managers.getDefaultHistory().add(task);
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
                System.out.println("Задача удалена");
                return;
            }
        }

        for (Integer id : epics.keySet()) {
            if (id == removeTaskId) {
                ArrayList<Subtask> subtasksForEpic = epics.get(id).getSubTasksForEpic();
                for (Subtask subtask : subtasksForEpic) {
                    subTasks.remove(subtask.getId());
                }
                epics.remove(id);
                System.out.println("Задача и подзадачи удаленны");
                return;
            }
        }

        for (Integer id : subTasks.keySet()) {
            if (id == removeTaskId) {
                subTasks.remove(id);
                System.out.println("Задача удалена");
                return;
            }
        }
    }

    @Override
    public void changeStatusTaskToInProgress(Task task) {
        task.changeStatusToInProgress();
    }

    @Override
    public void changeStatusSubtaskToInProgress(Subtask subtask) {
        subtask.changeStatusToInProgress();
        subtask.getEpicTask().changeStatus();
    }

    @Override
    public void changeStatusTaskDone(Task task) {
        task.changeStatusToDone();
    }

    @Override
    public void changeStatusSubtaskToDone(Subtask subtask) {
        subtask.changeStatusToDone();
        subtask.getEpicTask().changeStatus();
    }
}
