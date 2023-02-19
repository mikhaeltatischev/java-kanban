package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private ArrayList<Subtask> subTasks;
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
        subTasks = new ArrayList<>();
        type = TaskType.EPIC;
    }

    public Epic(String name, String description, int id) {
        super(name, description, id);
        subTasks = new ArrayList<>();
        type = TaskType.EPIC;
    }

    @Override
    public LocalDateTime getStartTime() {
        if (subTasks.size() == 0) {
            return null;
        }
        startTime = subTasks.get(0).getStartTime();

        for (Subtask sub : subTasks) {
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

    public long calculateDuration() {
        long time = 0;

        if (subTasks.size() == 0) {
            return 0;
        }

        for (Subtask sub : subTasks) {
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
        endTime = startTime.plusMinutes(getDuration());

        return endTime;
    }

    public void changeStatus() {
        int newStatusCounter = 0;
        int doneStatusCounter = 0;

        for (Subtask sub : subTasks) {
            if (sub.getStatus().equals(Status.NEW)) {
                newStatusCounter++;
            } else if (sub.getStatus().equals(Status.DONE)) {
                doneStatusCounter++;
            }
        }

        if (newStatusCounter == subTasks.size()) {
            changeStatus(Status.NEW);
        } else if (doneStatusCounter == subTasks.size()) {
            changeStatus(Status.DONE);
        } else {
            changeStatus(Status.IN_PROGRESS);
        }
    }

    public List<Subtask> getSubTasksForEpic() {
        return subTasks;
    }

    public void addSubTaskToList(Subtask subtask) {
        subTasks.add(subtask);
    }

    public void clearSubTasks() {
        subTasks.clear();
    }
}
