import com.google.gson.Gson;
import model.Epic;
import model.Subtask;
import server.KVServer;
import server.KVTaskClient;
import service.TaskManager;
import model.Task;
import service.impl.Managers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        KVServer kvServer = new KVServer();
        kvServer.start();
        TaskManager manager = Managers.getDefault();
        Gson gson = Managers.getDefaultGson();

        Scanner scanner = new Scanner(System.in);

        Task task = new Task("Task", "task", 1, LocalDateTime.now(), 5L);
        Task task1 = new Task("Task1", "task", 4, LocalDateTime.now(), 5L);
        Epic epic = new Epic("Epic", "epic", 2);
        Subtask subtask = new Subtask("Subtask", "subtask", 3, LocalDateTime.now().plusMinutes(10), 100L, epic);
        manager.addTask(task);
        manager.addTask(task1);
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
                manager.getTaskById(id);
            } else if (command == 4) {
                Task taskNew = createTask("Новая", "Создание новой задачи");
                manager.addTask(task);
            } else if (command == 5) {

            } else if (command == 6) {
                System.out.println("Введите идентификатор");
                int id = scanner.nextInt();
                manager.removeTask(id);
            } else if (command == 7) {

            } else if (command == 8) {
                System.out.println(manager.getHistoryManager().getHistory());
            } else if (command == 0) {
                kvServer.stop();
            }
        }
    }

    public static void printMenu() {
        System.out.println("Выберите какое действие вы хотите сделать:");
        System.out.println("1. Получить список задач;");
        System.out.println("2. Удалить все задачи;");
        System.out.println("3. Получить задачу по идентификатору;");
        System.out.println("4. Создать задачу;");
        System.out.println("5. Обновить задачу;");
        System.out.println("6. Удаление задачи по идентификатору;");
        System.out.println("7. Получить список подзадач для эпика;");
        System.out.println("8. Получить историю задач;");
        System.out.println("0. Выход.");
    }

    public static Task createTask(String name, String description) {
        Task newTask = new Task(name, description);
        return newTask;
    }
}
