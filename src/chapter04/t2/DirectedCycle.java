package chapter04.t2;

import chapter01.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 检查有向图是否有环
 * 无向图用递归深度搜索携带上个节点，当前节点访问过判断是否与上个节点等同来判断是否有环
 * 而有向图使用栈来保存访问过的节点，当搜索到该节点访问过，判断栈是否存在当前节点来实现
 * Created by learnless on 18.2.14.
 */
public class DirectedCycle {
    private boolean[] marked;
    private int[] edgeTo;
    private boolean[] onStack;  //保存访问过的节点
    private Stack<Integer> cycle;   //有环获取环上所有节点

    public DirectedCycle(Digraph G) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        onStack = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v])
                dfs(G, v);
        }
    }

    private void dfs(Digraph G, int v) {
        onStack[v] = true;

        marked[v] = true;
        for (int w : G.adj(v)) {
            if (hasCycle()) return;
            else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            } else if (onStack[w]) {    //有环
                cycle = new Stack<>();
                cycle.push(w);
                while (edgeTo[v] != v) {
                    cycle.push(v);
                    v = edgeTo[v];
                }
//                for (int x = v; x != w; x = edgeTo[x]) {
//                    cycle.push(x);
//                }
//                cycle.push(w);
//                cycle.push(v);
            }
        }

        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    public static void main(String[] args) {
        Digraph G = new Digraph(new In("tinyDG.txt"));
        DirectedCycle directedCycle = new DirectedCycle(G);
        if (directedCycle.hasCycle()) {
            directedCycle.cycle.forEach(i -> StdOut.print(i + " "));
        }
    }


}
