import java.io.*;
import java.util.*;

class Node {
    public int id;
    public double dist;
    public double x, y; // Position of the node
    public double h; // Heuristic value

    public Node(int id, double x, double y, double dist, double h) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.h = h;
    }
}

class Graph {
    private int V;
    private LinkedList<Node> adj[];
    public double maxDist;
    Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adj[i] = new LinkedList();
        }
    }

    void addEdge(Node v, Node w) {
        adj[v.id].add(w);
        adj[w.id].add(v);
    }

    double iterativeDeepeningAStarMax(int s, Node[] nodes) {
        double threshold = nodes[s].h;
        while (true) {
            double temp = search(nodes[s], 0, threshold, nodes);
            if (temp == -1) return maxDist;
            if (temp == Double.POSITIVE_INFINITY) return maxDist;
            threshold = temp;
        }
    }

    double search(Node node, double g, double threshold, Node[] nodes) {
        double f = g + node.h;
        if (f > threshold) return f;
        double min = Double.POSITIVE_INFINITY;
        for (Node nextNode : adj[node.id]) {
            if (nextNode.id == node.id) continue; // Skip if the next node is the same as the current node
            double temp = search(nextNode, g + 1, threshold, nodes);
            if (temp == -1) {
                maxDist = Math.max(maxDist, g + 1); // Update maxDist if a leaf node is reached
                return -1;
            }
            if (temp < min) min = temp;
        }
        return min;
    }
}
public class iterativeDeepeningAStar {
        public static void main(String[] args) throws FileNotFoundException {
        File file = new File("Graphs/graphPositional.txt"); // Specify your file name
        Scanner sc = new Scanner(file);

        int maxVertex = 0;
        while (sc.hasNextLine()) {
            String[] line = sc.nextLine().split(" ");
            int v = Integer.parseInt(line[0]);
            int w = Integer.parseInt(line[3]);
            maxVertex = Math.max(maxVertex, Math.max(v, w));
        }
        sc.close();

        Graph g = new Graph(maxVertex + 1);
        Node[] nodes = new Node[maxVertex + 1];

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" ");
            int v = Integer.parseInt(line[0]);
            double vx = Double.parseDouble(line[1]);
            double vy = Double.parseDouble(line[2]);
            int w = Integer.parseInt(line[3]);
            double wx = Double.parseDouble(line[4]);
            double wy = Double.parseDouble(line[5]);
            nodes[v] = new Node(v, vx, vy, 0, 0);
            nodes[w] = new Node(w, wx, wy, 0, 0);
            g.addEdge(nodes[v], nodes[w]);
            g.addEdge(nodes[w], nodes[v]);
        }
        scanner.close();

        g.iterativeDeepeningAStarMax(2,  nodes); // Assuming 0 as the source node and maxVertex as the destination node
        System.out.println(g.maxDist);
    }
}
