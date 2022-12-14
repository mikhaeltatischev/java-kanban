import options.Manager;
import tasks.EpicTask;
import tasks.SubTask;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        EpicTask newYear = new EpicTask("Празднование нового года", "Организовать новый год у нас дома");
        SubTask invitations = new SubTask("Приглашения", "Пригласить родственников", newYear);
        SubTask buyingFoods = new SubTask("Купить еду", "Написать список, сходить в магазин", newYear);
        EpicTask cleaning = new EpicTask("Уборка", "Убраться дома");
        SubTask toBegin = new SubTask("Начать уборку", "Собраться с силами", cleaning);
        Scanner scanner = new Scanner(System.in);

        manager.setEpicTask(newYear);
        manager.setEpicTask(cleaning);
        manager.setSubTask(invitations);
        manager.setSubTask(buyingFoods);
        manager.setSubTask(toBegin);

        System.out.println("Трекер задач");

        while (true) {
            printMenu();

            int command = scanner.nextInt();

            if (command == 1) {
                manager.getAllTasks();
            } else if (command == 2) {
                manager.removeAllTasks();
            } else if (command == 3) {
                System.out.println("Введите идентификатор");
                int id = scanner.nextInt();
                manager.getTaskById(id);
            } else if (command == 4) {
                manager.createTask();
            } else if (command == 5) {
                manager.updateTask(newYear, 1);
            } else if (command == 6) {
                manager.removeTask();
            } else if (command == 7) {
                manager.getAllSubTaskForEpic(newYear);
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
    }
}
