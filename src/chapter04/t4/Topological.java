package chapter04.t4;

import edu.princeton.cs.algs4.In;

/**
 * 拓扑排序:加权无环有向图的逆后序排序
 * 图的构造为字符串有向图，使用DirectedCycle判断图是否有环，DepthFirstOrder排序类
 * Created by learnless on 2018.02.23
 */
public class Topological {
    private Iterable<Integer> order;    //存储拓扑排序

    public Topological(EdgeWeightedDigraph G) {
        DirectedCycle cycle = new DirectedCycle(G);
        if (!cycle.hasCycle()) {
            DepthFirstOrder depthFirstOrder = new DepthFirstOrder(G);
            order = depthFirstOrder.reversePost();
        }
    }

    /**
     * 获取拓扑排序
     * @return
     */
    public Iterable<Integer> order() {
        return order;
    }

    /**
     * 是否有环
     * @return
     */
    public boolean isDAG() {
        return order != null;
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In("tinyEWDAG.txt"));
        Topological topological = new Topological(G);
        if (topological.isDAG()) {
            topological.order().forEach(i -> System.out.print(i + " "));
        } else {
            System.out.println("该图有环，没有拓扑排序");
        }
    }

}
