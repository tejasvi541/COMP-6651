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

    int DFS(int startVertex) {
        int time = 0;
        time = DFSVisit(nodes[startVertex], time);
        for (int i = 0; i < V; i++) {
            if (nodes[i].color == 0) {
                time = DFSVisit(nodes[i], time);
            }
        }
        return time;
    }

    int DFSVisit(Node startNode, int time) {
        startNode.visited = true;
        startNode.color = 1; // GRAY
        time++;
        startNode.depth = time;

        for (Edge edge : edges) {
            if (edge.contains(startNode)) {
                Node neighbor = edge.getOther(startNode);
                if (neighbor.color == 0) { // WHITE
                    neighbor.parent = startNode;
                    time = DFSVisit(neighbor, time);
                }
            }
        }

        startNode.color = 2; // BLACK
        return time;
    }

    int DFSHeuristic() {
        int Lmax = 0;
        Random rand = new Random();
        for (int i = 0; i < Math.sqrt(V); i++) {
            int u = rand.nextInt(V);
            DFS(u);
            Node v = null;
            int maxDepth = -1;
            for (int j = 0; j < V; j++) {
                if (nodes[j].depth > maxDepth) {
                    v = nodes[j];
                    maxDepth = nodes[j].depth;
                }
            }
            DFS(v.id);
            Node w = null;
            maxDepth = -1;
            for (int j = 0; j < V; j++) {
                if (nodes[j].depth > maxDepth) {
                    w = nodes[j];
                    maxDepth = nodes[j].depth;
                }
            }
            Lmax = Math.max(Lmax, v.depth);
            Lmax = Math.max(Lmax, w.depth);
        }
        return Lmax;
    }
}

public class DFSHeuristic {
    public static void main(String args[]) throws FileNotFoundException {
        File file = new File("Graphs/graph.txt");
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
        System.out.println("Longest simple path length: " + g.DFSHeuristic());
    }
}