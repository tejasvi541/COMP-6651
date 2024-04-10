import java.io.*;
import java.util.*;

class Node {
    int id;
    double dist;
    double x, y; // Position of the node
    double h; // Heuristic value

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


    void aStarMax(int s, int d, Node[] nodes) {
        double dist[] = new double[V];
        int prev[] = new int[V];
        boolean inQueue[] = new boolean[V];
        PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                return Double.compare(node2.dist - node2.h, node1.dist - node1.h); // Subtract heuristic value instead of adding it
            }
        });

        for (int i = 0; i < V; i++) {
            if (i == s) {
                dist[i] = 0;
            }else{
                dist[i] = Double.NEGATIVE_INFINITY;
            }
            prev[i] = -1;
            inQueue[i] = true;
            pq.add(new Node(i, nodes[i].x, nodes[i].y, dist[i], nodes[i].h)); // Negative heuristic value
        }
        while (!pq.isEmpty()) {
            Node u = pq.poll();
            inQueue[u.id] = false;
            if (u.dist != dist[u.id]) {
                continue;
            }

            for (Node v : adj[u.id]) {
                if (dist[v.id] < dist[u.id] + 1 && !inQueue[v.id] && prev[u.id] != v.id) { // Check if the predecessor of u is not v
                    dist[v.id] = dist[u.id] + 1;
                    prev[v.id] = u.id;
                    inQueue[v.id] = true;
                    pq.add(new Node(v.id, nodes[v.id].x, nodes[v.id].y, dist[v.id], -nodes[v.id].h)); // Negative heuristic value
                }
            }
        }

        // Print the longest path
        double maxDist = 0;
        int endNode = -1;
        for (int i = 0; i < V; i++) {
            if (dist[i] > maxDist) {
                maxDist = dist[i];
                endNode = i;
            }
        }

        System.out.println("Longest path length: " + maxDist);
        System.out.print("Path: ");
        for (int v = endNode; v != -1; v = prev[v]) {
            System.out.print(v + " ");
        }
        System.out.println();
    }
}

public class AStarHeuristic {
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

        g.aStarMax(0, maxVertex, nodes); // Assuming 0 as the source node and maxVertex as the destination node
    }
}