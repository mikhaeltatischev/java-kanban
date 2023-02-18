package model;

public class Subtask extends Task {
    private Epic epicTask;

    public Subtask(String name, String description, Epic epicTask) {
        super(name, description);
        this.epicTask = epicTask;
        this.epicTask.addSubTaskToList(this);
        type = TaskTypes.SUBTASK;
    }

    public Subtask(String name, String description, Epic epicTask, int id) {
        super(name, description, id);
        this.epicTask = epicTask;
        this.epicTask.addSubTaskToList(this);
        type = TaskTypes.SUBTASK;
    }

    public Epic getEpicTask() {
        return epicTask;
    }

    @Override
    public int getEpicId() {
        return epicTask.getId();
    }

    @Override
    public void changeStatusToInProgress() {
        super.changeStatusToInProgress();
        epicTask.changeStatus();
    }

    public void changeStatusToDone() {
        super.changeStatusToDone();
        epicTask.changeStatus();
    }

    public void changeStatusToNew() {
        super.changeStatusToNew();
        epicTask.changeStatus();
    }
}
