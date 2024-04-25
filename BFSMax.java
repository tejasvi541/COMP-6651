import java.io.*;
import java.util.*;

class Node {
   int id;
   int dist;

   public Node(int id, int dist) {
       this.id = id;
       this.dist = dist;
   }
}

class Graph {
   private int V;
   private LinkedList<Integer> adj[];
   public static int maxDistance=0;
   public static int maxEnd=-1;
   public int prev[]=new int[100000];

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

   void bfsMax(int s, int[] dist) {
       boolean visited[] = new boolean[V];
       Queue<Integer> queue = new LinkedList<>();

       for (int i = 0; i < V; i++) {
           dist[i] = Integer.MIN_VALUE;
           visited[i] = false;
       }

       queue.add(s);
       visited[s] = true;
       dist[s] = 0;

       while (!queue.isEmpty()) {
           int u = queue.poll();

           for (int v : adj[u]) {
               if (!visited[v]) {
                   dist[v] = dist[u] + 1;
                   visited[v] = true;
                   queue.add(v);
                   prev[v] = u;
                   if (dist[v] > maxDistance) {
                       maxDistance = dist[v];
                       maxEnd = v;
                   }
               }
           }
       }
   }

   void findLongestPath() {
       int dist[] = new int[V];

       // First BFS to find one end point of
       // longest path
       bfsMax(0, dist);

       // Second BFS to find actual longest path
       bfsMax(maxEnd, dist);

       System.out.println("Longest path length: " + maxDistance);
   }
}

public class BFSMax {

    public void runner(String p_file) throws FileNotFoundException{
        File file = new File(p_file); // Specify your file name
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
            g.addEdge(w, v);
        }
        scanner.close();

        g.findLongestPath();
        System.out.println("Graph: " + p_file);
        System.out.println("Longest simple path length: " + Graph.maxDistance);
        System.out.println();
        Graph.maxDistance=0;
        Graph.maxEnd=-1;
    }

    public static void main(String[] args) throws FileNotFoundException {
        BFSMax bfs = new BFSMax();
        bfs.runner("Graphs/graph.txt");
        bfs.runner("Graphs/graph300r28.txt");
        bfs.runner("Graphs/graph400r26.txt");
        bfs.runner("Graphs/graph500r24.txt");
        bfs.runner("Graphs/DSJC500-5.txt");
        bfs.runner("Graphs/inf-euroroad.txt");
        bfs.runner("Graphs/inf-power.txt");
    }
}