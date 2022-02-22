package managers;
import model.Task;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    static class Node<E extends Task> {
        public E data;
        public Node<E> next;
        public Node<E> prev;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    public InMemoryHistoryManager() {
    }

    private Node<Task> head;
    private Node<Task> tail;

    HashMap<Integer, Node<Task>> history = new HashMap<>();

    public void addLast(Task element) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(tail, element, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        if (tail != null && task.equals(tail.data)) {
            return;
        }
        addLast(task);
        if (history.containsKey(task.getId())) {
            remove(task.getId());
        }
        history.put(task.getId(), tail);
    }

    @Override
    public void remove(Integer id) {
        removeNode(history.get(id));
    }

    public void removeNode(Node<Task> node) {
        final Node<Task> next = node.next;
        final Node<Task> prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.data = null;
    }

    @Override
    public List<Task> getHistory() {
        List<Task> tasks = new ArrayList<>();
        Node<Task> current = tail;
        for (int i = history.size(); current != null; i--) {
            tasks.add(current.data);
            current = current.prev;
        }
        return tasks;
    }

}
