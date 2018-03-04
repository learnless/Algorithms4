package chapter04.t1;

import edu.princeton.cs.algs4.In;

/**
 * 检测图是否为无环图
 * Created by learnless on 18.2.13.
 */
public class Cycle {
    private boolean[] marked;
   private boolean hasCycle;

    public Cycle(Graph G) {
        marked = new boolean[G.V()];
        for (int s = 0; s < G.V(); s++) {
            if (hasCycle)
                break;
            if (!marked[s])
                dfs(G, s, s);
        }
    }

    /**
     * @param G
     * @param v 当前要深度搜索的点
     * @param u 上个深度搜索的点
     */
    private void dfs(Graph G, int v, int u) {
        if (hasCycle)   return;
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w, v);
            } else {    //当前节点的邻接节点被标记过，如果此时与上个节点u不想等，表示w已经被之前搜索过的，存在环
                if (w != u) {
                    hasCycle = true;
                }
            }
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        Cycle cycle = new Cycle(G);
        System.out.println("该图是否有环:"+cycle.hasCycle());
    }
}
