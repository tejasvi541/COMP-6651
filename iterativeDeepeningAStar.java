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

    void iterativeDeepeningAStarMax(int s, int d, Node[] nodes) {
        for (int i = 0; i < V; i++){
            if(nodes[i] == null){
                continue;
            }
            nodes[i].h = Math.sqrt(Math.pow(nodes[i].x - nodes[d].x, 2) + Math.pow(nodes[i].y - nodes[d].y, 2)); // Euclidean distance
        }
        double threshold = nodes[s].h;
        while (true) {
            double temp = search(nodes[s], 0, threshold, nodes, d, new HashSet<>());
            if (temp == -1) return;
            if (temp == Double.POSITIVE_INFINITY) return;
            threshold = temp;
        }
    }

    double search(Node node, double g, double threshold, Node[] nodes, int d, Set<Integer> visited) {
        double f = g + node.h;
        if (f > threshold) return f;
        if (node.id == d) return -1;
        double max = Double.NEGATIVE_INFINITY; // Change here: initialize to negative infinity
        visited.add(node.id);
        for (Node nextNode : adj[node.id]) {
            if (nextNode.id == node.id || visited.contains(nextNode.id)) continue;
            double temp = search(nextNode, g + 1, threshold, nodes, d, new HashSet<>(visited));
            if (temp == -1) return -1;
            if (temp > max) max = temp; // Change here: update max if temp is greater
        }
        return maxDist = max; // Change here: return max instead of min
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

        g.iterativeDeepeningAStarMax(2, maxVertex, nodes); // Assuming 0 as the source node and maxVertex as the destination node
        System.out.println(g.maxDist);
    }
}
