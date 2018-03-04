package chapter04.t2;

import edu.princeton.cs.algs4.In;

/**
 * 顶点对的可达性，其实在DirectedDFS已经实现，这里使用数组存储DirectedDFS类
 * Created by learnless on 18.2.15.
 */
public class TransitiveClosure {
    private DirectedDFS[] all;

    public TransitiveClosure(Digraph G) {
        all = new DirectedDFS[G.V()];
        for (int v = 0; v < G.V(); v++) {
            all[v] = new DirectedDFS(G, v);
        }
    }

    /**
     * 是否可达
     * @param v
     * @param w
     * @return
     */
    public boolean reachable(int v, int w) {
        return all[v].marked(w);
    }

    public static void main(String[] args) {
        Digraph digraph = new Digraph(new In("tinyDG.txt"));
        TransitiveClosure transitiveClosure = new TransitiveClosure(digraph);
        System.out.println(transitiveClosure.reachable(6, 10));

    }

}
