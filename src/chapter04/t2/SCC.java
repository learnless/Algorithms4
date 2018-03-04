package chapter04.t2;

import chapter01.Queue;
import edu.princeton.cs.algs4.In;

/**
 * 有向图强连通性类,Kosaraju算法
 * 使用DepthFirstOrder逆后序，由于逆后序依次查出的邻接边从后排序，故能通达的点组成强连通
 * 存在v->w同时w->v，强连通
 * Created by learnless on 18.2.14.
 */
public class SCC {
    private boolean[] marked;
    private int[] id;   //强连通的标识符
    private int count;  //强连通个数

    public SCC(Digraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        DepthFirstOrder depthFirstOrder = new DepthFirstOrder(G.reverse());

        for (int v : depthFirstOrder.reversePost()) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v)) {
            if (!marked[w])
                dfs(G, w);
        }
    }

    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }

    public int id(int v) {
        return id[v];
    }

    public int count() {
        return count;
    }

    public static void main(String[] args) {
        Digraph digraph = new Digraph(new In("tinyDG.txt"));
        SCC scc = new SCC(digraph);
        System.out.println(scc.count + " 个强连通分量");
        Queue<Integer>[] queues = new Queue[scc.count()];   //根据连通分量新建队列
        for (int i = 0; i < scc.count(); i++) {
            queues[i] = new Queue<>();
        }
        //进队列
        for (int i = 0; i < digraph.V(); i++) {
            queues[scc.id[i]].enqueue(i);
        }

        //输出
        for (int i = 0; i < scc.count(); i++) {
            for (int w : queues[i]) {
                System.out.print(w + " ");
            }
            System.out.println();
        }

    }


}
