package chapter04.t4;

import chapter01.Stack;
import chapter02.t4.IndexMinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 最短路径Dijkstra算法，即时版本
 * 只能处理无环无负权重
 * Created by learnless on 18.2.20.
 */
public class Dijkstra {
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;

    public Dijkstra(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<>(G.V());
        for (int i = 0; i < G.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }

        distTo[s] = 0.0;    //起点到自己距离为0
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            relax(G, pq.delMin());
        }

    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge edge : G.adj(v)) {
            int w = edge.to();
            double distance = distTo[v] + edge.weight();
            if (distance < distTo[w]) {    //松弛该边
                edgeTo[w] = edge;
                distTo[w] = distance;
                if (pq.contains(w)) pq.change(w, distance);
                else    pq.insert(w, distance);
            }
        }

    }

    public double distTo(int v) {
        double total = 0.0;
        for (DirectedEdge edge = edgeTo[v]; edge != null; edge = edgeTo[edge.from()]) {
            total += edge.weight();
        }

        return total;
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY ? true : false;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v))  return null;
        Stack<DirectedEdge> stack = new Stack<>();
        for (DirectedEdge edge = edgeTo[v]; edge != null; edge = edgeTo[edge.from()]) {
            stack.push(edge);
        }
        return stack;
    }

    public void print(EdgeWeightedDigraph G, int s) {
        for (int v = 0; v < G.V(); v++) {
            StdOut.print(String.format("%d to %d (%.2f): ", s, v, distTo(v)));
            Iterable<DirectedEdge> stack = pathTo(v);
            if (hasPathTo(v)) {
                for (DirectedEdge edge : pathTo(v)) {
                    StdOut.print(edge + "   ");
                }
            }
            StdOut.println();
        }
    }

    public static void main(String[] args) {
        int s = 0;
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In("tinyEWD.txt"));
        Dijkstra dijkstra = new Dijkstra(G, s);

        dijkstra.print(G, s);

//        for (int v = 0; v < G.V(); v++) {
//            StdOut.print(String.format("%d to %d (%.2f): ", s, v, dijkstra.distTo(v)));
//            Iterable<DirectedEdge> stack = dijkstra.pathTo(v);
//            if (dijkstra.hasPathTo(v)) {
//                for (DirectedEdge edge : dijkstra.pathTo(v)) {
//                    StdOut.print(edge + "   ");
//                }
//            }
//            StdOut.println();
//        }
    }


}
