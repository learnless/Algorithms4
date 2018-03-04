package chapter04.t4;

import chapter01.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 检查加权有向图是否有环
 * 无向图用递归深度搜索携带上个节点，当前节点访问过判断是否与上个节点等同来判断是否有环
 * 而有向图使用栈来保存访问过的节点，当搜索到该节点访问过，判断栈是否存在当前节点来实现
 * Created by learnless on 2018.02.23
 */
public class DirectedCycle {
    private boolean[] marked;
    private DirectedEdge[] edgeTo;
    private boolean[] onStack;  //保存访问过的节点
    private Stack<DirectedEdge> cycle;   //有环获取环上所有节点

    public DirectedCycle(EdgeWeightedDigraph G) {
        marked = new boolean[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onStack = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v])
                dfs(G, v);
        }
        assert check();
    }

    private void dfs(EdgeWeightedDigraph G, int v) {
        onStack[v] = true;

        marked[v] = true;
        for (DirectedEdge edge : G.adj(v)) {
            int w = edge.to();
            if (hasCycle()) return;
            else if (!marked[w]) {
                edgeTo[w] = edge;
                dfs(G, w);
            } else if (onStack[w]) {    //有环
                cycle = new Stack<>();
                DirectedEdge e;
                for (e = edge; e.from() != w; e = edgeTo[e.from()]) {
                    cycle.push(e);
                }
                cycle.push(e);
                return;
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

    private boolean check() {

        // edge-weighted digraph is cyclic
        if (hasCycle()) {
            // verify cycle
            DirectedEdge first = null, last = null;
            for (DirectedEdge e : cycle()) {
                if (first == null) first = e;
                if (last != null) {
                    if (last.to() != e.from()) {
                        System.err.printf("cycle edges %s and %s not incident\n", last, e);
                        return false;
                    }
                }
                last = e;
            }

            if (last.to() != first.from()) {
                System.err.printf("cycle edges %s and %s not incident\n", last, first);
                return false;
            }
        }


        return true;
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In("tinyEWD.txt"));
        DirectedCycle directedCycle = new DirectedCycle(G);
        if (directedCycle.hasCycle()) {
            directedCycle.cycle.forEach(i -> StdOut.print(i + " "));
        }
    }


}
