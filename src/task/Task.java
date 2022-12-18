package task;

public class Task {
    public int id;
    protected String name;
    protected String description;
    protected Status status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        status = Status.NEW;
    }

    @Override
    public String toString() {
        return name;
    }
}
enum Status {
    NEW,
    IN_PROGRESS,
    DONE
}
