package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import model.Epic;
import model.Subtask;
import model.Task;
import service.TaskManager;
import service.impl.FileBackedTasksManager;
import service.impl.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    private final static int PORT = 8080;

    private TaskManager taskManager;
    private HttpServer server;
    private Gson gson;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        this.server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        this.server.createContext("/tasks", this::handleTasks);
        this.gson = Managers.getDefaultGson();
    }

    public HttpTaskServer() throws IOException, InterruptedException {
        this.taskManager = Managers.getDefault();
        this.server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        this.server.createContext("/tasks", this::handleTasks);
        this.gson = Managers.getDefaultGson();
    }

    private void handleTasks(HttpExchange httpExchange) {
        try {
            String path = httpExchange.getRequestURI().getPath();
            String requestMethod = httpExchange.getRequestMethod();
            String query = httpExchange.getRequestURI().getQuery();
            switch (requestMethod) {
                case "GET": {
                    if (query == null) {
                        if (Pattern.matches("^/tasks/task/$", path)) {
                            String response = gson.toJson(taskManager.getAllTasks());
                            sendText(httpExchange, response);
                            break;
                        }

                        if (Pattern.matches("^/tasks/$", path)) {
                            FileBackedTasksManager fileBackedTasksManager = (FileBackedTasksManager) taskManager;
                            String response = gson.toJson(fileBackedTasksManager.getPrioritizedTasks());
                            sendText(httpExchange, response);
                            break;
                        }

                        if (Pattern.matches("^/tasks/history$", path)) {
                            FileBackedTasksManager fileBackedTasksManager = (FileBackedTasksManager) taskManager;
                            String response = gson.toJson(fileBackedTasksManager.getHistoryManager().getHistory());
                            sendText(httpExchange, response);
                            break;
                        }
                    } else {
                        if (Pattern.matches("^/tasks/task/$", path)) {
                            int id = queryToId(query);

                            if (id != -1) {
                                String response = gson.toJson(taskManager.getTaskById(id));
                                sendText(httpExchange, response);
                                break;
                            }
                        }

                        if (Pattern.matches("^/tasks/subtask/epic/$", path)) {
                            int id = queryToId(query);

                            if (id != -1) {
                                String response = gson.toJson(taskManager.getEpicSubTasks(id));
                                sendText(httpExchange, response);
                                break;
                            }
                        }
                    }

                    httpExchange.sendResponseHeaders(405, 0);
                    break;
                }
                case "POST": {
                    String body = readText(httpExchange);

                    if (Pattern.matches("^/tasks/task/$", path)) {
                        Task task = gson.fromJson(body, Task.class);

                        if (taskManager.getAllTasks().contains(task)) {
                            taskManager.updateTask(task);
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        } else {
                            taskManager.addTask(task);
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        }
                    }

                    if (Pattern.matches("^/tasks/subtask/$", path)) {
                        Subtask subtask = gson.fromJson(body, Subtask.class);

                        if (taskManager.getAllTasks().contains(subtask)) {
                            taskManager.updateSub(subtask);
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        } else {
                            taskManager.addSub(subtask);
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        }
                    }

                    if (Pattern.matches("^/tasks/epic/$", path)) {
                        Epic epic = gson.fromJson(body, Epic.class);

                        if (taskManager.getAllTasks().contains(epic)) {
                            taskManager.updateTask(epic);
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        } else {
                            taskManager.addTask(epic);
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        }
                    }

                    httpExchange.sendResponseHeaders(405, 0);
                    break;
                }
                case "DELETE": {
                    if (query == null) {
                        if (Pattern.matches("^/tasks/task/$", path)) {
                            taskManager.removeAllTasks();
                            System.out.println("Задачи успешно удалены");
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        }
                    } else {
                        if (Pattern.matches("^/tasks/task/$", path)) {
                            int id = queryToId(query);

                            if (id != -1) {
                                taskManager.removeTask(id);
                                System.out.println("Задача с id - " + id + " удалена.");
                                httpExchange.sendResponseHeaders(200, 0);
                                break;
                            } else {
                                System.out.println("Получен не верный id = " + id);
                                httpExchange.sendResponseHeaders(405, 0);
                                break;
                            }
                        }
                    }

                    httpExchange.sendResponseHeaders(405, 0);
                    break;
                }
                default: {
                    System.out.println("Не верный метод запроса - " + requestMethod);
                    httpExchange.sendResponseHeaders(405, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpExchange.close();
        }
    }

    private int parsePathId(String pathId) {
        try {
            return Integer.parseInt(pathId);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        server.start();
    }

    private String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    private void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили сервер на порту - " + PORT);
    }

    private void writeResponse(HttpExchange httpExchange, String response, int responseCode) throws IOException {
        if (response.isBlank()) {
            httpExchange.sendResponseHeaders(responseCode, 0);
        } else {
            sendText(httpExchange, response);
        }
    }

    private Integer queryToId(String query) {
        Integer id = null;

        if (query.isBlank()) {
            return null;
        }

        if (Pattern.matches("^id=\\d+$", query)) {
            id = parsePathId(query.replaceFirst("id=", ""));
        }

        return id;
    }
}
