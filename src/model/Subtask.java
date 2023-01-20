package model;

public class Subtask extends Task {
    private Epic epicTask;

    public Subtask(String name, String description, Epic epicTask) {
        super(name, description);
        this.epicTask = epicTask;
        this.epicTask.addSubTaskToList(this);
    }

    public Epic getEpicTask() {
        return epicTask;
    }
}
