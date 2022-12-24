package interfaces.impl;

import interfaces.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    Scanner scanner = new Scanner(System.in);
    public static int id = 1;
    private List<Task> listViewedTasks = new ArrayList<>();

    private void addViewedTask(Task task) {
        listViewedTasks.add(task);

        if (listViewedTasks.size() > 10) {
            listViewedTasks.remove(0);
        }
    }

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
                addViewedTask(task);
            }
        }

        for (Epic task : epics.values()) {
            if (task.getId() == id) {
                taskById = epics.get(task.getId());
                addViewedTask(task);
            }
        }

        for (Subtask task : subTasks.values()) {
            if (task.getId() == id) {
                taskById = subTasks.get(task.getId());
                addViewedTask(task);
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
    public void updateTask(Task task, int id) {
        for (Integer element : tasks.keySet()) {
            if (element == id) {
                tasks.put(id, task);
            }
        }

        System.out.println("Задача обновлена");
    }

    @Override
    public void updateSub(Subtask task, int id) {
        for (Integer element : subTasks.keySet()) {
            if (element == id) {
                subTasks.put(id, task);
            }
        }

        System.out.println("Задача обновлена");
    }

    @Override
    public void updateEpic(Epic task, int id) {
        for (Integer element : epics.keySet()) {
            if (element == id) {
                epics.put(id, task);
            }
        }

        task.changeStatus();
        System.out.println("Задача обновлена");
    }

    @Override
    public void removeTask() {
        System.out.println("Введите айди задачи которую хотите удалить");

        Integer id = scanner.nextInt();

        for (Integer num : tasks.keySet()) {
            if (num == id) {
                tasks.remove(num);
                System.out.println("Задача удалена");
                return;
            }
        }

        for (Integer num : epics.keySet()) {
            if (num == id) {
                ArrayList<Subtask> subtasksForEpic = epics.get(num).getSubTasksForEpic();
                for (Subtask subtask : subtasksForEpic) {
                    subTasks.remove(subtask.getId());
                }
                epics.remove(num);
                System.out.println("Задача и подзадачи удаленны");
                return;
            }
        }

        for (Integer num : subTasks.keySet()) {
            if (num == id) {
                subTasks.remove(num);
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

    @Override
    public List<Task> getHistory() {
        for (Task task : listViewedTasks) {
            System.out.println(task.getName());
        }

        return listViewedTasks;
    }
}
