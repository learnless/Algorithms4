package chapter04.t1;

import chapter01.Stack;

/**
 * 无向图间隔度数求解
 * 使用无向图广度搜索，方便求取其最短路径
 * Created by learnless on 18.2.13.
 */
public class DegreeOfSeparation {
    private SymbolGraph symbolGraph;

    public DegreeOfSeparation(String filename, String delim) {
        symbolGraph = new SymbolGraph(filename, delim);
    }

    /**
     * 求解s到w的度数
     *
     * @param v
     * @param w
     * @return
     */
    public int degree(String v, String w) {
        Graph G = symbolGraph.G();
        BreadthFirstPaths paths = new BreadthFirstPaths(G, symbolGraph.index(v));
        if (!paths.hasPathTo(symbolGraph.index(w))) {   //v->w不可达
            return -1;
        }
        return ((Stack) paths.pathTo(symbolGraph.index(w))).size() - 1;
    }

    public static void main(String[] args) {
        String filename = "routes.txt";
        DegreeOfSeparation degreeOfSeparation = new DegreeOfSeparation(filename, " ");
        int degree = degreeOfSeparation.degree("JFK", "MCO");
        int degree1 = degreeOfSeparation.degree("ATL", "MCO");
        int degree2 = degreeOfSeparation.degree("DFW", "PHX");
        int degree3 = degreeOfSeparation.degree("LAS", "MCO");

        System.out.println();
    }
}
