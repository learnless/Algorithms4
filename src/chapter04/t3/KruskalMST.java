package chapter04.t3;

import chapter01.Queue;
import chapter01.t5.UF;
import chapter02.t4.MinPQ;
import edu.princeton.cs.algs4.In;

/**
 * 最小生成树kruskal算法
 * Created by learnless on 18.2.19.
 */
public class KruskalMST {
    private Queue<Edge> mst;

    public KruskalMST(EdgeWeightedGraph G) {
        mst = new Queue<>();
        MinPQ<Edge> pq = new MinPQ<>();
        UF uf = new UF(G.V());

        for (Edge edge : G.edges()) {
            pq.insert(edge);
        }

        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge edge = pq.delMin();
            int v = edge.eight();
            int w = edge.other(v);
            if (uf.connected(v, w)) continue;   //失效的边
            uf.union(v, w);
            mst.enqueue(edge);
        }

    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        double total = 0.0;
        for (Edge edge : mst) {
            total += edge.weight();
        }

        return total;
    }

    public static void main(String[] args) {
        EdgeWeightedGraph G = new EdgeWeightedGraph(new In("tinyEWG.txt"));
        KruskalMST kruskalMST = new KruskalMST(G);
        kruskalMST.edges().forEach(System.out::println);
        System.out.println("权重为:" + kruskalMST.weight());
    }

}
