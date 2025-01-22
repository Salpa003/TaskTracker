package main.manager;

import main.Task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private Map<Long, Node> linkedList = new HashMap<>();

    public void add(Task task) {
        if (task == null) return;

        if (linkedList.containsKey(task.getID()))
            remove(task.getID());

        linkLast(task);
    }

    @Override
    public List<Task> getHistory() {
        List<Task> list = new ArrayList<>();
        Node node = first;
        while (node != null) {
            list.add(node.getTask());
            node = node.getNext();
        }
        return list;
    }

    @Override
    public void remove(long id) {
        Node node = linkedList.remove(id);
        if (node == null)
        return;
        removeNode(node);
    }

    private Node first;
    private Node last;

    private void linkLast(Task task) {
        Node node = new Node(task,last,null);

        if (first == null) {
            first = node;
        } else {
            last.setNext(node);
        }
        last = node;
        linkedList.put(task.getID(), node);
    }

    private void removeNode(Node node) {
        if (node == null) return;

        if (node == first) { //node.getPast() == null
            first = node.getNext();
            if (first == null) {
                last = null;
            } else {
                first.setPast(null);
            }
        } else {
           node.getPast().setNext(node.getNext());
            if (node.getNext()==null) {
                last = node.getPast();
            } else {
                node.getNext().setPast(node.getPast());
            }
        }
    }

}
