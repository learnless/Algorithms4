package chapter04.t3;

import chapter02.t4.IndexMinPQ;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;

/**
 * 最小生成树即时实现
 * Created by learnless on 18.2.18.
 */
public class PrimeMST {
    private boolean[] marked;
    private Edge[] edgeTo;  //距离最近的边
    private double[] distTo;    //距离,distTo[w] = edgeTo[w].weight()
    private IndexMinPQ<Double> pq;

    public PrimeMST(EdgeWeightedGraph G) {
        marked = new boolean[G.V()];
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<>(G.V());

        for (int i = 0; i < G.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }

        //初始化
        distTo[1] = 0.0;
        pq.insert(1, 0.0);
        while (!pq.isEmpty()) {
            visit(G, pq.delMin());
        }
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge edge : G.adj(v)) {
            int w = edge.other(v);
            if (marked[w])  continue;   //v-w失效
            if (edge.weight() < distTo[w]) {    //更新edgeTo distTo pq
                edgeTo[w] = edge;
                distTo[w] = edge.weight();
                if (pq.contains(w))
                    pq.change(w, distTo[w]);
                else
                    pq.insert(w, distTo[w]);
            }

        }
    }

    public Edge[] edges() {
        return edgeTo;
    }

    public double weight() {
        double total = 0.0;
        for (int i = 0; i < distTo.length; i++) {
            total += distTo[i];
        }
        return total;
    }

    public static void main(String[] args) {
        EdgeWeightedGraph G = new EdgeWeightedGraph(new In("tinyEWG.txt"));
        PrimeMST primeMST = new PrimeMST(G);
        Arrays.stream(primeMST.edges()).forEach(System.out::println);
        System.out.println("权重为:" + primeMST.weight());
        /**
         * 0-7 weight is 0.16
         1-7 weight is 0.19
         0-2 weight is 0.26
         2-3 weight is 0.17
         5-7 weight is 0.28
         4-5 weight is 0.35
         6-2 weight is 0.4
         权重为:1.81
         */

    }

}
