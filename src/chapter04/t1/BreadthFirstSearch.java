package chapter04.t1;

import chapter01.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 无向图广度优先搜索
 * Created by learnless on 18.2.12.
 */
public class BreadthFirstSearch {
    private final int s;    //起点
    private boolean[] marked;   //标记节点是否访问过,访问过设置为true
    private int[] edgeTo;   //路径
    private int count;  //连通的个数

    public BreadthFirstSearch(Graph G, int s) {
        this.s = s;
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        bfs(G, s);
    }

    /**
     * 广度搜索，采用队列来循环图,当队列为空表示与起点连通的节点已经循环结束
     *
     * @param G
     * @param s
     */
    private void bfs(Graph G, int s) {
        Queue<Integer> queue = new Queue<>();
        marked[s] = true;
        queue.enqueue(s);
        count++;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    queue.enqueue(w);
                    count++;
                }
            }
        }
    }

    public boolean marked(int w) {
        if (w >= marked.length) throw new ArrayIndexOutOfBoundsException("查找的节点在图中不存在");
        return marked[w];
    }

    public int count() {
        return count;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph graph = new Graph(in);
        StdOut.println(graph);
        int s = Integer.parseInt(args[1]);
        BreadthFirstSearch search = new BreadthFirstSearch(graph, s);
        StdOut.println("连通个数为:"+search.count());

        for (int i = 0; i < graph.V(); i++) {
            if (search.marked(i)) {
                StdOut.print(i + " ");
            }
        }
    }
}
