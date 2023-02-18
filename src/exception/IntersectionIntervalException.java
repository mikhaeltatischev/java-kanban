package exception;

public class IntersectionIntervalException extends Exception {
    String text;

    public IntersectionIntervalException(String string) {
        text = string;
    }

    @Override
    public String getMessage() {
        return text;
    }
}
