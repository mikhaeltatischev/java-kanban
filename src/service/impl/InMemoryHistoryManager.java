package service.impl;

import model.CustomLinkedList;
import model.Task;
import service.HistoryManager;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> viewedTasks = new ArrayList<>();
    private CustomLinkedList customLinkedList = new CustomLinkedList();

    @Override
    public void add(Task task) {
        if (viewedTasks.contains(task)) {
            viewedTasks.remove(task);
        }

        customLinkedList.linkLast(task);
        viewedTasks.add(task);
    }

    @Override
    public void remove(int id) {
        if (!customLinkedList.getTasks().containsKey(id)) {
            return;
        }

        Task taskToBeRemove = customLinkedList.getTasks().get(id).task;
        viewedTasks.remove(taskToBeRemove);
        customLinkedList.removeNode(customLinkedList.getTasks().get(id));
        customLinkedList.getTasks().remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return viewedTasks;
    }

    @Override
    public void removeAllTasks() {
        viewedTasks.clear();
        customLinkedList.getTasks().clear();
        customLinkedList.headToNull();
        customLinkedList.tailToNull();
    }
}
