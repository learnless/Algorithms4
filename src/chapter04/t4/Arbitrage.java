package chapter04.t4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 应用bellman-ford算法求出套汇
 * Created by learnless on 18.2.24.
 */
public class Arbitrage {
    public static void main(String[] args) {
        In in = new In("rates.txt");
        int V = in.readInt();
        String[] name = new String[V];
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; v++) {
            name[v] = in.readString();
            for (int w = 0; w < V; w++) {
                double rate = in.readDouble();
                DirectedEdge e = new DirectedEdge(v, w, -Math.log(rate));
                G.addEdge(e);
            }
        }

        BellmanFordSP sp = new BellmanFordSP(G, 0);
        if (sp.hasNegativeCycle()) {
            double stake = 1000.0;
            for (DirectedEdge e : sp.negativeCycle()) {
                StdOut.printf("%10.5f %s", stake, name[e.from()]);
                stake *= Math.exp(-e.weight());
                StdOut.printf("= %10.5f %s\n", stake, name[e.to()]);
            }
        } else {
            StdOut.println("no cycle");
        }

    }
}
