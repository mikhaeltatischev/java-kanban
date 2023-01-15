package service.impl;

import task.Task;

public class Node<T> {
    public T task;
    public Node<T> nextNode;
    public Node<T> prevNode;

    public Node(T task) {
        this.task = task;
        nextNode = null;
        prevNode = null;
    }
}
