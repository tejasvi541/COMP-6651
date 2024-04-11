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

  int[] largestConnectedComponentProperties() {
     boolean[] visited = new boolean[V];
     int maxDegree = 0;
     int sumDegree = 0;
     int countNodes = 0;
     for (int i = 0; i < V; i++) {
         if (!visited[i]) {
             int[] properties = dfs(i, visited);
             if (properties[2] > countNodes) {
                 maxDegree = properties[0];
                 sumDegree = properties[1];
                 countNodes = properties[2];
             }
         }
     }
     return new int[]{maxDegree, sumDegree / countNodes, countNodes};
 }

 int[] dfs(int v, boolean[] visited) {
     visited[v] = true;
     int maxDegree = adj[v].size();
     int sumDegree = maxDegree;
     int countNodes = 1;
     for (int u : adj[v]) {
         if (!visited[u]) {
             int[] properties = dfs(u, visited);
             maxDegree = Math.max(maxDegree, properties[0]);
             sumDegree += properties[1];
             countNodes += properties[2];
         }
     }
     return new int[]{maxDegree, sumDegree, countNodes};
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
     int[] properties=this.largestConnectedComponentProperties();
     for (int i = 0; i < Math.sqrt(properties[2]); i++) {
         int u = rand.nextInt(properties[2]);
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
         g.addEdge(w, v);
     }
     scanner.close();
     System.out.println("Longest simple path length: " + g.DFSHeuristic());
 }
}