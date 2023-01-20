package model;

public class Node {
    public Task task;
    public Node nextNode;
    public Node prevNode;

    public Node(Task task) {
        this.task = task;
        nextNode = null;
        prevNode = null;
    }
}
