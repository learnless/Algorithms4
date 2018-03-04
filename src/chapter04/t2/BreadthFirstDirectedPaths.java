package chapter04.t2;

import chapter01.Queue;
import chapter01.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 有向图广度优先查找路径
 * Created by learnless on 18.2.14.
 */
public class BreadthFirstDirectedPaths {
    private final int s;    //起点
    private boolean[] marked;   //标记访问过的节点
    private int[] edgeTo;   //边指向 如 3->4 就记为edgeTo[4]=3

    public BreadthFirstDirectedPaths(Digraph G, int s) {
        this.s = s;
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        bfs(G, s);
    }

    private void bfs(Digraph G, int s) {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(s);
        marked[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    queue.enqueue(w);
                    marked[w] = true;
                    edgeTo[w] = v;
                }
            }
        }
    }

    /**
     * 通向v的路径
     * @param v
     * @return
     */
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v))  return null;
        Stack<Integer> stack = new Stack<>();
        while (v != s) {
            stack.push(v);
            v = edgeTo[v];
        }
        stack.push(s);
        return stack;
    }

    /**
     * 判断节点v是否与起点连通
     * @param v
     * @return
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public static void main(String[] args) {
        int s = 8;
        Digraph G = new Digraph(new In("tinyDG.txt"));
        BreadthFirstDirectedPaths paths = new BreadthFirstDirectedPaths(G, s);

        for (int i = 0; i < paths.edgeTo.length; i++) {
            StdOut.println(i+":"+paths.edgeTo[i]);
        }

        for (int i = 0; i < G.V(); i++) {
            Iterable<Integer> pathTo = paths.pathTo(i);
            StdOut.print(s + " to " + i + ": ");
            if (pathTo == null) {
                StdOut.println( "no the path");
                continue;
            }
            for (int w : pathTo) {
                if (w == s)
                    StdOut.print(s);
                else
                    StdOut.print("-"+w);
            }
            StdOut.println();
        }
    }
}
