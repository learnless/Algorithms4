package chapter04.t1;

import chapter01.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 无向图寻找路径类
 * Created by learnless on 18.2.12.
 */
public class DepthFirstPaths {
    private boolean[] marked;   //这个顶点调用dfs标志为true
    private final int s;  //起点
    private int[] edgeTo;   //从起点到一个顶点已知路径的最后一个顶点

    public DepthFirstPaths(Graph G, int s) {
        this.s = s;
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    /**
     * 通向v是否连通
     * @param v
     * @return
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /**
     * 通向v的路径，返回栈
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

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        int s = Integer.parseInt(args[1]);
        DepthFirstPaths paths = new DepthFirstPaths(G, s);
        boolean hasPathTo = paths.hasPathTo(5);
        System.out.println(hasPathTo);
        //打印所有路径
        for (int i = 0; i < G.V(); i++) {
            Iterable<Integer> pathTo = paths.pathTo(i);
            StdOut.print(s + " to " + i + ": ");
            if (pathTo == null) {
                StdOut.print( "no the path");
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
