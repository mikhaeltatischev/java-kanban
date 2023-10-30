package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class Task implements Comparable<Task> {

    protected int id;
    protected String name;
    protected String description;
    protected Status status;
    protected TaskType type;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected Long duration;

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
        this.endTime = calculateEndTime();
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
        if (startTime != null) {
            endTime = startTime.plusMinutes(duration);
        } else {
            return null;
        }
        return endTime;
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

        String text = getId() + "," + getType() + "," + getName() + "," + getStatus() + "," + getDescription() + ","
                + startTimeString + "," + duration + "," + endTimeString + "\n";

        return text;
    }
}