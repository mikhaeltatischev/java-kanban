package model;

import java.util.HashMap;
import java.util.Map;

public class CustomLinkedList {
    private Node head;
    private Node tail;
    private int size = 0;
    private Map<Integer, Node> nodes = new HashMap<>();

    public Map<Integer, Node> getTasks() {
        return nodes;
    }

    public Node linkLast(Task task) {
        Node oldTail = tail;
        Node newTail = new Node(task);

        if (size == 0) {
            tail = newTail;
            head = tail;
        } else {
            tail.nextNode = newTail;
            newTail.prevNode = oldTail;
            tail = newTail;
        }

        nodes.put(tail.task.getId(), tail);
        size++;

        return tail;
    }

    public Node removeNode(Node node) {
        Node nodeToBeRemoved = node;

        if (nodeToBeRemoved == null) {
            System.out.println("Такой задачи нет");
            return null;
        }

        if (size == 1) {
            head = null;
            tail = null;
            nodeToBeRemoved.nextNode = null;
            nodeToBeRemoved.prevNode = null;
            nodes.remove(nodeToBeRemoved);
            size--;
            return nodeToBeRemoved;
        }

        if (nodeToBeRemoved.prevNode == null) {
            nodeToBeRemoved.nextNode.prevNode = null;
            head = nodeToBeRemoved.nextNode;
        } else if (nodeToBeRemoved.nextNode == null) {
            nodeToBeRemoved.prevNode.nextNode = null;
            tail = nodeToBeRemoved.prevNode;
        } else {
            nodeToBeRemoved.prevNode.nextNode = nodeToBeRemoved.nextNode;
            nodeToBeRemoved.nextNode.prevNode = nodeToBeRemoved.prevNode;
            nodeToBeRemoved.prevNode = null;
            nodeToBeRemoved.nextNode = null;
        }

        nodes.remove(nodeToBeRemoved);
        size--;

        return nodeToBeRemoved;
    }

    public void headToNull() {
        head = null;
    }

    public void tailToNull() {
        tail = null;
    }
}

