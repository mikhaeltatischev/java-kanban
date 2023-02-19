package model;

import java.time.LocalDateTime;

public class Subtask extends Task {
    private Epic epicTask;

    public Subtask(String name, String description, Epic epicTask) {
        super(name, description);
        this.epicTask = epicTask;
        this.epicTask.addSubTaskToList(this);
        type = TaskType.SUBTASK;
    }

    public Subtask(String name, String description, Epic epicTask, int id) {
        super(name, description, id);
        this.epicTask = epicTask;
        this.epicTask.addSubTaskToList(this);
        type = TaskType.SUBTASK;
    }

    public Subtask(String name, String description, Epic epicTask, int id, LocalDateTime startTime, Long duration) {
        super(name, description, id);
        this.epicTask = epicTask;
        this.epicTask.addSubTaskToList(this);
        this.startTime = startTime;
        this.duration = duration;
        type = TaskType.SUBTASK;
    }

    public Epic getEpicTask() {
        return epicTask;
    }

    @Override
    public int getEpicId() {
        return epicTask.getId();
    }

    @Override
    public void changeStatus(Status status) {
        super.changeStatus(status);
        epicTask.changeStatus();
    }
}
