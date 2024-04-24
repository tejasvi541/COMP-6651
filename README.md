# How to run and test our Project COMP 6651

## By

#### Tejasvi Daivik Pruthviraj

## The Project Structure is Like below

    .
    ├── ...
    ├── Graphs                         # Graph txt files
    │   ├── graph.txt                  # Graphs with only edge information
    │   .
    │   .
    │   .
    │   .
    │   └── graphPositional.txt        # Graphs Those have positional in their name have their "x" "y" info
    │
    ├── GraphGenerator.java            # Use this to generate the eucelidian graphs
    ├── LargestConnectedComponent.java # Use This to calculate LCC and other metrics
    ├── DijkstraMax.java               # Dijkstra LSP
    ├── DFSHeuristic.java              # DFS LSP
    ├── AStarHeuristic.java            # A* LSP
    ├──
    └─

## Running Each File

<p>You can run each file using the java compiler in you terminal by following commands in the project directory</p>
<code>> javac AlgorithmFileName.java</code><br/>
<code>> java AlgorithmFileName</code>

<p>These will run your desired files</p>
<p>In every Algorithm java file you can set the graph in the main function below in the file and call the runner function over it, for example below is the main function of DFS to add a graph file to run the algorithm over it, also note that this process will work in every file.<br/>
</p>

```java
public static void main(String args[]) throws FileNotFoundException {
        DFSHeuristic dfs = new DFSHeuristic();
        dfs.runner("Graphs/graph.txt");
        dfs.runner("Graphs/graph300r28.txt");
        dfs.runner("Graphs/graph400r26.txt");
        dfs.runner("Graphs/graph500r24.txt");
        dfs.runner("Graphs/inf-euroroad.txt");
        dfs.runner("Graphs/inf-power.txt");
        dfs.runner("Graphs/DSJC500-5.txt");
        dfs.runner("Add your Graph file here")       // Add your Graph file here below the file
    }
```

- Use Positional graphs for A\* and Other normal graphs for other algorithms

<p>Also you can directly use the graph generator or if you want to tune the values of <code>n</code> or <code>r</code> you can do that in the GraphGenerator file on lines 14 and 15</p>

```java
public static void main(String[] args) throws IOException {
    int n = 500; // number of vertices
    double r = 0.66; // maximum distance between nodes sharing an edge
    generateGeometricGraph(n, r);
}
```
