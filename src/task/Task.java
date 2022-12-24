package task;

public class Task {
    protected int id;
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void changeStatusToInProgress() {
        this.status = Status.IN_PROGRESS;
    }

    public void changeStatusToDone() {
        this.status = Status.DONE;
    }

    public String getName() {
        return name;
    }
}