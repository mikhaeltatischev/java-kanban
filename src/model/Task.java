package model;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Task implements Comparable<Task> {
    protected int id;
    protected String name;
    protected String description;
    protected Status status;
    protected TaskType type;
    protected LocalDateTime startTime;
    protected Long duration;
    protected LocalDateTime endTime;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        status = Status.NEW;
        type = TaskType.TASK;
    }

    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
        status = Status.NEW;
        type = TaskType.TASK;
    }

    public Task(String name, String description, int id, LocalDateTime startTime, long duration) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.startTime = startTime;
        this.duration = duration;
        status = Status.NEW;
        type = TaskType.TASK;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        if (duration == null) {
            if (startTime == null) {
                return null;
            } else {
                return startTime;
            }
        } else {
            return calculateEndTime();
        }
    }

    public LocalDateTime calculateEndTime() {
        endTime = startTime.plusMinutes(duration);

        return endTime;
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

    public String getName() {
        return name;
    }

    public TaskType getType() {
        return type;
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
    public int compareTo(Task task) {
        if (startTime != null && task.startTime != null) {
            ZonedDateTime first = ZonedDateTime.of(startTime, ZoneId.systemDefault());
            ZonedDateTime second = ZonedDateTime.of(task.startTime, ZoneId.systemDefault());
            long timeFirstTask = first.toInstant().toEpochMilli();
            long timeSecondTask = second.toInstant().toEpochMilli();

            return (int) (timeFirstTask - timeSecondTask);
        } else if (startTime == null && task.startTime == null) {
            return this.getId() - task.getId();
        } else {
            if (startTime == null) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}