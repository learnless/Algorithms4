package chapter04.t2;

/**
 * 拓扑排序:无环有向图的逆后序排序
 * 图的构造为字符串有向图，使用DirectedCycle判断图是否有环，DepthFirstOrder排序类
 * Created by learnless on 18.2.14.
 */
public class Topological {
    private Iterable<Integer> order;    //存储拓扑排序

    public Topological(Digraph G) {
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
//        SymbolDigraph symbolDigraph = new SymbolDigraph("routes.txt", " ");
        SymbolDigraph symbolDigraph = new SymbolDigraph("jobs.txt", "/");
        Topological topological = new Topological(symbolDigraph.G());
        if (topological.isDAG()) {
            //0 2 3 9 5 6 8 7 4 1
            topological.order().forEach(i -> System.out.println(symbolDigraph.name(i)));
        } else {
            System.out.println("该图有环，没有拓扑排序");
        }
    }

}
