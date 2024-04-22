import java.util.*;
import java.io.*;

class Node {
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

class Edge {
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

class Graph {
    private int V;
    private LinkedList<Integer> adj[];
    public Node nodes[];
    private List<Edge> edges;
    private static final int MAX_ATTEMPTS = 1000;

    Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        nodes = new Node[v];
        edges = new ArrayList<>();
        for (int i=0; i<v; ++i) {
            adj[i] = new LinkedList();
            nodes[i] = new Node(i, 0, 0);
        }
    }

    void addEdge(int v,int w) {
        adj[v].add(w);
        adj[w].add(v);
        edges.add(new Edge(nodes[v], nodes[w]));
    }

    Node DFSVisit(Node startNode, int time) {
        startNode.visited = true;
        startNode.color = 1; // GRAY
        time++;
        startNode.depth = time;

        for (Edge edge : edges) {
            if (edge.contains(startNode)) {
                Node neighbor = edge.getOther(startNode);
                if (neighbor.color == 0) { // WHITE
                    neighbor.parent = startNode;
                    DFSVisit(neighbor, time);
                }
            }
        }

        startNode.color = 2; // BLACK
        return startNode;
    }

    int DFSHeuristic() {
        int maxLength = 0;

        if (nodes.length > 0) {
            for (int i = 0; i < MAX_ATTEMPTS; i++) {
                Node startNode = nodes[new Random().nextInt(nodes.length)];
                DFSVisit(startNode, 0);

                Node maxDepthNode = findMaxDepthNode();
                resetNodes();
                DFSVisit(maxDepthNode, 0);

                maxLength = Math.max(maxLength, findMaxDepthNode().depth);
            }
        } else {
            System.err.println("Error: Node list is empty.");
        }

        return maxLength;
    }

    void resetNodes() {
        for (Node node : nodes) {
            node.depth = 0;
            node.parent = null;
            node.visited = false;
            node.color = 0; // WHITE
        }
    }

    Node findMaxDepthNode() {
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
}

public class DFSHeuristic {
    public void runner(String p_file) throws FileNotFoundException {
        File file = new File(p_file);
        Scanner sc = new Scanner(file);

        int maxVertex = 0;
        while (sc.hasNextLine()) {
            String[] line = sc.nextLine().split(" ");
            int v = Integer.parseInt(line[0]);
            int w = Integer.parseInt(line[1]);
            maxVertex = Math.max(maxVertex, Math.max(v, w));
        }
        sc.close();

        Graph g = new Graph(maxVertex + 1);

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" ");
            int v = Integer.parseInt(line[0]);
            int w = Integer.parseInt(line[1]);
            g.addEdge(v, w);
        }
        scanner.close();
        System.out.println("Graph: " + p_file);
        System.out.println("Longest simple path length: " + g.DFSHeuristic());
        System.out.println();
    }
    public static void main(String args[]) throws FileNotFoundException {
        DFSHeuristic dfs = new DFSHeuristic();
        dfs.runner("Graphs/graph.txt");
        dfs.runner("Graphs/graph300r28.txt");
        dfs.runner("Graphs/graph400r26.txt");
        dfs.runner("Graphs/graph500r24.txt");
        dfs.runner("Graphs/inf-euroroad.txt");
        dfs.runner("Graphs/inf-power.txt");
        dfs.runner("Graphs/DSJC500-5.txt");
    }
}