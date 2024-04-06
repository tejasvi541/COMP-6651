import java.io.*;
import java.util.*;

class Node implements Comparable<Node> {
    int id;
    int dist;

    public Node(int id, int dist) {
        this.id = id;
        this.dist = dist;
    }

    @Override
    public int compareTo(Node other) {
        return other.dist - this.dist;
    }
}

class Graph {
    private int V;
    private LinkedList<Integer> adj[];

    Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adj[i] = new LinkedList();
        }
    }

    void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    void dijkstraMax(int s) {
        int dist[] = new int[V];
        int prev[] = new int[V];
        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MIN_VALUE;
            prev[i] = -1;
            if (i == s) {
                dist[i] = 0;
            }
            pq.add(new Node(i, dist[i]));
        }

        while (!pq.isEmpty()) {
            Node u = pq.poll();

            for (int v : adj[u.id]) {
                if (dist[v] < dist[u.id] + 1) {
                    pq.remove(new Node(v, dist[v]));
                    dist[v] = dist[u.id] + 1;
                    prev[v] = u.id;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }

        // Print the longest path
        int maxDist = 0, endNode = -1;
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

public class DijkstraMax {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("DSJC500-5\\DSJC500-5.txt"); // Specify your file name
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

        g.dijkstraMax(0); // Assuming 0 as the source node
    }
}