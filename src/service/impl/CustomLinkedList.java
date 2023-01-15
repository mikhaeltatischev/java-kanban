package service.impl;

import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomLinkedList<T> {
    public Node<T> head;
    public Node<T> tail;
    Map<Integer, Node<T>> mapOfNodes = new HashMap<>();
    private int size = 0;

    public void linkLast(Node<T> task) {
        tail.nextNode = task;
        task.prevNode = tail;
        tail = task;
        size++;
    }

    public List<T> getTasks() {
        List<T> listOfTasks = new ArrayList<>();

        while (head.task != null) {
            listOfTasks.add(head.task);
            head.task = (T) head.nextNode;
        }

        return listOfTasks;
    }

    public Node<T> removeNode(Node<T> node) {
        while (head.task != null) {
            if (head.task == node.task) {
                Node<T> currentNode = head;
                head.nextNode.prevNode = head.prevNode;
                head.prevNode.nextNode = head.nextNode;
                head.prevNode = null;
                head.nextNode = null;

                return currentNode;
            }
            head.task = (T) head.nextNode;
        }

        return null;
    }

    public void add(Task task) {
        Node newNode = new Node(task);

        if (mapOfNodes.containsKey(newNode)) {
            removeNode(newNode);
            mapOfNodes.remove(newNode);
            mapOfNodes.put(mapOfNodes.size(), newNode);
            tail.nextNode = newNode;
            newNode.prevNode = tail;
            tail = newNode;
        }
    }
}
