import service.HistoryManager;
import service.TaskManager;
import service.impl.InMemoryHistoryManager;
import service.impl.InMemoryTaskManager;
import service.impl.Managers;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new InMemoryTaskManager();
        Epic newYear = new Epic("1", "Организовать новый год у нас дома");
        Epic cleaning = new Epic("2", "Убраться дома");
        Subtask invitations = new Subtask("3", "Пригласить родственников", newYear);
        Subtask buyingFoods = new Subtask("4", "Написать список, сходить в магазин", newYear);
        Subtask toBegin = new Subtask("5", "Приготовить еду и накрыть стол", newYear);
        Subtask toBegin2 = new Subtask("6", "Приготовить еду и накрыть стол", newYear);
        Subtask toBegin3 = new Subtask("7", "Приготовить еду и накрыть стол", newYear);
        Subtask toBegin4 = new Subtask("8", "Приготовить еду и накрыть стол", newYear);
        Subtask toBegin5 = new Subtask("9", "Приготовить еду и накрыть стол", newYear);
        Subtask toBegin6 = new Subtask("10", "Приготовить еду и накрыть стол", newYear);
        Subtask toBegin7 = new Subtask("11", "Приготовить еду и накрыть стол", newYear);
        Scanner scanner = new Scanner(System.in);

        manager.addEpic(newYear);
        manager.addEpic(cleaning);
        manager.addSub(invitations);
        manager.addSub(buyingFoods);
        manager.addSub(toBegin);
        manager.addSub(toBegin2);
        manager.addSub(toBegin3);
        manager.addSub(toBegin4);
        manager.addSub(toBegin5);
        manager.addSub(toBegin6);
        manager.addSub(toBegin7);

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
                System.out.println("Введите идентификатор");
                int id = scanner.nextInt();
                manager.removeTask(id);
            } else if (command == 7) {
                newYear.getSubTasksForEpic();
            } else if (command == 8) {
                System.out.println(manager.getHistoryManager().getHistory());
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
