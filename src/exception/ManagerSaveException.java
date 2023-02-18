package exception;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException() {
    }

    @Override
    public String getMessage() {
        return "Файла не существует";
    }
}
