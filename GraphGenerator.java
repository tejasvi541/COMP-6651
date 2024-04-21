import java.io.*;
import java.util.*;

class Node {
double x, y;
Node(double x, double y) {
    this.x = x;
    this.y = y;
}
}

public class GraphGenerator {
public static void main(String[] args) throws IOException {
    int n = 10; // number of vertices
    double r = 0.5; // maximum distance between nodes sharing an edge
    generateGeometricGraph(n, r);
}

public static void generateGeometricGraph(int n, double r) throws IOException {
    Node[] nodes = new Node[n];
    Random rand = new Random();

    // assign each node in V random Cartesian coordinates (x, y)
    for (int i = 0; i < n; i++) {
        double x = rand.nextDouble();
        double y = rand.nextDouble();
        nodes[i] = new Node(x, y);
    }

    // add all undirected edges of length ≤ r to E
    List<String> edges = new ArrayList<>();
    List<String> edgesPositional = new ArrayList<>();

    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            double distance = Math.sqrt(Math.pow(nodes[i].x - nodes[j].x, 2) + Math.pow(nodes[i].y - nodes[j].y, 2));
            if (distance <= Math.pow(r, 2) && i != j) {
                edges.add(i + " " + j);
                edgesPositional.add(i + " " + nodes[i].x + " " + nodes[i].y + " " + j + " " + nodes[j].x + " " + nodes[j].y);
            }
        }
    }

    // write the graph to an EDGES format file
    try (PrintWriter writer = new PrintWriter(new File("Graphs/graph.txt"))) {
        for (String edge : edges) {
            writer.println(edge);
        }
    }

    try (PrintWriter writer = new PrintWriter(new File("Graphs/graphPositional.txt"))) {
        for (String edge : edgesPositional) {
            writer.println(edge);
        }
    }
}
}