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

   void dijkstraMax(int s) {
       int dist[] = new int[V];
    //    int prev[] = new int[V];
       boolean inQueue[] = new boolean[V];
       PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
           @Override
           public int compare(Node node1, Node node2) {
               return Integer.compare(node2.dist, node1.dist);
           }
       });

       for (int i = 0; i < V; i++) {
           if (i == s) {
               dist[i] = 0;
           }else{
               dist[i] = Integer.MIN_VALUE;
           }
           prev[i] = -1;
           inQueue[i] = true;

           pq.add(new Node(i, dist[i]));
       }

       while (!pq.isEmpty()) {
           Node u = pq.poll();
           inQueue[u.id] = false;
           if (u.dist != dist[u.id]) {
               continue;
           }

           for (int v : adj[u.id]) {
               if (dist[v] < dist[u.id] + 1 && inQueue[v]) {
                   dist[v] = dist[u.id] + 1;
                   prev[v] = u.id;
                   inQueue[v] = true;
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
         if(maxDist>maxDistance){
              maxDistance=maxDist;
              maxEnd=endNode;
         }
    //   System.out.println("Longest path length: " + maxDist);
    //   System.out.print("Path: ");
    //   for (int v = endNode; v != -1; v = prev[v]) {
    //       System.out.print(v + " ");
    //   }
    //   System.out.println();
   }
}

public class DijkstraMax {

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

       for(int i=0;i<maxVertex+1;i++){
           g.dijkstraMax(i);
       }
        System.out.println("Graph: " + p_file);
        System.out.println("Longest simple path length: " + Graph.maxDistance);
        System.out.println();
        Graph.maxDistance=0;
        Graph.maxEnd=-1;
    }

   public static void main(String[] args) throws FileNotFoundException {
        DijkstraMax dijk = new DijkstraMax();
        dijk.runner("Graphs/graph.txt");
        dijk.runner("Graphs/graph300r28.txt");
        dijk.runner("Graphs/graph400r26.txt");
        dijk.runner("Graphs/graph500r24.txt");
        dijk.runner("Graphs/DSJC500-5.txt");
        dijk.runner("Graphs/inf-euroroad.txt");
        dijk.runner("Graphs/inf-power.txt");
   }
}