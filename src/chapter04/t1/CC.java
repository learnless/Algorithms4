package chapter04.t1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 使用无向图深度搜索找出所有的连通分量
 * Created by learnless on 18.2.12.
 */
public class CC {
    private int count;  //连通分量数
    private boolean[] marked;   //标识节点是否访问过
    private int[] id;   //分量标识符

    public CC(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                marked[v] = true;
                dfs(G, v);
                count ++;
            }
        }
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;  //设置分量标识符
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }


    /**
     * v与w是否连通,只需要判断是否分量标识符是否相等
     * @param v
     * @param w
     * @return
     */
    public boolean connected(int v, int w) {
        if (id[v] == id[w])
            return true;
        return false;
    }

    /**
     * 连通分量数
     * @return
     */
    public int count() {
        return count;
    }

    /**
     * v所在的连通分量的标识符,范围在0-count()-1
     * @param v
     * @return
     */
    public int id(int v) {
        return id[v];
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        CC cc = new CC(G);
        System.out.println(G);
        boolean connected = cc.connected(0, 5);
        StdOut.println(connected);
        StdOut.println("连通分量:" + cc.count());
    }

}
