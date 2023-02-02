package model;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected Status status;
    protected TaskTypes type;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        status = Status.NEW;
        type = TaskTypes.TASK;
    }

    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
        status = Status.NEW;
        type = TaskTypes.TASK;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getType() {
        return type + "";
    }

    public String getStatus() {
        return status + "";
    }

    public String getDescription() {
        return description;
    }

    public int getEpicId() {
        return -1;
    }
}