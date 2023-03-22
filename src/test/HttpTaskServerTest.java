package test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;
import server.KVServer;
import server.KVTaskClient;
import service.TaskManager;
import service.impl.Managers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskServerTest {

    private final Gson gson = Managers.getDefaultGson();

    private HttpTaskServer httpTaskServer;
    private TaskManager taskManager;
    private HttpClient httpClient;
    private Task task;
    private KVServer kvServer;

    @BeforeEach
    void beforeEach() throws IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        taskManager = Managers.getDefault();
        httpTaskServer = new HttpTaskServer(taskManager);
        httpClient = HttpClient.newHttpClient();
        task = new Task("Task", "task", 1, LocalDateTime.now(), 100L);

        httpTaskServer.start();
    }

    @AfterEach
    void afterEach() {
        httpTaskServer.stop();
        kvServer.stop();
    }

    @Test
    void getTasks() throws IOException, InterruptedException {
        taskManager.addTask(task);

        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    void addTask() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/task/");
        String json = gson.toJson(task);
        System.out.println(json);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(body)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void removeTask() throws IOException, InterruptedException {
        taskManager.addTask(task);
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void getTaskById() throws IOException, InterruptedException {
        taskManager.addTask(task);
        URI url = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void removeTaskById() throws IOException, InterruptedException {
        taskManager.addTask(task);
        URI url = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void getSubtaskForEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic", "epic");
        Subtask subtask = new Subtask("Subtask", "sub", LocalDateTime.now(), 100L, epic);
        taskManager.addEpic(epic);
        taskManager.addSub(subtask);
        int epicId = epic.getId();

        URI url = URI.create("http://localhost:8080/tasks/subtask/epic/?id=" + epicId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void getHistory() throws IOException, InterruptedException {
        Task task1 = new Task("Task1", "task", 2, LocalDateTime.now().plusMinutes(101L), 10L);
        List<Task> historyList = new ArrayList<>();
        taskManager.addTask(task);
        taskManager.addTask(task1);
        taskManager.getTaskById(task.getId());
        taskManager.getTaskById(task1.getId());
        historyList.add(task);
        historyList.add(task1);

        String jsonHistory = gson.toJson(historyList);

        URI url = URI.create("http://localhost:8080/tasks/history");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(jsonHistory, response.body());
    }

    @Test
    void getPrioritizedTasks() throws IOException, InterruptedException {
        TreeSet<Task> treeSet = new TreeSet<>();
        Task task1 = new Task("Task1", "task", 2, LocalDateTime.now().plusMinutes(101L), 10L);
        taskManager.addTask(task);
        taskManager.addTask(task1);
        treeSet.add(task);
        treeSet.add(task1);

        String jsonPrioritizedTasks = gson.toJson(treeSet);

        URI url = URI.create("http://localhost:8080/tasks/");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(jsonPrioritizedTasks, response.body());
    }
}