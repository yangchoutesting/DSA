import java.util.ArrayList;
import java.util.List;

public class Tree {
    private class Node {
        private int value;
        private Node leftChild;
        private Node rightChild;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node [value=" + value + "]";
        }

    }

    private Node root;

    public void insert(int value) {
        var node = new Node(value);
        if (root == null) {
            root = node;
            return;
        }

        var current = root;
        while (true) {
            if (value > current.value) {
                if (current.rightChild == null) {
                    current.rightChild = node;
                    break;
                }
                current = current.rightChild;
            } else {
                if (current.leftChild == null) {
                    current.leftChild = node;
                    break;
                }
                current = current.leftChild;
            }
        }
    }

    public boolean find(int value) {
        var current = root;

        while (current != null) {
            if (value < current.value) {
                current = current.leftChild;
            } else if (value > current.value) {
                current = current.rightChild;
            } else {
                return true;
            }
        }
        return false;
    }

    public void travasalPreOrder() {
        travasalPreOrder(root);
    }

    private void travasalPreOrder(Node root) {
        if (root == null) {
            return;
        }
        System.out.println(root.value);
        travasalPreOrder(root.leftChild);
        travasalPreOrder(root.rightChild);
    }

    public void travasalInOrder() {
        travasalInOrder(root);
    }

    private void travasalInOrder(Node root) {
        if (root == null) {
            return;
        }
        travasalInOrder(root.leftChild);
        System.out.println(root.value);
        travasalInOrder(root.rightChild);
    }

    public void travasalPostOrder() {
        travasalPostOrder(root);
    }

    private void travasalPostOrder(Node root) {
        if (root == null) {
            return;
        }

        travasalPostOrder(root.leftChild);
        travasalPostOrder(root.rightChild);
        System.out.println(root.value);
    }

    public int height() {
        return height(root);
    }

    private int height(Node root) {
        if (root == null) {
            return -1;
        }
        if (isLeaf(root)) {
            return 0;
        }
        return 1 + Math.max(height(root.leftChild), height(root.rightChild));
    }

    private boolean isLeaf(Node root) {
        return root.leftChild == null && root.rightChild == null;
    }

    public int min() {
        return min(root);
    }

    private int min(Node root) {
        if (isLeaf(root)) {
            return root.value;
        }
        var left = min(root.leftChild);
        var right = min(root.rightChild);

        return Math.min(Math.min(left, right), root.value);
    }

    public boolean equal(Tree other) {
        if (other == null) {
            return false;
        }
        return equal(root, other.root);
    }

    public boolean equal(Node first, Node second) {
        if (first == null && second == null) {
            return true;
        }
        if (first != null && second != null) {
            return first.value == second.value
                    && equal(first.leftChild, second.leftChild)
                    && equal(first.rightChild, second.rightChild);
        }
        return false;
    }

    public boolean isBinarySearchTree() {
        return isBinarySearchTree(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBinarySearchTree(Node root, int min, int max) {
        if (root == null) {
            return true;
        }

        if (root.value < min || root.value > max) {
            return false;
        }

        return isBinarySearchTree(root.leftChild, min, root.value - 1)
                && isBinarySearchTree(root.rightChild, root.value + 1, max);
    }

    public List<Integer> gerNodeFromDistance(int distance) {
        var list = new ArrayList<Integer>();
        gerNodeFromDistance(root, distance, list);
        return list;
    }

    private void gerNodeFromDistance(Node root, int distance, List<Integer> list) {
        if (root == null) {
            return;
        }
        if (distance == 0) {
            list.add(root.value);
            return;
        }

        gerNodeFromDistance(root.leftChild, distance - 1, list);
        gerNodeFromDistance(root.rightChild, distance - 1, list);

    }

    public void traverseLevelOrder() {
        for (int i = 0; i <= height(); i++) {
            var list = gerNodeFromDistance(i);
            for (int value : list) {
                System.out.println(value);
            }
        }
    }
}
