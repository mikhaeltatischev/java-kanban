package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subTasks;
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
        subTasks = new ArrayList<>();
        type = TaskTypes.EPIC;
    }

    public Epic(String name, String description, int id) {
        super(name, description, id);
        subTasks = new ArrayList<>();
        type = TaskTypes.EPIC;
    }

    @Override
    public LocalDateTime getStartTime() {
        try {
            startTime = subTasks.get(0).getStartTime();

            for (Subtask sub : subTasks) {
                if (startTime.isAfter(sub.getStartTime())) {
                    startTime = sub.getStartTime();
                }
            }
        } catch (NullPointerException e) {
            e.getMessage();
        } catch (IndexOutOfBoundsException e) {
            e.getMessage();
            startTime = LocalDateTime.of(01, 01, 01, 01, 01, 01);
        }

        return startTime;
    }

    @Override
    public long getDuration() {
        duration = calculateDuration();

        return duration;
    }

    public long calculateDuration() {
        long time = 0;
        for (Subtask sub : subTasks) {
            time += sub.getDuration();
        }

        return time;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime calculateEndTime() {
        try {
            endTime = startTime.plusMinutes(getDuration());
        } catch (NullPointerException e) {
            e.getMessage();
        }

        return endTime;
    }

    public void changeStatus() {
        int newStatusCounter = 0;
        int inProgressStatusCounter = 0;
        int doneStatusCounter = 0;

        for (Subtask sub : subTasks) {
            if (sub.getStatus().equals(Status.NEW)) {
                newStatusCounter++;
            } else if (sub.getStatus().equals(Status.DONE)) {
                doneStatusCounter++;
            } else {
                inProgressStatusCounter++;
            }
        }

        if (newStatusCounter == subTasks.size()) {
            changeStatusToNew();
        } else if (doneStatusCounter == subTasks.size()) {
            changeStatusToDone();
        } else {
            changeStatusToInProgress();
        }
    }

    public ArrayList<Subtask> getSubTasksForEpic() {
        return subTasks;
    }

    public void addSubTaskToList(Subtask subtask) {
        subTasks.add(subtask);
    }

    public void clearSubTasks() {
        subTasks.clear();
    }
}
