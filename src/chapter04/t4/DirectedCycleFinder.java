package chapter04.t4;

import chapter01.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 加权有向图查找负权重环
 * Created by learnless on 18.2.23.
 */
public class DirectedCycleFinder {
    private boolean[] marked;
    private DirectedEdge[] edgeTo;
    private boolean[] onStack;
    private Stack<DirectedEdge> cycle;

    public DirectedCycleFinder(EdgeWeightedDigraph G) {
        marked = new boolean[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onStack = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(EdgeWeightedDigraph G, int v) {
        onStack[v] = true;

        marked[v] = true;
        for (DirectedEdge edge : G.adj(v)) {
            int w = edge.to();
            if (hasCycle()) {
                return;
            } else if (!marked[w]) {
                edgeTo[w] = edge;
                dfs(G, w);
            } else {
                if (onStack[w]) {
                    cycle = new Stack<>();
                    DirectedEdge e;
                    for (e = edge; e.from() != w; e = edgeTo[e.from()])
                        cycle.push(e);
                    cycle.push(e);
                    //检测环是否为负
                    double total = 0.0;
                    for (DirectedEdge e1 : cycle) {
                        total += e1.weight();
                    }
                    if (total >= 0.0) {
                        cycle = null;
                    }
                }
            }
        }

        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In("tinyEWDnc.txt"));
        DirectedCycleFinder finder = new DirectedCycleFinder(G);
        if (finder.hasCycle()) {
            finder.cycle.forEach(i -> StdOut.print(i + " "));
        }
    }

}
