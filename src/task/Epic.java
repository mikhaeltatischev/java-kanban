package task;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subTasks;

    public Epic(String name, String description) {
        super(name, description);
        subTasks = new ArrayList<>();
    }

    public void changeStatus() {
        int newStatusCounter = 0;
        int inProgressStatusCounter = 0;
        int doneStatusCounter = 0;

        for (Subtask sub : subTasks) {
            if (sub.status == Status.NEW) {
                newStatusCounter++;
            } else if (sub.status == Status.DONE) {
                doneStatusCounter++;
            } else inProgressStatusCounter++;
        }

        if (newStatusCounter == subTasks.size()) {
            status = Status.NEW;
        } else if (doneStatusCounter == subTasks.size()) {
            status = Status.DONE;
        } else status = Status.IN_PROGRESS;
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
