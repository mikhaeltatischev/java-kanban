package test;

import model.Epic;
import model.Status;
import model.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {
    Epic epic;

    @BeforeEach
    public void beforeEach() {
        epic = new Epic("Epic", "Epic");
    }

    @Test
    public void epicStatusShouldBeNew() {
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    public void epicStatusShouldBeNewWithOneNewSubtask() {
        Subtask subtask = new Subtask("Subtask", "subtask", epic);

        epic.changeStatus();

        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    public void epicShouldChangeStatusToInProgressWithOneInProgressSubtask() {
        Subtask subtask = new Subtask("Subtask", "subtask", epic);

        subtask.changeStatus(Status.IN_PROGRESS);
        epic.changeStatus();

        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void epicShouldChangeStatusToDoneWithOneDoneSubtask() {
        Subtask subtask = new Subtask("Subtask", "subtask", epic);

        subtask.changeStatus(Status.DONE);
        epic.changeStatus();

        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    public void epicShouldChangeStatusToInProgressWithOneDoneAndOneNew() {
        Subtask subtask1 = new Subtask("Subtask1", "subtask", epic);
        Subtask subtask2 = new Subtask("Subtask2", "subtask", epic);

        subtask1.changeStatus(Status.DONE);
        epic.changeStatus();

        subtask2.changeStatus(Status.NEW);
        epic.changeStatus();

        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void epicShouldClearSubtask() {
        Subtask subtask1 = new Subtask("Subtask1", "subtask", epic);
        Subtask subtask2 = new Subtask("Subtask2", "subtask", epic);

        epic.clearSubTasks();

        assertEquals(0, epic.getSubtasksForEpic().size());
    }

    @Test
    public void epicShouldContainTwoSubtasks() {
        Subtask subtask1 = new Subtask("Subtask1", "subtask", epic);
        Subtask subtask2 = new Subtask("Subtask2", "subtask", epic);

        assertEquals(2, epic.getSubtasksForEpic().size());
    }

    @Test
    public void epicShouldBeNewWithEmptyListSubtask() {
        assertEquals(Status.NEW, epic.getStatus());
    }
}