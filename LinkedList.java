import java.util.NoSuchElementException;

public class LinkedList {

    class Node {
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    private Node first;
    private Node last;
    private int size;

    public void addLast(int item) {
        Node node = new Node(item);
        size++;

        if (isEmpty()) {
            first = node;
            last = node;
            return;
        }

        last.next = node;
        last = node;
    }

    public void addFirst(int item) {
        Node node = new Node(item);
        size++;

        if (isEmpty()) {
            first = node;
            last = node;
            return;
        }

        node.next = first;
        first = node;
    }

    public int indexOf(int item) {
        int index = 0;
        Node current = first;

        while (current != null) {
            if (current.value == item) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    public void removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        size--;
        if (first == last) {
            first = null;
            last = null;
            return;
        }

        Node second = first.next;
        first.next = null;
        first = second;
    }

    public void removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        size--;
        if (first == last) {
            first = null;
            last = null;
            return;
        }

        Node previous = getPrevious(last);
        last = previous;
        last.next = null;
    }

    public int size() {
        return size;
    }

    public int[] toArray() {
        int[] array = new int[size];

        Node current = first;
        int index = 0;

        while (current != null) {
            array[index] = current.value;
            index++;
            current = current.next;
        }

        return array;
    }

    private Node getPrevious(Node node) {
        Node current = first;
        while (current != null) {
            if (current.next == node) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void reverse() {
        if (isEmpty()) {
            return;
        }

        Node previous = first;
        Node current = first.next;

        while (current != null) {
            Node next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }

        last = first;
        last.next = null;
        first = previous;

    }

    public int getKthFromTheEnd(int k) {
        if (isEmpty()) {
            throw new IllegalStateException();
        }

        Node a = first;
        Node b = first;
        int distance = k - 1;

        for (int i = 0; i < distance; i++) {
            b = b.next;
            if (b == null) {
                throw new IllegalArgumentException();
            }
        }

        while (b != last) {
            a = a.next;
            b = b.next;
        }
        return a.value;

    }

    public boolean contains(int item) {
        return indexOf(item) != -1;
    }

    private boolean isEmpty() {
        return first == null;
    }

}
