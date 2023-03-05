package service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import exception.IntersectionIntervalException;
import exception.ManagerSaveException;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskType;
import server.KVTaskClient;
import service.HistoryManager;

import java.io.IOException;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {
    KVTaskClient client;
    String url;
    Gson gson;

    public HttpTaskManager(String url) throws IOException, InterruptedException {
        this.url = url;
        client = new KVTaskClient(url);
        gson = Managers.getDefaultGson();
    }

    @Override
    public HttpTaskManager load(String url) {
        HttpTaskManager httpTaskManager;

        try {
            httpTaskManager = new HttpTaskManager(url);

            JsonElement jsonTasks = JsonParser.parseString(client.load("Tasks"));
            JsonElement jsonHistory = JsonParser.parseString(client.load("History"));

            if (!jsonTasks.isJsonNull()) {
                JsonArray jsonArrayTasks = jsonTasks.getAsJsonArray();

                String[] history = jsonHistory.getAsString().split(",");

                for (JsonElement task : jsonArrayTasks) {
                    Task currentTask = gson.fromJson(task, Task.class);

                    if (currentTask.getType().equals(TaskType.SUBTASK.name())) {
                        httpTaskManager.subTasks.put(currentTask.getId(), (Subtask) currentTask);
                    } else if (currentTask.getType().equals(TaskType.EPIC.name())) {
                        httpTaskManager.epics.put(currentTask.getId(), (Epic) currentTask);
                    } else {
                        httpTaskManager.tasks.put(currentTask.getId(), currentTask);
                    }
                }

                if (!history[0].isBlank()) {
                    for (int i = 0; i < history.length; i++) {
                        Task currentTask = httpTaskManager.getTaskById(Integer.parseInt(history[i]));

                        if (currentTask.getType() == TaskType.EPIC) {
                            Epic epic = (Epic) currentTask;
                            epic.changeStatus();
                            epic.calculateEndTime();
                        }
                    }
                }
            } else {
                return httpTaskManager;
            }
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException();
        }

        return httpTaskManager;
    }

    @Override
    protected void save() {
        List<Task> tasks = getAllTasks();
        String history = historyToString(super.getHistoryManager());

        try {
            client.put("Tasks", gson.toJson(tasks));
            client.put("History", gson.toJson(history));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
            super.checkIntersection();
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
        save();
        return super.getHistoryManager();
    }
}
