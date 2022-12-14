package options;

import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Manager {
    private HashMap<Integer, Task> listTask;
    private HashMap<Integer, SubTask> listSubTask;
    private HashMap<Integer, EpicTask> listEpicTask;
    Scanner scanner = new Scanner(System.in);

    public Manager() {
        listTask = new HashMap<>();
        listSubTask = new HashMap<>();
        listEpicTask = new HashMap<>();
    }

    public void setTask(Task task) {
        listTask.put(task.getCurrentId(), task);
    }

    public void setSubTask(SubTask subTask) {
        listSubTask.put(subTask.getCurrentId(), subTask);
    }

    public void setEpicTask(EpicTask epicTask) {
        listEpicTask.put(epicTask.getCurrentId(), epicTask);
    }

    public Task getTask(Integer identifier) {
        System.out.println(listTask.get(identifier).getName());

        return listTask.get(identifier);
    }

    public EpicTask getEpicTask(Integer identifier) {
        System.out.println(listEpicTask.get(identifier).getName());

        return listEpicTask.get(identifier);
    }

    public SubTask getSubTask(Integer identifier) {
        System.out.println(listSubTask.get(identifier).getName());

        return listSubTask.get(identifier);
    }

    public Object getAllTasks() {
        ArrayList<Object> list = new ArrayList<>();

        for (Integer identifier : listTask.keySet()) {
            list.add(getTask(identifier));
        }

        for (Integer identifier : listEpicTask.keySet()) {
            list.add(getEpicTask(identifier));
        }

        for (Integer identifier : listSubTask.keySet()) {
            list.add(getSubTask(identifier));
        }

        if (list.size() == 0) {
            System.out.println("Нет не одной задачи");
        }

        return list;
    }

    public void removeAllTasks() {
        listTask.clear();
        listEpicTask.clear();
        listSubTask.clear();
        System.out.println("Все задачи удаленны");
    }

    public Object getTaskById(Integer id) {
        Object taskById = null;

        for (Task task : listTask.values()) {
            if (task.getCurrentId() == id) {
                taskById = task;
            }
        }

        for (EpicTask task : listEpicTask.values()) {
            if (task.getCurrentId() == id) {
                taskById = task;
            }
        }

        for (SubTask task : listSubTask.values()) {
            if (task.getCurrentId() == id) {
                taskById = task;
            }
        }

        if (taskById != null) {
            System.out.println("Задача с идентификатором - " + id + " - " + taskById);
        } else {
            System.out.println("Задачи не существует");
        }

        return taskById;
    }

    public void createTask() {
        String name;
        String description;

        System.out.println("Введите название задачи");

        name = scanner.next();

        System.out.println("Введите описание задачи");

        description = scanner.next();

        System.out.println("Выберите какую задачу вы хотите создать:");
        System.out.println("1. Обычная;");
        System.out.println("2. Эпик задача;");
        System.out.println("3. Подзадача.");

        int num = scanner.nextInt();

        if (num == 1) {
            Task task = new Task(name, description);
            setTask(task);
        } else if (num == 2) {
            EpicTask epicTask = new EpicTask(name, description);
            setEpicTask(epicTask);
        } else if (num == 3) {
            System.out.println("Введите айди эпик задачи, для которой будет создана эта подзадача");

            int id = scanner.nextInt();
            SubTask subTask = new SubTask(name, description, (EpicTask) getTaskById(id));
            setSubTask(subTask);
        } else {
            System.out.println("Не верная цифра!");
        }

        System.out.println("Задача успешно создана");
    }

    public void updateTask(Object object, Integer id) {
        for (Task task : listTask.values()) {
            if (task.getCurrentId() == id) {
                listTask.put(id, (Task) object);
                System.out.println("Введите статус задачи");
                String status = scanner.next();
                task.setStatus(status);
            }
        }

        System.out.println("Задача обновлена");
    }

    public void updateSubTask(Object object, Integer id) {
        for (Task task : listSubTask.values()) {
            if (task.getCurrentId() == id) {
                listSubTask.put(id, (SubTask) object);
                System.out.println("Введите статус задачи");
                String status = scanner.next();
                task.setStatus(status);
            }
        }

        System.out.println("Задача обновлена");
    }

    public void removeTask() {
        System.out.println("Введите айди задачи которую хотите удалить");

        Integer id = scanner.nextInt();

        for (Integer num : listTask.keySet()) {
            if (listTask.get(num).getCurrentId() == id) {
                listTask.remove(num);
                System.out.println("Задача удалена");
                return;
            }
        }

        for (Integer num : listEpicTask.keySet()) {
            if (listEpicTask.get(num).getCurrentId() == id) {
                listEpicTask.remove(num);
                System.out.println("Задача удалена");
                return;
            }
        }

        for (Integer num : listSubTask.keySet()) {
            if (listSubTask.get(num).getCurrentId() == id) {
                listSubTask.remove(num);
                System.out.println("Задача удалена");
                return;
            }
        }
    }

    public ArrayList<SubTask> getAllSubTaskForEpic(EpicTask epicTask) {
        ArrayList<SubTask> subTasks;

        subTasks = epicTask.getSubTasks();
        System.out.println(subTasks);
        return subTasks;
    }
}
