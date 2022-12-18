package task;

import java.util.ArrayList;

public class Epic extends Task {
    public ArrayList<Sub> subTasks;

    public Epic(String name, String description) {
        super(name, description);
        subTasks = new ArrayList<>();
    }
}
