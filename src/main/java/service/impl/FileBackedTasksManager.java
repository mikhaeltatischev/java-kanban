package service.impl;

import exception.IntersectionIntervalException;
import exception.ManagerSaveException;
import model.*;
import service.HistoryManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class FileBackedTasksManager extends InMemoryTaskManager {
    final int ID_INDEX = 0;
    final int TYPE_INDEX = 1;
    final int NAME_INDEX = 2;
    final int STATUS_INDEX = 3;
    final int DESCRIPTION_INDEX = 4;
    final int EPIC_ID_INDEX = 5;
    final int START_TIME_INDEX = 6;
    final int DURATION_INDEX = 7;
    final int END_TIME_INDEX = 8;

    private Path tasksFile;
    private TreeSet<Task> treeSet;

    public FileBackedTasksManager(String file) throws IOException {
        Path path = Paths.get(file);
        if (!Files.exists(path)) {
            throw new ManagerSaveException();
        }
        tasksFile = path;
        treeSet = new TreeSet<>();
    }

    public FileBackedTasksManager() {
        treeSet = new TreeSet<>();
    }

    public FileBackedTasksManager load(String file) throws IOException {
        FileBackedTasksManager fileBackedTasksManager;

        try (FileReader reader = new FileReader(String.valueOf(file));
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            fileBackedTasksManager = new FileBackedTasksManager(file);
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

                if (task.getType().equals(TaskType.SUBTASK.name())) {
                    fileBackedTasksManager.subTasks.put(task.getId(), (Subtask) task);
                } else if (task.getType().equals(TaskType.EPIC.name())) {
                    fileBackedTasksManager.epics.put(task.getId(), (Epic) task);
                } else {
                    fileBackedTasksManager.tasks.put(task.getId(), task);
                }
            }

            List<Integer> history;

            try {
                history = historyFromString(lines.get(i + 1));

                for (Integer id : history) {
                    fileBackedTasksManager.getTaskById(id);
                }
            } catch (IndexOutOfBoundsException e) {
                e.getMessage();
            }
        } catch (IOException e) {
            throw new ManagerSaveException();
        }

        for (Epic epic : fileBackedTasksManager.getEpics().values()) {
            epic.changeStatus();
            epic.calculateEndTime();
        }

        return fileBackedTasksManager;
    }

    protected String historyToString(HistoryManager manager) {
        List<Task> tasks = manager.getHistory();
        StringBuilder result = new StringBuilder();

        if (tasks.isEmpty()) {
            return "";
        }

        for (Task task : tasks) {
            result.append(task.getId() + ",");
        }

        result.deleteCharAt(result.length() - 1);
        return String.valueOf(result);
    }

    protected List<Integer> historyFromString(String value) {
        List<Integer> ids = new ArrayList<>();
        String[] history = value.split(",");

        for (String id : history) {
            ids.add(Integer.parseInt(id));
        }

        return ids;
    }

    public void setTasksFile(String tasksFile) {
        this.tasksFile = Paths.get(tasksFile);
    }

    public TreeSet<Task> getPrioritizedTasks() {
        return treeSet;
    }

    public void sortPrioritizedTasks() {
        for (Task task : getTasks().values()) {
            treeSet.add(task);
        }

        for (Task task : getSubTasks().values()) {
            treeSet.add(task);
        }

        treeSet.stream().sorted();
    }

    protected void checkIntersection() throws IntersectionIntervalException {
        TreeSet<Task> tasks = getPrioritizedTasks();
        List<Task> sortedList = new ArrayList<>();

        for (Task task : tasks) {
            sortedList.add(task);
        }

        for (int i = 0; i < sortedList.size(); i++) {
            Task currentTask = sortedList.get(i);
            for (int j = 0; j < sortedList.size(); j++) {
                if (currentTask == sortedList.get(j)) {
                    continue;
                }
                LocalDateTime firstStart = currentTask.getStartTime();
                LocalDateTime firstEnd = currentTask.getEndTime();
                LocalDateTime secondStart = sortedList.get(j).getStartTime();
                LocalDateTime secondEnd = sortedList.get(j).getEndTime();

                if (firstStart == null || secondStart == null) {
                    return;
                }

                if (firstStart.isAfter(secondEnd) || firstEnd.isBefore(secondStart)) {
                    continue;
                } else {
                    throw new IntersectionIntervalException("Пересечение выполнения задач!");
                }
            }
        }
    }

    protected void save() {
        try (FileWriter writer = new FileWriter(String.valueOf(tasksFile))) {
            List<Task> tasks = getAllTasks();
            writer.write("id,type,name,status,description,epic,startTime,duration,endTime\n");

            for (Task task : tasks) {
                writer.write(task.toString());
            }

            writer.write("\n");
            writer.write(historyToString(super.getHistoryManager()));
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }

    protected Task fromString(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy-HH:mm");
        String[] array = value.split(",");

        String status = array[STATUS_INDEX];
        String name = array[NAME_INDEX];
        String description = array[DESCRIPTION_INDEX];
        int id = Integer.parseInt(array[ID_INDEX]);
        String type = array[TYPE_INDEX];
        String startTimeInString;
        long duration;
        String endTimeInString;

        if (type.equals(TaskType.SUBTASK)) {
            startTimeInString = array[START_TIME_INDEX];
            duration = Long.parseLong(array[DURATION_INDEX]);
            endTimeInString = array[END_TIME_INDEX];
        } else {
            startTimeInString = array[START_TIME_INDEX - 1];
            duration = Long.parseLong(array[DURATION_INDEX - 1]);
            endTimeInString = array[END_TIME_INDEX - 1];
        }

        if (endTimeInString == null) {
            endTimeInString = "00.00.00-00:00";
        }

        if (startTimeInString == null) {
            startTimeInString = "00.00.00-00:00";
        }

        if (type.equals(TaskType.SUBTASK.name())) {
            int epicId = Integer.parseInt(array[EPIC_ID_INDEX]);
            Epic current = super.getEpicId(epicId);
            Subtask subtask = new Subtask(name, description, current, id);
            LocalDateTime startTime = LocalDateTime.parse(startTimeInString, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endTimeInString, formatter);

            subtask.setStartTime(startTime);
            subtask.setDuration(duration);
            subtask.changeStatus(Status.valueOf(status));

            return subtask;
        } else if (type.equals(TaskType.EPIC.name())) {
            Epic epic = new Epic(name, description, id);

            LocalDateTime startTime = LocalDateTime.parse(startTimeInString, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endTimeInString, formatter);

            epic.setStartTime(startTime);
            epic.setDuration(duration);

            return epic;
        } else {
            Task task = new Task(name, description, id);

            LocalDateTime startTime = LocalDateTime.parse(startTimeInString, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endTimeInString, formatter);

            task.setStartTime(startTime);
            task.setDuration(duration);
            task.changeStatus(Status.valueOf(status));

            return task;
        }
    }

    @Override
    public int addTask(Task task) {
        super.addTask(task);
        save();
        sortPrioritizedTasks();

        try {
            checkIntersection();
        } catch (IntersectionIntervalException e) {
            throw new RuntimeException(e);
        }

        return task.getId();
    }

    @Override
    public int addSub(Subtask subTask) {
        super.addSub(subTask);
        save();
        sortPrioritizedTasks();

        try {
            checkIntersection();
        } catch (IntersectionIntervalException e) {
            throw new RuntimeException(e);
        }

        return subTask.getId();
    }

    @Override
    public int addEpic(Epic epicTask) {
        super.addEpic(epicTask);
        save();

        try {
            checkIntersection();
        } catch (IntersectionIntervalException e) {
            throw new RuntimeException(e);
        }

        return epicTask.getId();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSub(Subtask subtask) {
        super.updateSub(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void removeTask(int removeTaskId) {
        super.removeTask(removeTaskId);
        save();
    }

    @Override
    public HistoryManager getHistoryManager() {
        return super.getHistoryManager();
    }
}
