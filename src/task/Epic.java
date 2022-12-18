package task;

import java.util.ArrayList;

public class Epic extends Task {
    public ArrayList<Sub> subTasks;

    public Epic(String name, String description) {
        super(name, description);
        subTasks = new ArrayList<>();
    }

    public void changeStatus() {
        int countNEW = 0;
        int countIN_PROGRESS = 0;
        int countDONE = 0;

        for (Sub sub : subTasks) {
            if (sub.status == Status.NEW) {
                countNEW++;
            } else if (sub.status == Status.DONE) {
                countDONE++;
            } else countIN_PROGRESS++;
        }

        if (countNEW == subTasks.size()) {
            status = Status.NEW;
        } else if (countDONE == subTasks.size()) {
            status = Status.DONE;
        } else status = Status.IN_PROGRESS;
    }
}
