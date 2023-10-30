package exception;

public class ManagerSaveException extends RuntimeException {

    private static final String MESSAGE = "Файла не существует";

    public ManagerSaveException() {
        super(MESSAGE);
    }

}