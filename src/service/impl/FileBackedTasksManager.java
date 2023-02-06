package service.impl;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskTypes;
import service.HistoryManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    final int NAME_INDEX = 2;
    final int DESCRIPTION_INDEX = 4;
    final int ID_INDEX = 0;
    final int TYPE_INDEX = 1;

    private Path tasksFile;

    public FileBackedTasksManager(String file) throws IOException {
        Path path = Paths.get(file);

        try {
            tasksFile = Files.createFile(path);
        } catch (FileAlreadyExistsException e) {
            tasksFile = path;
        }
    }

    public static void main(String[] args) throws IOException {
        Epic task1 = new Epic("1", "task1");
        Task task2 = new Task("2", "task2");
        Subtask task3 = new Subtask("3", "task3", task1);

        String path = "src\\file\\tasks.txt";

        FileBackedTasksManager manager1 = new FileBackedTasksManager(path);

        manager1.addEpic(task1);
        manager1.addTask(task2);
        manager1.addSub(task3);

        manager1.getTaskById(1);
        manager1.getTaskById(2);
        manager1.getTaskById(3);
        manager1.getTaskById(1);
        manager1.getTaskById(1);
        manager1.getTaskById(3);
        manager1.getTaskById(1);
        manager1.getTaskById(2);

        System.out.println(manager1.getHistoryManager().getHistory());

        FileBackedTasksManager manager2 = loadFromFile(path);

        System.out.println(manager2.getHistoryManager().getHistory());
        manager2.getTaskById(3);
        manager2.getTaskById(2);
        manager2.getTaskById(1);
        System.out.println(manager2.getHistoryManager().getHistory());

    }

    private static FileBackedTasksManager loadFromFile(String file) throws IOException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        FileReader reader = new FileReader(String.valueOf(file));
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<String> lines = new ArrayList<>();

        while (bufferedReader.ready()) {
            lines.add(bufferedReader.readLine());
        }

        int i;
        for (i = 1; i < lines.size(); i++) {
            if (lines.get(i).isEmpty()) {
                break;
            }

            Task task = fileBackedTasksManager.fromString(lines.get(i));

            if (task.getType().equals(TaskTypes.SUBTASK.name())) {
                fileBackedTasksManager.subTasks.put(task.getId(), (Subtask) task);
            } else if (task.getType().equals(TaskTypes.EPIC.name())) {
                fileBackedTasksManager.epics.put(task.getId(), (Epic) task);
            } else {
                fileBackedTasksManager.tasks.put(task.getId(), task);
            }
        }

        List<Integer> history = historyFromString(lines.get(i + 1));

        for (Integer id : history) {
            fileBackedTasksManager.getTaskById(id);
        }

        return fileBackedTasksManager;
    }

    private static String historyToString(HistoryManager manager) {
        List<Task> tasks = manager.getHistory();
        StringBuilder result = new StringBuilder();

        if (tasks.isEmpty()) {
            return "";
        }

        for (Task task : tasks) {
            result.append(task + ",");
        }

        result.deleteCharAt(result.length() - 1);
        return String.valueOf(result);
    }

    private static List<Integer> historyFromString(String value) {
        List<Integer> ids = new ArrayList<>();
        String[] history = value.split(",");

        for (String id : history) {
            ids.add(Integer.parseInt(id));
        }

        return ids;
    }

    private void save() {
        if (Files.exists(tasksFile)) {
            try (FileWriter writer = new FileWriter(String.valueOf(tasksFile))) {
                List<Task> tasks = getAllTasks();
                writer.write("id,type,name,status,description,epic\n");

                for (Task task : tasks) {
                    writer.write(toString(task));
                }

                writer.write("\n");
                writer.write(historyToString(super.getHistoryManager()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Файл не существует");
        }
    }

    private String toString(Task task) {
        int id = task.getId();
        String type = task.getType();
        String name = task.getName();
        String status = task.getStatus();
        String description = task.getDescription();
        String text;

        if (task.getClass() == Subtask.class) {
            int epicId = task.getEpicId();
            text = id + "," + type + "," + name + "," + status + "," + description + "," + epicId + "\n";
        } else {
            text = id + "," + type + "," + name + "," + status + "," + description + "," + "\n";
        }

        return text;
    }

    private Task fromString(String value) {
        String[] array = value.split(",");

        String name = array[NAME_INDEX];
        String description = array[DESCRIPTION_INDEX];
        int id = Integer.parseInt(array[ID_INDEX]);
        String type = array[TYPE_INDEX];

        if (type.equals(TaskTypes.SUBTASK.name())) {
            final int EPIC_ID_INDEX = 5;
            int epicId = Integer.parseInt(array[EPIC_ID_INDEX]);
            Epic current = super.getEpicId(epicId);
            return new Subtask(name, description, current, id);
        } else if (array[1].equals(TaskTypes.EPIC.name())) {
            return new Epic(name, description, id);
        } else {
            return new Task(name, description, id);
        }
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addSub(Subtask subTask) {
        super.addSub(subTask);
        save();
    }

    @Override
    public void addEpic(Epic epicTask) {
        super.addEpic(epicTask);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void updateTask(Task task, int taskId) {
        super.updateTask(task, taskId);
        save();
    }

    @Override
    public void updateSub(Subtask subtask, int subtaskId) {
        super.updateSub(subtask, subtaskId);
        save();
    }

    @Override
    public void updateEpic(Epic epic, int epicId) {
        super.updateEpic(epic, epicId);
        save();
    }

    @Override
    public void removeTask(int removeTaskId) {
        super.removeTask(removeTaskId);
        save();
    }

    @Override
    public HistoryManager getHistoryManager() {
        save();
        return super.getHistoryManager();
    }
}
