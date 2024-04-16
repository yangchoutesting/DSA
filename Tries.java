import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tries {
    public static int ALPHABET_SIZE = 26;

    private class Node {
        private char value;
        private HashMap<Character, Node> childern = new HashMap<>();
        private boolean isEndOfWord;

        public Node(char value) {
            this.value = value;
        }

        public boolean hasChild(char c) {
            return childern.containsKey(c);
        }

        public void addChild(char c) {
            childern.put(c, new Node(c));
        }

        public Node getChild(char c) {
            return childern.get(c);
        }

        public Node[] getChildren() {
            return childern.values().toArray(new Node[0]);
        }

        public boolean hasChildren() {
            return !childern.isEmpty();
        }

        public void removeChild(char c) {
            childern.remove(c);
        }

        @Override
        public String toString() {
            return "Node [value=" + value + "]";
        }
    }

    private Node root = new Node(' ');

    public void insert(String word) {
        var current = root;

        for (char c : word.toCharArray()) {
            if (!current.hasChild(c)) {
                current.addChild(c);
            }
            current = current.getChild(c);
        }
        current.isEndOfWord = true;
    }

    public boolean contains(String word) {
        var current = root;

        for (char c : word.toCharArray()) {
            if (!current.hasChild(c)) {
                return false;
            }
            current = current.getChild(c);
        }
        return current.isEndOfWord;
    }

    public void traverse() {
        traverse(root);
    }

    private void traverse(Node root) {
        System.out.println(root.value);

        for (var child : root.getChildren()) {
            traverse(child);
        }
    }

    public void remove(String word) {
        if (word == null) {
            return;
        }
        remove(word, root, 0);
    }

    private void remove(String word, Node root, int index) {
        if (index == word.length()) {
            root.isEndOfWord = false;
            return;
        }

        char c = word.charAt(index);
        Node child = root.getChild(c);
        if (child == null) {
            return;
        }

        remove(word, child, index + 1);

        if (!child.hasChildren() && !child.isEndOfWord) {
            root.removeChild(c);
        }
    }

    public List<String> findWords(String prefix) {
        List<String> words = new ArrayList<>();
        Node lastNode = findLastNodeOf(prefix);
        findWords(lastNode, prefix, words);
        return words;
    }

    private void findWords(Node root, String prefix, List<String> words) {
        if (root == null) {
            return;
        }

        if (root.isEndOfWord) {
            words.add(prefix);
        }

        for (Node child : root.getChildren()) {
            findWords(child, prefix + child.value, words);
        }

    }

    private Node findLastNodeOf(String prefix) {
        if (prefix == null) {
            return null;
        }

        Node current = root;
        for (char c : prefix.toCharArray()) {
            Node child = current.getChild(c);
            if (child == null) {
                return null;
            }
            current = child;
        }
        return current;
    }
}
