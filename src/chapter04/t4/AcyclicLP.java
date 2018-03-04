package chapter04.t4;

import chapter01.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 加权有向图最长路径，使用拓扑排序依次松弛节点，能处理无环负权重
 * Created by learnless on 18.2.23.
 */
public class AcyclicLP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;

    public AcyclicLP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.NEGATIVE_INFINITY;
        }

        distTo[s] = 0.0;
        Topological topological = new Topological(G);
        for (int v : topological.order()) {
            relax(G, v);
        }
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge edge : G.adj(v)) {
            int w = edge.to();
            if (distTo[v] + edge.weight() > distTo[w]) {
                distTo[w] = distTo[v] + edge.weight();
                edgeTo[w] = edge;
            }
        }
    }

    public double distTo(int v) {
        double total = 0.0;
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            total += e.weight();
        }
        return total;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<DirectedEdge> stack = new Stack<>();
        for (DirectedEdge edge = edgeTo[v]; edge != null; edge = edgeTo[edge.from()]) {
            stack.push(edge);
        }
        return stack;
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY ? true : false;
    }

    public static void main(String[] args) {
        int s = 5;
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In("tinyEWDAG.txt"));
        AcyclicLP acyclicLP = new AcyclicLP(G, s);

        for (int v = 0; v < G.V(); v++) {
            StdOut.print(String.format("%d to %d (%.2f): ", s, v, acyclicLP.distTo(v)));
            Iterable<DirectedEdge> stack = acyclicLP.pathTo(v);
            if (acyclicLP.hasPathTo(v)) {
                for (DirectedEdge edge : acyclicLP.pathTo(v)) {
                    StdOut.print(edge + "   ");
                }
            }
            StdOut.println();
        }
    }

}
