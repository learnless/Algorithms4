package chapter04.t4;

import chapter01.Queue;
import chapter02.t4.MinPQ;

/**
 * 加权有向图最短路径延迟不版本 TODO
 * Created by learnless on 18.2.23.
 */
public class LazyDijkstra {
    private MinPQ<DirectedEdge> pq;
    private Queue<DirectedEdge> mst;
    private double[] distTo;
    private boolean[] marked;

    public LazyDijkstra(EdgeWeightedDigraph G, int s) {
        pq = new MinPQ<>();
        mst = new Queue<>();
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        for (int i = 0; i < G.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;
        relax(G, s);
        while (!pq.isEmpty()) {
            DirectedEdge edge = pq.delMin();

        }
    }

    private void relax(EdgeWeightedDigraph G, int s) {
        marked[s] = true;
        for (DirectedEdge edge : G.adj(s)) {
            pq.insert(edge);
        }
    }

}
