package chapter04.t2;

import chapter01.Bag;
import chapter01.Stack;
import edu.princeton.cs.algs4.In;

/**
 * 有向图api
 * Created by learnless on 18.2.14.
 */
public class Digraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    public Digraph(int V) {
        this.V = V;
        adj = new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public Digraph(Digraph G) {
        this(G.V());
        this.E = G.E();
        for (int i = 0; i < V(); i++) {
            Stack<Integer> stack = new Stack<>();
            for (int w : G.adj[i]) {
                stack.push(w);
            }
            for (int w : stack) {
                this.adj[i].add(w);
            }
        }
    }

    public Digraph(In in) {
        int V = in.readInt();   //读取顶点个数
        if (V < 0) throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");
        int E = in.readInt();   //读取边个数
        if (E < 0) throw new IllegalArgumentException("number of edges in a Graph must be nonnegative");
        this.V = V;
        adj = new Bag[V];

        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
        //读取生成边
        in.readLine();
        for (String line = in.readLine(); line != null; line = in.readLine()) {
            String[] split = line.trim().split("\\s+");
            if (split.length == 2) {    //添加边
                int v = Integer.parseInt(split[0]);
                int w = Integer.parseInt(split[1]);
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
            }

        }
    }

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * 当前有向图的反向图
     * @return
     */
    public Digraph reverse() {
        Digraph H = new Digraph(V());
        for (int v = 0; v < V(); v++) {
            for (int w : adj[v]) {
                H.addEdge(w, v);
            }
        }
        return H;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException(String.format("vertex %d is not between 0 and %d", v, V - 1));
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public static void main(String[] args) {
        String filename = "tinyDG.txt";
        In in = new In(filename);
        Digraph digraph = new Digraph(in);
        System.out.println(digraph);
        System.out.println("====================");
        Digraph digraph1 = new Digraph(digraph);
        System.out.println(digraph1);
        System.out.println("degreee=" + digraph.degree(6));
        System.out.println("====================");
        Digraph reverse = digraph.reverse();
        System.out.println(reverse);
    }

}
