import java.io.*;
import java.util.*;

public class DFSLongestSimplePath {
    private static final int MAX_ATTEMPTS = 1000;

    private static class Node {
        int id;
        double x, y;
        int depth;
        Node parent;
        boolean visited;
        int color; // 0: WHITE, 1: GRAY, 2: BLACK
        int degree;

        Node(int id, double x, double y) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.depth = 0;
            this.parent = null;
            this.visited = false;
            this.color = 0; // Initially WHITE
            this.degree = 0;
        }
    }

    private static class Edge {
        Node u, v;

        Edge(Node u, Node v) {
            this.u = u;
            this.v = v;
        }

        boolean contains(Node node) {
            return u == node || v == node;
        }

        Node getOther(Node node) {
            return node == u ? v : u;
        }
    }

    private static List<Node> readNodesFromFile(String fileName) {
        List<Node> nodes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine(); // Read the first line (n = ...)
            if (line != null) {
                int n = Integer.parseInt(line.split(" = ")[1]);

                for (int i = 0; i < n; i++) {
                    nodes.add(new Node(i, 0, 0)); // Initial coordinates are not needed
                }
            } else {
                System.err.println("Error: Invalid file format. Expected 'n = <number of nodes>' on the first line.");
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return nodes;
    }

    private static List<Edge> readEdgesFromFile(String fileName, List<Node> nodes) {
        List<Edge> edges = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine(); // Skip the first line (n = ...)
            String edgeLine;
            while ((edgeLine = reader.readLine()) != null) {
                String[] nodeIds = edgeLine.split(" ");
                int u = Integer.parseInt(nodeIds[0]);
                int v = Integer.parseInt(nodeIds[1]);
                edges.add(new Edge(nodes.get(u), nodes.get(v)));
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return edges;
    }

    private static void dfs(Node startNode, List<Edge> edges, int time) {
        startNode.visited = true;
        startNode.color = 1; // GRAY
        time++;
        startNode.depth = time;

        for (Edge edge : edges) {
            if (edge.contains(startNode)) {
                Node neighbor = edge.getOther(startNode);
                if (neighbor.color == 0) { // WHITE
                    neighbor.parent = startNode;
                    dfs(neighbor, edges, time);
                }
            }
        }

        startNode.color = 2; // BLACK
    }

    private static int dfsLongestSimplePath(List<Node> lccNodes, List<Edge> edges) {
        int maxLength = 0;

        if (!lccNodes.isEmpty()) {
            for (int i = 0; i < MAX_ATTEMPTS; i++) {
                Node startNode = lccNodes.get(new Random().nextInt(lccNodes.size()));
                resetNodes(lccNodes);
                dfs(startNode, edges, 0);

                Node maxDepthNode = findMaxDepthNode(lccNodes);
                resetNodes(lccNodes);
                dfs(maxDepthNode, edges, 0);

                maxLength = Math.max(maxLength, findMaxDepthNode(lccNodes).depth);
            }
        } else {
            System.err.println("Error: LCC node list is empty.");
        }

        return maxLength;
    }

    private static void resetNodes(List<Node> nodes) {
        for (Node node : nodes) {
            node.depth = 0;
            node.parent = null;
            node.visited = false;
            node.color = 0; // WHITE
        }
    }

    private static Node findMaxDepthNode(List<Node> nodes) {
        Node maxDepthNode = null;
        int maxDepth = 0;

        for (Node node : nodes) {
            if (node.depth > maxDepth) {
                maxDepth = node.depth;
                maxDepthNode = node;
            }
        }

        return maxDepthNode;
    }

    private static int calculateEdgeCount(List<Node> lccNodes, List<Edge> edges) {
        Set<Node> visitedNodes = new HashSet<>();
        int edgeCount = 0;
        for (Edge edge : edges) {
            if (lccNodes.contains(edge.u) && lccNodes.contains(edge.v)) {
                edgeCount++;
            }
        }
        return edgeCount;
    }

    private static double calculateAverageDegree(List<Node> lccNodes, List<Edge> edges) {
        int edgeCount = calculateEdgeCount(lccNodes, edges);
        return (double) (2 * edgeCount) / lccNodes.size();
    }

    private static int calculateDeltaLCC(List<Node> lccNodes, List<Edge> edges) {
        int maxDegree = 0;
        for (Node node : lccNodes) {
            int degree = 0;
            for (Edge edge : edges) {
                if (edge.u == node || edge.v == node) {
                    degree++;
                }
            }
            maxDegree = Math.max(maxDegree, degree);
        }
        return maxDegree;
    }


    private static int calculateMaxLength(List<Node> lccNodes, List<Edge> edges) {
        return dfsLongestSimplePath(lccNodes, edges);
    }

    public static void main(String[] args) {
        runDFSLongestSimplePath("Graphs/graph.txt");
    }

    private static void runDFSLongestSimplePath(String fileName) {
        List<Node> nodes = readNodesFromFile(fileName);
        List<Edge> edges = readEdgesFromFile(fileName, nodes);

        int n = nodes.size();
        List<Node> lccNodes = new ArrayList<>(nodes);

        int maxLength = calculateMaxLength(lccNodes, edges);
        int edgeCount = calculateEdgeCount(lccNodes, edges);
        double averageDegree = calculateAverageDegree(lccNodes, edges);
        int deltaLCC = calculateDeltaLCC(lccNodes, edges);

        System.out.println("Graph with n = " + n + ", |VLCC| = " + n + ", Δ(LCC) = " + deltaLCC +
                ", k(LCC) = " + averageDegree + ", |E(LCC)| = " + edgeCount + ", L_max = " + maxLength);
    }
}
