package service.impl;

import service.HistoryManager;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final static int HISTORY_SIZE = 10;
    private List<Task> viewedTasks = new ArrayList<>();
    CustomLinkedList customLinkedList = new CustomLinkedList();

    @Override
    public void add(Task task) {
        if (viewedTasks.contains(task)) {
            viewedTasks.remove(task);
        }

        if (viewedTasks.size() >= HISTORY_SIZE) {
            viewedTasks.remove(0);
        }

        customLinkedList.linkLast(task);
        viewedTasks.add(task);
    }

    @Override
    public void remove(int id) {
        if (!customLinkedList.mapOfNodes.containsKey(id)) {
            return;
        }

        Task taskToBeRemove = customLinkedList.mapOfNodes.get(id).task;
        viewedTasks.remove(taskToBeRemove);
        customLinkedList.removeNode(customLinkedList.mapOfNodes.get(id));
        customLinkedList.mapOfNodes.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return viewedTasks;
    }

    @Override
    public void removeAllTasks() {
        viewedTasks.clear();
        customLinkedList.mapOfNodes.clear();
        customLinkedList.head = null;
        customLinkedList.tail = null;
    }

    private class CustomLinkedList {
        private Node head;
        private Node tail;
        private int size = 0;
        private Map<Integer, Node> mapOfNodes = new HashMap<>();

        public Map<Integer, Node> getTasks() {
            return mapOfNodes;
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

            mapOfNodes.put(tail.task.getId(), tail);
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
                mapOfNodes.remove(nodeToBeRemoved);
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

            mapOfNodes.remove(nodeToBeRemoved);
            size--;

            return nodeToBeRemoved;
        }
    }
}
