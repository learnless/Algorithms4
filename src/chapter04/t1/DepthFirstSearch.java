package chapter04.t1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 无向图深度搜索
 * Created by learnless on 18.2.12.
 */
public class DepthFirstSearch {
    private int count;  //连通个数
    private boolean[] marked;

    public DepthFirstSearch(Graph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    /**
     * 深度搜索
     * @param v
     * @return
     */
    private void dfs(Graph G, int v) {
        marked[v] = true;
        count++;
        for (int w : G.adj(v)) {
            if (!marked[w])
                dfs(G, w);
        }
    }

    /**
     * 与w是否连通
     * @param w
     * @return
     */
    public boolean marked(int w) {
        if (w >= marked.length) throw new ArrayIndexOutOfBoundsException("查找的节点在图中不存在");
        return marked[w];
    }

    /**
     * 与s连通顶点个数
     * @return
     */
    public int count() {
        return count;
    }

    public static void main(String[] args) {
        //args[0] 文件 args[1] 起点
        In in = new In(args[0]);
        Graph graph = new Graph(in);
        StdOut.println(graph);
        int s = Integer.parseInt(args[1]);
        DepthFirstSearch search = new DepthFirstSearch(graph, s);
        StdOut.println("连通个数为:"+search.count());

        //输出与s连通所有节点
        for (int i = 0; i < graph.V(); i++) {
            if (search.marked(i)) {
                StdOut.print(i + " ");
            }
        }
        StdOut.println();
        if (search.marked(3)) {
            StdOut.println("与3连通");
        }
        if (!search.marked(2)) {
            StdOut.println("与2不连通");
        }

    }

}
