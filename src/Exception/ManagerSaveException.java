package Exception;

import java.io.IOException;

public class ManagerSaveException extends Exception {
    public ManagerSaveException(String message) {
        System.out.println("Ошибка сохранения");
    }
}
