package chapter04.t4;

import chapter01.Queue;
import chapter01.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * bellman-ford最短路径算法，加权有向图可负可环，但不可以有负权重环
 * Created by learnless on 18.2.23.
 */
public class BellmanFordSP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private boolean[] onQ;  //当前节点是否在队列中
    private Queue<Integer> queue;
    private int cost;   //relax方法调用次数
    private Iterable<DirectedEdge> cycle;   //是否有负权重环

    public BellmanFordSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onQ = new boolean[G.V()];
        queue = new Queue<>();

        for (int i = 0; i < G.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }

        distTo[s] = 0.0;
        onQ[s] = true;
        queue.enqueue(s);

        while (!queue.isEmpty() && !hasNegativeCycle()) {   //无负环且队列不为空
            int v = queue.dequeue();
            onQ[v] = false;
            relax(G, v);
        }

        assert check(G, s);
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[v] + e.weight() < distTo(w)) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                //检测是否在队列中，没有则加入到队列中
                if (!onQ[w]) {
                    queue.enqueue(w);
                    onQ[w] = true;
                }
            }

            if (cost++ % G.V() == 0) {
                findNegativeCycle();
                if (hasNegativeCycle()) {
                    return;
                }
            }
        }
    }

    /**
     * 查找是否有负权重环,重新构建一幅图
     */
    private void findNegativeCycle() {
        EdgeWeightedDigraph spg = new EdgeWeightedDigraph(edgeTo.length);
        for (int i = 0; i < edgeTo.length; i++) {
            if (edgeTo[i] != null) {
                spg.addEdge(edgeTo[i]);
            }
        }
        DirectedCycle finder = new DirectedCycle(spg);
        cycle = finder.cycle();
    }

    /**
     * 返回是否有负权重环
     *
     * @return
     */
    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    /**
     * 返回负权重环
     *
     * @return
     */
    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (hasNegativeCycle()) throw new UnsupportedOperationException("Negative cost cycle exists");
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> stack = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            stack.push(e);
        return stack;
    }

    private boolean check(EdgeWeightedDigraph G, int s) {

        // has a negative cycle
        if (hasNegativeCycle()) {
            double weight = 0.0;
            for (DirectedEdge e : negativeCycle()) {
                weight += e.weight();
            }
            if (weight >= 0.0) {
                System.err.println("error: weight of negative cycle = " + weight);
                return false;
            }
        }

        // no negative cycle reachable from source
        else {

            // check that distTo[v] and edgeTo[v] are consistent
            if (distTo[s] != 0.0 || edgeTo[s] != null) {
                System.err.println("distanceTo[s] and edgeTo[s] inconsistent");
                return false;
            }
            for (int v = 0; v < G.V(); v++) {
                if (v == s) continue;
                if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                    System.err.println("distTo[] and edgeTo[] inconsistent");
                    return false;
                }
            }

            // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
            for (int v = 0; v < G.V(); v++) {
                for (DirectedEdge e : G.adj(v)) {
                    int w = e.to();
                    if (distTo[v] + e.weight() < distTo[w]) {
                        System.err.println("edge " + e + " not relaxed");
                        return false;
                    }
                }
            }

            // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
            for (int w = 0; w < G.V(); w++) {
                if (edgeTo[w] == null) continue;
                DirectedEdge e = edgeTo[w];
                int v = e.from();
                if (w != e.to()) return false;
                if (distTo[v] + e.weight() != distTo[w]) {
                    System.err.println("edge " + e + " on shortest path not tight");
                    return false;
                }
            }
        }

        StdOut.println("Satisfies optimality conditions");
        StdOut.println();
        return true;
    }

    public static void main(String[] args) {
        int s = 5;
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In("tinyEWDnc.txt"));
        BellmanFordSP sp = new BellmanFordSP(G, s);

//        for (int v = 0; v < G.V(); v++) {
//            StdOut.print(String.format("%d to %d (%.2f): ", s, v, sp.distTo(v)));
//            Iterable<DirectedEdge> stack = sp.pathTo(v);
//            if (sp.hasPathTo(v)) {
//                for (DirectedEdge edge : sp.pathTo(v)) {
//                    StdOut.print(edge + "   ");
//                }
//            }
//            StdOut.println();
//        }

        if (sp.hasNegativeCycle())
            for (DirectedEdge e : sp.negativeCycle())
                StdOut.println(e);
        else
            for (int v = 0; v < G.V(); v++)
                if (sp.hasPathTo(v)) {
                    StdOut.printf("%d to %d (%5.2f)  ", s, v, sp.distTo(v));
                    for (DirectedEdge e : sp.pathTo(v)) {
                        StdOut.print(e + "   ");
                    }
                    StdOut.println();
                } else {
                    StdOut.printf("%d to %d           no path\n", s, v);
                }


    }

}
