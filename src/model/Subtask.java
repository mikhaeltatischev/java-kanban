package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public int getEpicId() {
        return epicTask.getId();
    }

    @Override
    public void changeStatus(Status status) {
        super.changeStatus(status);
        epicTask.changeStatus();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy-HH:mm");
        String startTimeString = null;
        String endTimeString = null;
        String duration = null;


        if (getStartTime() != null) {
            startTimeString = getStartTime().format(formatter);
        }

        if (getEndTime() != null) {
            endTimeString = getEndTime().format(formatter);
        }

        if (getDuration() != null) {
            duration = String.valueOf(getDuration());
        }

        String text = getId() + "," + getType() + "," + getName() + "," + getStatus() + "," + getDescription() + "," +
                getEpicId() + "," + startTimeString + "," + duration + "," + endTimeString + "\n";

        return text;
    }
}
