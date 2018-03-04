package chapter04.t3;

import chapter01.Queue;
import chapter02.t4.MinPQ;
import edu.princeton.cs.algs4.In;

/**
 * 最小生成树延迟实现
 * Created by learnless on 18.2.18.
 */
public class LazyPrimeMST {
    private boolean[] marked;
    private Queue<Edge> mst;
    private MinPQ<Edge> pq;

    public LazyPrimeMST(EdgeWeightedGraph G) {
        marked = new boolean[G.V()];
        mst = new Queue<>();
        pq = new MinPQ<>();

        visit(G, 0);
        while (!pq.isEmpty()) {
            Edge edge = pq.delMin();
            int v = edge.eight();
            int w = edge.other(v);
            if (marked[v] && marked[w]) continue;
            mst.enqueue(edge);
            if (!marked[v]) visit(G, v);
            if (!marked[w]) visit(G, w);
        }
    }

    /**
     * 设置返回过的节点为true，并将其相邻节点加入优先队列
     * @param G
     * @param v
     */
    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge edge : G.adj(v)) {
            if (!marked[edge.other(v)])
                pq.insert(edge);
        }
    }

    /**
     * 获取所有的边
     * @return
     */
    public Iterable<Edge> edges() {
        return mst;
    }

    /**
     * 获取最小生成树权重
     * @return
     */
    public double weight() {
        double total = 0.0;
        for (Edge edge : mst) {
            total += edge.weight();
        }
        return total;
    }

    public static void main(String[] args) {
        EdgeWeightedGraph G = new EdgeWeightedGraph(new In("tinyEWG.txt"));
        LazyPrimeMST primeMST = new LazyPrimeMST(G);
        primeMST.edges().forEach(System.out::println);
        System.out.println("权重为:" + primeMST.weight());
    }
}
