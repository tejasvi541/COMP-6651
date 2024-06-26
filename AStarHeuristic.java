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

    void aStarMax(int s, int d, Node[] nodes) {
        double dist[] = new double[V];
        int prev[] = new int[V];
        boolean inQueue[] = new boolean[V];
        PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                return Double.compare(node2.dist + node2.h, node1.dist + node1.h); 
            }
        });
        for (int i = 0; i < V; i++){
            if(nodes[i] == null){
                continue;
            }
            nodes[i].h = Math.sqrt(Math.pow(nodes[i].x - nodes[d].x, 2) + Math.pow(nodes[i].y - nodes[d].y, 2)); // Euclidean distance
        }
        for (int i = 0; i < V; i++) {
            if(nodes[i] == null){
                continue;
            }
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
                if (dist[v.id] < dist[u.id] + 1 && inQueue[v.id] && prev[u.id] != v.id) { // Check if the predecessor of u is not v
                    dist[v.id] = dist[u.id] + 1;
                    prev[v.id] = u.id;
                    inQueue[v.id] = true;
                    pq.add(new Node(v.id, nodes[v.id].x, nodes[v.id].y, dist[v.id], dist[v.id] + nodes[v.id].h)); // Negative heuristic value
                }
            }
        }

        maxDist = 0;
        int endNode = -1;
        for (int i = 0; i < V; i++) {
            if (dist[i] > maxDist) {
                maxDist = dist[i];
                endNode = i;
            }
        }
    }
}

public class AStarHeuristic {
    public void runner (String p_file) throws FileNotFoundException{
        File file = new File(p_file); // Specify your file name
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
        double maxDist = 0;
        scanner.close();
        System.out.println("Graph: " + p_file);
        for(int i=1; i<maxVertex; i++){
            for(int j=1; j<maxVertex; j++){
                if(i == j || nodes[i] == null || nodes[j] == null){
                    continue;
                }
                for (int k = 0; k < maxVertex + 1; k++) {
                    if(nodes[k] == null){
                        continue;
                    }
                    nodes[k] = new Node(nodes[k].id, nodes[k].x, nodes[k].y, 0, 0);
                }
                g.aStarMax(i, j, nodes); 
                maxDist = Math.max(maxDist, g.maxDist);
            }
        }
        System.out.println("Longest path length: " + maxDist);
        System.out.println();
    }
    public static void main(String[] args) throws FileNotFoundException {
        AStarHeuristic astar = new AStarHeuristic();
        astar.runner("Graphs/smallgraphPositional.txt");
        astar.runner("Graphs/graphPositional300r28.txt");
        astar.runner("Graphs/graphPositional400r26.txt");
        astar.runner("Graphs/graphPositional500r24.txt");
        astar.runner("Graphs/graphPositional-DSJC500-5.txt");
        astar.runner("Graphs/graphPositional-inf-euroroad.txt");
        // astar.runner("Graphs/graphPositional-inf-power.txt");
    }
}