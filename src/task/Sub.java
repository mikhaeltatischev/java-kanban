package task;

public class Sub extends Task{
    private Epic epicTask;

    public Sub(String name, String description, Epic epicTask) {
        super(name, description);
        this.epicTask = epicTask;
        this.epicTask.subTasks.add(this);
    }
}
