import java.util.*;
import java.io.*;

class Node {
    int d;
    double h;
    String color;
    Node parent;
    double x, y;

    Node() {
        this.color = "WHITE";
        this.parent = null;
        this.d = Integer.MIN_VALUE;
    }
}

class Graph {
    private int V;
    private LinkedList<Integer> adj[];
    public Node nodes[];

    Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        nodes = new Node[v];
        for (int i=0; i<v; ++i) {
            adj[i] = new LinkedList();
            nodes[i] = new Node();
        }
    }

    void addEdge(int v,int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    void initializeSingleSourceMax(int s) {
        nodes[s].d = 0;
    }

    void relaxMax(int u, int v) {
        if (nodes[v].d < nodes[u].d + 1) {
            nodes[v].d = nodes[u].d + 1;
            nodes[v].parent = nodes[u];
        }
    }

    void AStar(int s, int d) {
        initializeSingleSourceMax(s);
        for (Node node : nodes) {
            node.h = Math.sqrt(Math.pow(d - node.x, 2) + Math.pow(d - node.y, 2));
        }
        PriorityQueue<Integer> Q = new PriorityQueue<>(Comparator.comparingInt(u -> -(nodes[u].d + (int)nodes[u].h)));
        List<Integer> S = new ArrayList<>();
        boolean[] visited = new boolean[V];
        for (int i = 0; i < V; i++) {
            Q.add(i);
        }
        while (!Q.isEmpty()) {
            System.out.println(Q.peek());
            int u = Q.poll();
            if (visited[u]) continue;
            visited[u] = true;
            for (int v : adj[u]) {
                int old_d = nodes[v].d;
                relaxMax(u, v);
                if (nodes[v].d > old_d) {
                    if(S.contains(v)) {
                        S.remove(S.indexOf(v));
                        Q.add(v);
                    }else{
                        Q.add(v);
                    }
                }
            }
        }
    }
}

public class AStarHeuristic {
    public static void main(String args[]) throws FileNotFoundException {
        File file = new File("graph.txt");
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
        g.AStar(0, maxVertex);
        System.out.println("Longest simple path length: " + Arrays.stream(g.nodes).mapToInt(node -> node.d).max().getAsInt());
    }
}