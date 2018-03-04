package chapter04.t4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 使用Dijkstra算法计算任意两点的距离
 * Created by learnless on 18.2.20.
 */
public class DijkstraAllPairsSP {
    private Dijkstra[] all;

    public DijkstraAllPairsSP(EdgeWeightedDigraph G) {
        all = new Dijkstra[G.V()];
        for (int v = 0; v < G.V(); v++) {
            all[v] = new Dijkstra(G, v);
        }
    }

    /**
     * 两点之间的路径
     * @param s
     * @param v
     * @return
     */
    public Iterable<DirectedEdge> path(int s, int v) {
        return all[s].pathTo(v);
    }

    /**
     * 两点之间的路径
     * @param s
     * @param v
     * @return
     */
    public double dist(int s, int v) {
        return all[s].distTo(v);
    }

    public Dijkstra dijkstra(int v) {
        return all[v];
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In("tinyEWD.txt"));
        DijkstraAllPairsSP sp = new DijkstraAllPairsSP(G);
        for (int i = 0; i < G.V(); i++) {
            StdOut.println("============start point is " + i);
            sp.dijkstra(i).print(G, i);
        }
    }

}
