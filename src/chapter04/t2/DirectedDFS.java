package chapter04.t2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 有向图的可达性,使用深度搜索
 * Created by learnless on 18.2.14.
 */
public class DirectedDFS {
    private boolean[] marked;
    private List<Integer> reach;

    /**
     * 从s可达的点
     *
     * @param G
     * @param s
     */
    public DirectedDFS(Digraph G, int s) {
        marked = new boolean[G.V()];
        reach = new ArrayList<>();
        dfs(G, s);
    }

    /**
     * 从sources所有点可达的点
     *
     * @param G
     * @param sources
     */
    public DirectedDFS(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        reach = new ArrayList<>();
        for (int s : sources) {
            if (!marked[s])
                dfs(G, s);
        }
    }

    private void dfs(Digraph G, int s) {
        marked[s] = true;
        reach.add(s);
        for (int w : G.adj(s)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    public Iterable<Integer> reach() {
        return reach;
    }

    public boolean marked(int v) {
        return marked[v];
    }

    public static void main(String[] args) {
        In in = new In("tinyDG.txt");
        Digraph G = new Digraph(in);
        DirectedDFS directedDFS = new DirectedDFS(G, 9);
        directedDFS.reach().forEach(i -> StdOut.print(i + " "));
        System.out.println("\n=====================");
        directedDFS = new DirectedDFS(G, Arrays.asList(0, 7));
        directedDFS.reach().forEach(i -> StdOut.print(i + " "));
    }
}
