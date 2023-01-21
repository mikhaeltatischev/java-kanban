package service.impl;

import model.CustomLinkedList;
import model.Node;
import model.Task;
import service.HistoryManager;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private CustomLinkedList customLinkedList = new CustomLinkedList();

    @Override
    public void add(Task task) {
        if (customLinkedList.getTasks().containsKey(task.getId())) {
            Node current = customLinkedList.getTasks().get(task.getId());
            customLinkedList.removeNode(current);
        }

        customLinkedList.linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (!customLinkedList.getTasks().containsKey(id)) {
            return;
        }

        customLinkedList.removeNode(customLinkedList.getTasks().get(id));
        customLinkedList.getTasks().remove(id);
    }

    @Override
    public List<Task> getHistory() {
        List<Task> tasks = new ArrayList<>();
        Node head = customLinkedList.getHead();

        while (head != null) {
            tasks.add(head.task);
            head = head.nextNode;
        }

        return tasks;
    }

    @Override
    public void removeAllTasks() {
        customLinkedList.getTasks().clear();
        customLinkedList.headToNull();
        customLinkedList.tailToNull();
    }
}
