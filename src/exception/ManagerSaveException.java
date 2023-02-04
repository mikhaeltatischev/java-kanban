package exception;

public class ManagerSaveException extends Exception {
    public ManagerSaveException(String message) {
        System.out.println("Ошибка сохранения");
    }
}
