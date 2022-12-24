import interfaces.impl.InMemoryTaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        Epic newYear = new Epic("Празднование нового года", "Организовать новый год у нас дома");
        Subtask invitations = new Subtask("Приглашения", "Пригласить родственников", newYear);
        Subtask buyingFoods = new Subtask("Купить еду", "Написать список, сходить в магазин", newYear);
        Epic cleaning = new Epic("Уборка", "Убраться дома");
        Subtask toBegin = new Subtask("Начать уборку", "Собраться с силами", cleaning);
        Scanner scanner = new Scanner(System.in);

        manager.addEpic(newYear);
        manager.addEpic(cleaning);
        manager.addSub(invitations);
        manager.addSub(buyingFoods);
        manager.addSub(toBegin);

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
                Task task = createTask("Новая", "Создание новой задачи");
                manager.addTask(task);
            } else if (command == 5) {
                manager.updateTask(newYear, 1);
            } else if (command == 6) {
                manager.removeTask();
            } else if (command == 7) {
                newYear.getSubTasksForEpic();
            } else if (command == 8) {
                manager.getHistory();
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
        System.out.println("8. Получить 10 последних просмотренных задач.");
    }

    public static Task createTask(String name, String description) {
        Task newTask = new Task(name, description);
        return newTask;
    }
}
