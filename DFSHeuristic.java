import java.util.*;
import java.io.*;

class Node {
    int d, f;
    String color;
    Node parent;

    Node() {
        this.color = "WHITE";
        this.parent = null;
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

    int DFS(int startVertex) {
        int time = 0;
        time = DFSVisit(startVertex, time);
        for (int i = 0; i < V; i++) {
            if (nodes[i].color.equals("WHITE")) {
                time = DFSVisit(i, time);
            }
        }
        return time;
    }

    int DFSVisit(int u, int time) {
        time++;
        nodes[u].d = time;
        nodes[u].color = "GRAY";
        for (int v : adj[u]) {
            if (nodes[v].color.equals("WHITE")) {
                nodes[v].parent = nodes[u];
                time = DFSVisit(v, time);
            }
        }
        nodes[u].color = "BLACK";
        time++;
        nodes[u].f = time;
        return time;
    }

    int DFSHeuristic() {
        int Lmax = 0;
        Random rand = new Random();
        for (int i = 0; i < Math.sqrt(V); i++) {
            int u = rand.nextInt(V);
            DFS(u);
            int v = -1, maxDepth = -1;
            for (int j = 0; j < V; j++) {
                if (nodes[j].d > maxDepth) {
                    v = j;
                    maxDepth = nodes[j].d;
                }
            }
            DFS(v);
            int w = -1;
            maxDepth = -1;
            for (int j = 0; j < V; j++) {
                if (nodes[j].d > maxDepth) {
                    w = j;
                    maxDepth = nodes[j].d;
                }
            }
            Lmax = Math.max(Lmax, nodes[v].d);
            Lmax = Math.max(Lmax, nodes[w].d);
        }
        return Lmax;
    }
}

public class DFSHeuristic {
    public static void main(String args[]) throws FileNotFoundException {
        File file = new File("DSJC500-5\\DSJC500-5.txt");
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