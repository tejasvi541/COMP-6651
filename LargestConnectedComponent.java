import java.io.*;
import java.util.*;

class Graph {
private int V;
private LinkedList<Integer> adj[];
public List<String> edges = new ArrayList<>();

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

int[] largestConnectedComponentProperties() throws IOException {
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
   try (PrintWriter writer = new PrintWriter(new File("Graphs/lccgraph.txt"))) {
        for (String edge : edges) {
            writer.println(edge);
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
}

    public class LargestConnectedComponent  {

        public void runner (String p_file) throws FileNotFoundException{
            File file = new File(p_file); // Specify your file name
            Scanner sc = new Scanner(file);

            int maxVertex = 0;
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(" ");
                int v;
                int w;
                if(line.length == 1) continue;
                if(line.length > 2) {
                     v = Integer.parseInt(line[0]);
                     w = Integer.parseInt(line[3]);
                }else{
                     v = Integer.parseInt(line[0]);
                     w = Integer.parseInt(line[1]);
                }
                maxVertex = Math.max(maxVertex, Math.max(v, w));
            }
            sc.close();

            Graph g = new Graph(maxVertex + 1);

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                int v;
                int w;
                if(line.length > 2) {
                     v = Integer.parseInt(line[0]);
                     w = Integer.parseInt(line[3]);
                }else{
                     v = Integer.parseInt(line[0]);
                     w = Integer.parseInt(line[1]);
                }
                g.addEdge(v, w);
            }
            scanner.close();
            int[] properties = new int[3];
            try{
                properties = g.largestConnectedComponentProperties();
            }catch(IOException e){
                System.out.println("Error: " + e);
            }
            System.out.println("File: " + p_file);
            System.out.println("Max Degree of Largest Connected Component: " + properties[0]);
            System.out.println("Average Degree of Largest Connected Component: " + properties[1]);
            System.out.println("Number of Nodes in Largest Connected Component: " + properties[2]);
            System.out.println();
        }

    public static void main(String[] args) throws FileNotFoundException {
        LargestConnectedComponent lcc = new LargestConnectedComponent();
        lcc.runner("Graphs/graph.txt");
        lcc.runner("Graphs/graph300r28.txt");
        lcc.runner("Graphs/graph400r26.txt");
        lcc.runner("Graphs/graph500r24.txt");
        lcc.runner("Graphs/DSJC500-5.txt");
        lcc.runner("Graphs/inf-euroroad.txt");
        lcc.runner("Graphs/inf-power.txt");
        lcc.runner("Graphs/graphPositional-DSJC500-5.txt");
        lcc.runner("Graphs/graphPositional-inf-euroroad.txt");
        lcc.runner("Graphs/graphPositional-inf-power.txt");
    }
}