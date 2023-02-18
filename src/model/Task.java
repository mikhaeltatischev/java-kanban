package model;

import javax.swing.text.DateFormatter;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Task implements Comparable<Task> {
    protected TaskTypes type;
    protected LocalDateTime startTime;
    protected long duration;
    protected LocalDateTime endTime;
    private int id;
    private String name;
    private String description;
    private Status status;

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

    public long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        endTime = calculateEndTime();

        return endTime;
    }

    public LocalDateTime calculateEndTime() {
        try {
            endTime = startTime.plusMinutes(duration);
        } catch (NullPointerException e) {
            endTime = startTime;
            e.getMessage();
        }

        return endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
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

    public void changeStatusToNew() {
        this.status = Status.NEW;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type + "";
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public int getEpicId() {
        return -1;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }

    @Override
    public int compareTo(Task o) {
        ZonedDateTime first = ZonedDateTime.of(startTime, ZoneId.systemDefault());
        ZonedDateTime second = ZonedDateTime.of(o.startTime, ZoneId.systemDefault());
        long timeFirstTask = first.toInstant().toEpochMilli();
        long timeSecondTask = second.toInstant().toEpochMilli();
        return (int) (timeFirstTask - timeSecondTask);
    }
}