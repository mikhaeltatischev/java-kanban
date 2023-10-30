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
        if (node == null) {
            System.out.println("Такой задачи нет");
            return null;
        }

        if (size == 1) {
            head = null;
            tail = null;
            node.nextNode = null;
            node.prevNode = null;
            nodes.remove(node);
            size--;
            return node;
        }

        if (node.prevNode == null) {
            node.nextNode.prevNode = null;
            head = node.nextNode;
        } else if (node.nextNode == null) {
            node.prevNode.nextNode = null;
            tail = node.prevNode;
        } else {
            node.prevNode.nextNode = node.nextNode;
            node.nextNode.prevNode = node.prevNode;
            node.prevNode = null;
            node.nextNode = null;
        }

        nodes.remove(node.task.getId());
        size--;

        return node;
    }

    public void headToNull() {
        head = null;
    }

    public void tailToNull() {
        tail = null;
    }

    public Node getHead() {
        return head;
    }
}

