package main.manager;

import main.Task.Task;

public class Node  {
  private   Task task;
  private   Node next;
  private   Node past;

    public Node(Task task, Node past, Node next) {
        this.task = task;
        this.next = next;
        this.past = past;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPast() {
        return past;
    }

    public void setPast(Node past) {
        this.past = past;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
