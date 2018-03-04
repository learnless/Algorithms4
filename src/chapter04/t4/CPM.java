package chapter04.t4;

import edu.princeton.cs.algs4.In;

/**
 * 优先级限制下的并行任务调度问题的关键问题
 * 查找最长的路径
 * Created by learnless on 18.2.23.
 */
public class CPM {
    private EdgeWeightedDigraph G;
    private AcyclicLP acyclicLP;

    public CPM(In in) {
        int N = Integer.parseInt(in.readLine());
        G = new EdgeWeightedDigraph(N*2+2);
        int s = N*2;
        int t = N*2+1;
        for (int i = 0; i < N; i++) {
            String[] a = in.readLine().trim().split("\\s+");
            double weight = Double.parseDouble(a[0]);
            /**
             * 添加收尾节点指向
             */
            G.addEdge(new DirectedEdge(s, i, 0));   //起始节点指向节点
            G.addEdge(new DirectedEdge(i, i + N, weight));  //节点自身指向
            G.addEdge(new DirectedEdge(i + N, t, 0));   //节点指向结束节点
            for (int j = 1; j < a.length; j++) {
                int k = Integer.parseInt(a[j]);
                G.addEdge(new DirectedEdge(i+N, k, 0));
            }

        }

        acyclicLP = new AcyclicLP(G, s);
    }

    public EdgeWeightedDigraph G() {
        return G;
    }

    public AcyclicLP acyclicLP() {
        return acyclicLP;
    }

    public static void main(String[] args) {
        CPM cpm = new CPM(new In("jobsPC.txt"));
        System.out.println(cpm.G());
        System.out.println("===================================");
        AcyclicLP acyclicLP = cpm.acyclicLP();
        System.out.println("start times:");
        for (int i = 0; i < cpm.G().V(); i++) {
            System.out.println(String.format("%4d: %5.1f", i, acyclicLP.distTo(i)));
        }
        System.out.println("finish time:" + acyclicLP.distTo(cpm.G().V() - 1));
    }

}
