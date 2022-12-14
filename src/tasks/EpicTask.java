package tasks;

import java.util.ArrayList;

public class EpicTask extends Task {
    protected ArrayList<SubTask> subTasks;

    public EpicTask(String name, String description) {
        super(name, description);
        subTasks = new ArrayList<>();
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    public void changeStatus() {
        int countNew = 0;
        int countDone = 0;

        for (int i = 0; i < subTasks.size(); i++) {
            if (subTasks.get(i).getStatus().equals("NEW")) {
                countNew += 1;
            } else if (subTasks.get(i).getStatus().equals("DONE")) {
                countDone += 1;
            }
        }

        if (countNew == subTasks.size()) {
            setStatus("NEW");
        } else if (countDone == subTasks.size()) {
            setStatus("DONE");
        } else {
            setStatus("IN_PROGRESS");
        }
    }
}
