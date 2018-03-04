package chapter04.t4;

import chapter01.Bag;
import edu.princeton.cs.algs4.In;

/**
 * 加权有向图
 * Created by learnless on 18.2.20.
 */
public class EdgeWeightedDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    private Bag<DirectedEdge>[] adj;    //邻接边

    public EdgeWeightedDigraph(int V) {
        this.V = V;
        adj = new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public EdgeWeightedDigraph(In in) {
        int V = Integer.parseInt(in.readLine());
        int E = Integer.parseInt(in.readLine());
        if (V < 0 || E < 0) throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");
        this.V = V;
        adj = new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }

        while (in.hasNextLine()) {
            String[] split = in.readLine().trim().split("\\s+");
            if (split.length == 3) {
                int v = Integer.parseInt(split[0]);
                int w = Integer.parseInt(split[1]);
                double weight = Double.parseDouble(split[2]);
                validateVertex(v);
                validateVertex(w);
                addEdge(new DirectedEdge(v, w, weight));
            }
        }
    }

    public void addEdge(DirectedEdge edge) {
        int from = edge.from();
        int to = edge.to();
        validateVertex(from);
        validateVertex(to);
        adj[from].add(edge);
        E++;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> bag = new Bag<>();
        for (int i = 0; i < V; i++) {
            for (DirectedEdge edge : adj[i]) {
                bag.add(edge);
            }
        }

        return bag;
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + " -> ");
            for (DirectedEdge edge : adj[v]) {
                s.append(edge.to() + " ");
                s.append(edge.weight() + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException(String.format("vertex %d is not between 0 and %d", v, V - 1));
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In("tinyEWD.txt"));
        System.out.println(G);
        G.edges().forEach(System.out::println);
    }

}
