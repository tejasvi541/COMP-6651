import java.io.*;
import java.util.*;

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

    int largestConnectedComponentSize() {
        boolean[] visited = new boolean[V];
        int maxSize = 0;
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                int size = dfs(i, visited);
                maxSize = Math.max(maxSize, size);
            }
        }
        return maxSize;
    }

    int dfs(int v, boolean[] visited) {
        visited[v] = true;
        int size = 1;
        for (int u : adj[v]) {
            if (!visited[u]) {
                size += dfs(u, visited);
            }
        }
        return size;
    }
}

public class LargestConnectedComponent {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("graph.txt"); // Specify your file name
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

        int largestComponentSize = g.largestConnectedComponentSize();
        System.out.println("Size of Largest Connected Component: " + largestComponentSize);
    }
}
