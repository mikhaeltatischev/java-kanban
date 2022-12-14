package tasks;

public class Task {
    protected String name;
    protected String description;
    protected static int id = 0;
    protected String status;
    protected int currentId;

    public Task(String name, String description) {
        id++;
        this.name = name;
        this.description = description;
        currentId = id;
        status = "NEW";
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status.equals("NEW") || status.equals("DONE") || status.equals("IN_PROGRESS")) {
            this.status = status;
        } else {
            System.out.println("Введен не верный статус");
        }
    }

    public int getCurrentId() {
        return currentId;
    }

    @Override
    public String toString() {
        return name;
    }
}
