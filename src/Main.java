import com.google.gson.Gson;
import model.Epic;
import model.Subtask;
import model.TaskType;
import server.HttpTaskServer;
import server.KVServer;
import server.KVTaskClient;
import service.TaskManager;
import model.Task;
import service.impl.Managers;

import java.io.IOException;
import java.net.http.HttpClient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();
        KVServer kvServer = new KVServer();
        kvServer.start();
        TaskManager manager = Managers.getDefault();
        HttpTaskServer httpTaskServer = new HttpTaskServer(manager);
        Gson gson = Managers.getDefaultGson();
        httpTaskServer.start();

        Scanner scanner = new Scanner(System.in);

        Task task = new Task("Task", "task", 1, LocalDateTime.now(), 5L);
        Epic epic = new Epic("Epic", "epic", 2);
        Subtask subtask = new Subtask("Subtask", "subtask", 3, LocalDateTime.now().plusMinutes(10), 100L, epic);
        manager.addTask(task);
        manager.addEpic(epic);
        manager.addSub(subtask);

        System.out.println("Трекер задач");

        while (true) {
            printMenu();

            int command = scanner.nextInt();

            if (command == 1) {
                manager.getAllTasks();
                System.out.println(manager.getAllTasks());
            } else if (command == 2) {
                manager.removeAllTasks();
            } else if (command == 3) {
                System.out.println("Введите идентификатор");
                int id = scanner.nextInt();
                System.out.println(manager.getTaskById(id));
            } else if (command == 4) {
                System.out.println("Введите тип задачи");
                scanner.nextLine();
                String type = scanner.nextLine();
                System.out.println("Введите название задачи");
                String name = scanner.nextLine();
                System.out.println("Введите описание задачи");
                String description = scanner.nextLine();
                switch (type) {
                    case ("Task"):
                        manager.addTask(createTask(name, description));
                        break;
                    case ("Epic"):
                        manager.addEpic(createEpic(name, description));
                        break;
                    case ("Subtask"):
                        System.out.println("Введите id эпика для этой подзадачи");
                        int id = scanner.nextInt();
                        Task currentTask = manager.getTaskById(id);

                        if (currentTask.getType().equals(TaskType.EPIC)) {
                            manager.addSub(createSubtask(name, description, (Epic) currentTask));
                        }
                        break;
                    default:
                        System.out.println("Не верный тип задачи");
                        break;
                }
            } else if (command == 5) {
                System.out.println("Введите идентификатор");
                int id = scanner.nextInt();
                manager.removeTask(id);
            } else if (command == 6) {
                System.out.println("Введите идентификатор");
                int id = scanner.nextInt();
                if (!manager.getTaskById(id).getType().equals(TaskType.EPIC)) {
                    System.out.println("Выбрана не эпик задача");
                } else {
                    System.out.println(manager.getEpicSubTasks(id));
                }
            } else if (command == 7) {
                System.out.println(manager.getHistoryManager().getHistory());
            } else if (command == 0) {
                kvServer.stop();
                httpTaskServer.stop();
                return;
            }
        }
    }

    public static void printMenu() {
        System.out.println("Выберите какое действие вы хотите сделать:");
        System.out.println("1. Получить список задач;");
        System.out.println("2. Удалить все задачи;");
        System.out.println("3. Получить задачу по идентификатору;");
        System.out.println("4. Создать задачу;");
        System.out.println("5. Удаление задачи по идентификатору;");
        System.out.println("6. Получить список подзадач для эпика;");
        System.out.println("7. Получить историю задач;");
        System.out.println("0. Выход.");
    }

    public static Task createTask(String name, String description) {
        Task newTask = new Task(name, description);
        return newTask;
    }

    public static Epic createEpic(String name, String description) {
        Epic newTask = new Epic(name, description);
        return newTask;
    }

    public static Subtask createSubtask(String name, String description, Epic epic) {
        Subtask newTask = new Subtask(name, description, epic);
        return newTask;
    }
}
