package tasks;

public class SubTask extends Task{
    private EpicTask epicTask;

    public SubTask(String name, String description, EpicTask epicTask) {
        super(name, description);
        this.epicTask = epicTask;
        this.epicTask.subTasks.add(this);
    }

    @Override
    public void setStatus(String status) {
        if (status.equals("IN_PROGRESS") || status.equals("DONE")) {
            this.status = status;
            System.out.println("Статус изменен");
        } else {
            System.out.println("Статус задачи не верный\nВыберите: IN_PROGRESS или DONE");
        }

        epicTask.changeStatus();
    }
}
