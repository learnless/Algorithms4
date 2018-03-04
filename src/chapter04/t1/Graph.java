package chapter04.t1;

import chapter01.Bag;
import chapter01.Stack;
import edu.princeton.cs.algs4.In;

import java.util.NoSuchElementException;

/**
 * 无线图的api
 * Created by learnless on 18.2.11.
 */
public class Graph {
    private static final String NEWLINE = System.getProperty("line.separator");
    /**
     * 顶点个数
     */
    private final int V;

    /**
     * 边的个数
     */
    private int E;

    /**
     * 相邻边数组
     */
    private Bag<Integer>[] adj;

    /**
     * 指定顶点的构造函数
     *
     * @param V
     */
    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertex must be nonnegative");
        this.V = V;
        adj = new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    /**
     * 由输入流构造图，格式如下:
     * 13 指定顶点个数
     * 13 指定边的个数
     * 3 2 3指向2的边
     * 4 5 4指向5的边
     * 。。。
     *
     * @param in
     */
    public Graph(In in) {
        try {

            int V = in.readInt();   //读取顶点个数
            if (V < 0) throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");
            int E = in.readInt();   //读取边个数
            if (E < 0) throw new IllegalArgumentException("number of edges in a Graph must be nonnegative");
            this.V = V;
            adj = new Bag[V];
            //构造相邻边数组
            for (int i = 0; i < V; i++) {
                adj[i] = new Bag<>();
            }
            //读取生成边
            in.readLine();
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                String[] split = line.split(" ");
                if (split.length == 2) {    //添加边
                    int v = Integer.parseInt(split[0]);
                    int w = Integer.parseInt(split[1]);
                    validateVertex(v);
                    validateVertex(w);
                    addEdge(v, w);
                }

            }

//            for (int i = 0; i < E; i++) {
//                String line = in.readLine();
//                String[] split = line.split(" ");
//                int v = Integer.parseInt(split[0]);
//                int w = Integer.parseInt(split[1]);
//
//                validateVertex(v);
//                validateVertex(w);
//                addEdge(v, w);
//            }


        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }

    /**
     * 深度复制图G
     *
     * @param graph
     */
    public Graph(Graph G) {
        this(G.V());
        this.E = G.E();
        for (int i = 0; i < G.V(); i++) {
            Stack<Integer> stack = new Stack<>();
            for (int w : G.adj[i]) {
                stack.push(w);
            }
            for (int w : stack) {
                adj[i].add(w);
            }
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    /**
     * 添加边，要考虑无线图边的双向性
     *
     * @param v 起点
     * @param w 终点
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        Bag<Integer> adjv = adj[v];
        adjv.add(w);
        Bag<Integer> adjw = adj[w];
        adjw.add(v);
        E++;
    }

    /**
     * 删除边
     *
     * @param v
     * @param w
     */
    public void delEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        Bag<Integer> adjv = adj[v];
        if (adjv.contains(w)) {
            adjv.remove(w);
            adj[w].remove(v);   //无向图双向性，同时w也得删除v
            E--;
        }

    }

    /**
     * 相邻边所有顶点
     *
     * @param v
     * @return
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * 边的度数:相邻边的个数
     *
     * @param v
     * @return
     */
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * 校验边
     *
     * @param v
     */
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
        In in = new In(args[0]);
        Graph graph = new Graph(in);
        System.out.println(graph);

        Graph graph1 = new Graph(graph);
        System.out.println(graph1);

//        graph.delEdge(1, 0);
//        graph.delEdge(9, 11);
//        System.out.println(graph);

    }
}
