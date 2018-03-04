package chapter04.t3;

import chapter01.Bag;
import edu.princeton.cs.algs4.In;

/**
 * 加权无向图api
 * Created by learnless on 18.2.16.
 */
public class EdgeWeightedGraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    private Bag<Edge>[] adj;

    public EdgeWeightedGraph(int V) {
        this.V = V;
        adj = new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public EdgeWeightedGraph(In in) {
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
                addEdge(new Edge(weight, v, w));
            }
        }


    }

    /**
     * 添加边
     * @param edge
     */
    public void addEdge(Edge edge) {
        int v = edge.eight();   //获取两个顶点任意一个
        int w = edge.other(v);  //获取另一个
        adj[v].add(edge);
        adj[w].add(edge);
        E++;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException(String.format("vertex %d is not between 0 and %d", v, V - 1));
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    /**
     * 获取所有的边
     * @return
     */
    public Iterable<Edge> edges() {
        Bag<Edge> bag = new Bag<>();
        for (int i = 0; i < V; i++) {
            for (Edge edge : adj(i)) {
                //由于无向图边的双向性，只添加低指向高的边
                if (edge.other(i) > i)  bag.add(edge);
            }
        }

        return bag;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Edge edge : adj[v]) {
                s.append(edge.other(v) + " ");
                s.append(edge.weight() + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public static void main(String[] args) {
        EdgeWeightedGraph G = new EdgeWeightedGraph(new In("tinyEWG.txt"));
        System.out.println(G);
        G.edges().forEach(System.out::println);
    }

}
