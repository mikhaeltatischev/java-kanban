package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private ArrayList<Subtask> subtasks;

    public Epic(String name, String description) {
        super(name, description);
        subtasks = new ArrayList<>();
        type = TaskType.EPIC;
    }

    public Epic(String name, String description, int id) {
        super(name, description, id);
        subtasks = new ArrayList<>();
        type = TaskType.EPIC;
    }

    @Override
    public LocalDateTime getStartTime() {
        if (subtasks.size() == 0) {
            return null;
        }
        startTime = subtasks.get(0).getStartTime();

        for (Subtask sub : subtasks) {
            if (startTime == null || startTime.isAfter(sub.getStartTime())) {
                startTime = sub.getStartTime();
            }
        }

        return startTime;
    }

    @Override
    public Long getDuration() {
        duration = calculateDuration();

        return duration;
    }

    private long calculateDuration() {
        long time = 0;

        if (subtasks.size() == 0) {
            return 0;
        }

        for (Subtask sub : subtasks) {
            if (sub.getDuration() == null) {
                time += 0;
            } else {
                time += sub.getDuration();
            }
        }

        return time;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime calculateEndTime() {
        if (startTime != null) {
            endTime = startTime.plusMinutes(getDuration());
        } else {
            return null;
        }
        return endTime;
    }

    public void changeStatus() {
        int newStatusCounter = 0;
        int doneStatusCounter = 0;

        for (Subtask sub : subtasks) {
            if (sub.getStatus().equals(Status.NEW)) {
                newStatusCounter++;
            } else if (sub.getStatus().equals(Status.DONE)) {
                doneStatusCounter++;
            }
        }

        if (newStatusCounter == subtasks.size()) {
            changeStatus(Status.NEW);
        } else if (doneStatusCounter == subtasks.size()) {
            changeStatus(Status.DONE);
        } else {
            changeStatus(Status.IN_PROGRESS);
        }
    }

    public List<Subtask> getSubtasksForEpic() {
        return subtasks;
    }

    public void addSubTaskToList(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void clearSubTasks() {
        subtasks.clear();
    }

}