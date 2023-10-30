package model;

import java.time.LocalDateTime;

public class Subtask extends Task {
    transient private Epic epicTask;

    public Subtask(String name, String description, Epic epicTask) {
        super(name, description);
        this.epicTask = epicTask;
        this.epicTask.addSubTaskToList(this);
        type = TaskType.SUBTASK;
        this.calculateEndTime();
        epicTask.calculateEndTime();
    }

    public Subtask(String name, String description, Epic epicTask, int id) {
        super(name, description, id);
        this.epicTask = epicTask;
        this.epicTask.addSubTaskToList(this);
        type = TaskType.SUBTASK;
        this.calculateEndTime();
        epicTask.calculateEndTime();
    }

    public Subtask(String name, String description, int id, LocalDateTime startTime, Long duration, Epic epicTask) {
        super(name, description, id);
        this.epicTask = epicTask;
        this.epicTask.addSubTaskToList(this);
        this.startTime = startTime;
        this.duration = duration;
        type = TaskType.SUBTASK;
        this.calculateEndTime();
        epicTask.calculateEndTime();
    }

    public Subtask(String name, String description, LocalDateTime startTime, Long duration, Epic epicTask) {
        super(name, description);
        this.epicTask = epicTask;
        this.epicTask.addSubTaskToList(this);
        this.startTime = startTime;
        this.duration = duration;
        type = TaskType.SUBTASK;
        this.calculateEndTime();
        epicTask.calculateEndTime();
    }

    public Epic getEpicTask() {
        return epicTask;
    }

    @Override
    public void changeStatus(Status status) {
        super.changeStatus(status);
        epicTask.changeStatus();
    }
}
