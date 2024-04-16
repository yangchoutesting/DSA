import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Graph {
    private class Node {
        private String lable;

        public Node(String lable) {
            this.lable = lable;
        }

        @Override
        public String toString() {
            return lable;
        }
    }

    private Map<String, Node> nodes = new HashMap<>();
    private Map<Node, List<Node>> adjacencyList = new HashMap<>();

    public void addNode(String lable) {
        Node node = new Node(lable);
        nodes.putIfAbsent(lable, node);
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(String from, String to) {
        Node fromNode = nodes.get(from);
        if (fromNode == null) {
            throw new IllegalStateException();
        }
        Node toNode = nodes.get(to);
        if (toNode == null) {
            throw new IllegalStateException();
        }
        adjacencyList.get(fromNode).add(toNode);
    }

    public void removeNode(String lable) {
        Node node = nodes.get(lable);
        if (node == null) {
            return;
        }
        for (Node n : adjacencyList.keySet()) {
            adjacencyList.get(n).remove(node);
        }

        adjacencyList.remove(node);
        nodes.remove(lable);
    }

    public void removeEdges(String from, String to) {
        Node fromNode = nodes.get(from);
        Node toNode = nodes.get(to);

        if (fromNode == null || toNode == null) {
            return;
        }

        adjacencyList.get(fromNode).remove(toNode);
    }

    public void DFS(String root) {
        Node node = nodes.get(root);
        if (node == null) {
            return;
        }
        DFS(node, new HashSet<>());
    }

    private void DFS(Node root, Set<Node> isVisited) {
        System.out.println(root);
        isVisited.add(root);

        for (Node node : adjacencyList.get(root)) {
            if (!isVisited.contains(node)) {
                DFS(node, isVisited);
            }
        }
    }

    public void DFSIter(String root) {
        Node node = nodes.get(root);
        if (node == null) {
            return;
        }
        Set<Node> visited = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            if (visited.contains(current)) {
                continue;
            }
            System.out.println(current);
            visited.add(current);

            for (Node neighbour : adjacencyList.get(current)) {
                if (!visited.contains(neighbour)) {
                    stack.push(neighbour);
                }
            }
        }
    }

    public void BFSIter(String root) {
        Node node = nodes.get(root);
        if (node == null) {
            return;
        }
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new ArrayDeque<>();

        queue.add(node);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (visited.contains(current)) {
                continue;
            }

            System.out.println(current);
            visited.add(current);

            for (Node neighbour : adjacencyList.get(current)) {
                if (!visited.contains(neighbour)) {
                    queue.add(neighbour);
                }
            }
        }
    }

    public List<String> topologicalSort() {
        Set<Node> visited = new HashSet<>();
        Stack<Node> stack = new Stack<>();

        for (Node node : nodes.values()) {
            topologicalSort(node, visited, stack);
        }

        List<String> sorted = new ArrayList<>();
        while (!stack.isEmpty()) {
            sorted.add(stack.pop().lable);
        }
        return sorted;
    }

    private void topologicalSort(Node node, Set<Node> visited, Stack<Node> stack) {
        if (visited.contains(node)) {
            return;
        }

        visited.add(node);

        for (Node neighbour : adjacencyList.get(node)) {
            if (!visited.contains(neighbour)) {
                topologicalSort(neighbour, visited, stack);
            }
        }

        stack.push(node);
    }

    public boolean hasCycle() {
        Set<Node> all = new HashSet<>();
        all.addAll(nodes.values());

        Set<Node> visiting = new HashSet<>();
        Set<Node> visited = new HashSet<>();

        while (!all.isEmpty()) {
            var current = all.iterator().next();
            if (hasCycle(current, all, visiting, visited)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasCycle(Node node, Set<Node> all, Set<Node> visiting, Set<Node> visited) {
        all.remove(node);
        visiting.add(node);

        for (Node neighbour : adjacencyList.get(node)) {
            if (visited.contains(neighbour)) {
                continue;
            }
            if (visiting.contains(neighbour)) {
                return true;
            }
            if (hasCycle(neighbour, all, visiting, visited)) {
                return true;
            }
        }

        visiting.remove(node);
        visited.add(node);

        return false;
    }

    public void print() {
        for (var source : adjacencyList.keySet()) {
            var targets = adjacencyList.get(source);
            if (!targets.isEmpty()) {
                System.out.println(source + " is connected to " + targets);
            }
        }
    }
}
