package chapter04.t4;

import chapter01.Queue;
import chapter01.Stack;
import edu.princeton.cs.algs4.In;

/**
 * 加权有向图基于深度搜索的顶点排序
 * Created by learnless on 2018.02.23
 */
public class DepthFirstOrder {
    private boolean[] marked;
    private Queue<Integer> pre; //前序排序
    private Queue<Integer> post;    //后序排序
    private Stack<Integer> resersePost; //逆后序排序

    public DepthFirstOrder(EdgeWeightedDigraph G) {
        marked = new boolean[G.V()];
        pre = new Queue<>();
        post = new Queue<>();
        resersePost = new Stack<>();
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v])
                dfs(G, v);
        }

    }

    private void dfs(EdgeWeightedDigraph G, int v) {
        pre.enqueue(v);
        marked[v] = true;
        for (DirectedEdge edge : G.adj(v)) {
            int w = edge.to();
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        post.enqueue(v);
        resersePost.push(v);
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public Iterable<Integer> reversePost() {
        return resersePost;
    }

    public static void main(String[] args) {
        DepthFirstOrder order = new DepthFirstOrder(new EdgeWeightedDigraph(new In("tinyEWDAG.txt")));
        System.out.println("\n================前序===================");
        order.pre().forEach(i -> System.out.print(i + " "));
        System.out.println("\n================后序===================");
        order.post().forEach(i -> System.out.print(i + " "));
        System.out.println("\n================逆后序===================");
        order.reversePost().forEach(i -> System.out.print(i + " "));
    }
}
