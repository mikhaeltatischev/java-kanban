package service;

import task.Epic;
import task.Sub;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Manager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Sub> subTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    Scanner scanner = new Scanner(System.in);
    public static int id = 0;

    public void addTask(Task task) {
        tasks.put(id++, task);
        task.id = id;
    }

    public void addSub(Sub subTask) {
        subTasks.put(id++, subTask);
        subTask.id = id;
    }

    public void addEpic(Epic epicTask) {
        epics.put(id++, epicTask);
        epicTask.id = id;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> list = new ArrayList<>();

        for (Task task : tasks.values()) {
            list.add(task);
        }

        for (Epic task : epics.values()) {
            list.add(task);
        }

        for (Sub task : subTasks.values()) {
            list.add(task);
        }

        if (list.size() == 0) {
            System.out.println("Нет не одной задачи");
        }

        return list;
    }

    public void removeAllTasks() {
        tasks.clear();
        epics.clear();
        subTasks.clear();
        System.out.println("Все задачи удаленны");
    }

    public Task getTaskById(int id) {
        Task taskById = null;

        for (Integer index : tasks.keySet()) {
            if (index == id) {
                taskById = tasks.get(index);
            }
        }

        for (Integer index : epics.keySet()) {
            if (index == id) {
                taskById = epics.get(index);
            }
        }

        for (Integer index : subTasks.keySet()) {
            if (index == id) {
                taskById = subTasks.get(index);
            }
        }

        if (taskById != null) {
            System.out.println("Задача с идентификатором - " + id + " - " + taskById);
        } else {
            System.out.println("Задачи не существует");
        }

        return taskById;
    }

    public void updateTask(Task task, int id) {
        for (Integer element : tasks.keySet()) {
            if (element == id) {
                tasks.put(id, task);
            }
        }

        System.out.println("Задача обновлена");
    }

    public void updateSub(Sub task, int id) {
        for (Integer element : subTasks.keySet()) {
            if (element == id) {
                subTasks.put(id, task);
            }
        }

        System.out.println("Задача обновлена");
    }

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
                epics.remove(num);
                System.out.println("Задача удалена");
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
}
