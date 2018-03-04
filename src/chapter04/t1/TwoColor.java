package chapter04.t1;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 无向图是否为二分图
 * Created by learnless on 18.2.13.
 */
public class TwoColor {
    private boolean[] marked;
    private boolean[] color;    //以颜色来标志二分图，相邻节点要设置成与当前相反的颜色
    private boolean isTwoColor = true;
    private Map<Boolean, List<Integer>> map = new HashMap<>();  //分组存储二分图节点

    public TwoColor(Graph G) {
        marked = new boolean[G.V()];
        color = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!isTwoColor)    //不是二分图，直接退出
                break;
            if (!marked[v])
                dfs(G, v);
        }
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        //存储节点
        List<Integer> list = map.get(color[v]);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(v);
        map.put(color[v], list);
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                color[w] = !color[v];   //将相邻节点设置为与当前节点相反的颜色
                dfs(G, w);
            } else {    //相邻节点被标志过，与当前节点颜色相同，则不是二分图
                if (color[v] == color[w]) {
                    isTwoColor = false;
                }
            }
        }
    }

    public boolean isBipartite() {
        return isTwoColor;
    }

    /**
     * 如果为二分图，获取集合
     * @return
     */
    public List<Integer> get(boolean b) {
        if (!isBipartite()) return null;
        return map.get(b);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        TwoColor twoColor = new TwoColor(G);
        System.out.println("该图是否为二分图:"+twoColor.isBipartite());
        List<Integer> red = twoColor.get(true);
        List<Integer> black = twoColor.get(false);
        System.out.println();
    }

}
