package chapter04.t2;

import chapter03.t1.ST;
import edu.princeton.cs.algs4.In;

/**
 * 符号无向图，更普遍应用
 * Key 符号名
 * Value 索引
 * Created by learnless on 18.2.13.
 */
public class SymbolDigraph {
    private ST<String, Integer> st;  //符号->索引
    private String[] keys; //索引->符号
    private Digraph G;

    public SymbolDigraph(String filename, String delim) {
        st = new ST<>();
        In in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delim);
            //为每个不同的字符关联一个索引
            for (int i = 0; i < a.length; i++) {
                if (!st.contains(a[i])) {
                    st.put(a[i], st.size());
                }
            }
        }

        //设置反向索引
        keys = new String[st.size()];
        for (String key : st.keys()) {
            keys[st.get(key)] = key;
        }

        //生成无向图,再次读取文件
        G = new Digraph(keys.length);
        in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delim);
            String k = a[0];
            int v = st.get(k);  //起始点
            for (int i = 1; i < a.length; i++) {    //添加边
                G.addEdge(v, st.get(a[i]));
            }

        }
    }

    /**
     * 由符号名获取索引
     * @param key 符号名
     * @return
     */
    public int index(String key) {
        return st.get(key);
    }

    /**
     * 由索引获取符号名
     * @param value 索引
     * @return
     */
    public String name(int value) {
        return keys[value];
    }

    /**
     * 是否包含顶点
     * @param Key 符号名
     * @return
     */
    public boolean contains(String key) {
        return st.contains(key);
    }

    /**
     * 获取无向图
     * @return
     */
    public Digraph G() {
        return G;
    }

    public static void main(String[] args) {
        String filename = "routes.txt";
        SymbolDigraph symbolDigraph = new SymbolDigraph(filename, " ");
        System.out.println(symbolDigraph.G());
        System.out.println();
        String[] keys = symbolDigraph.keys;
        for (int i = 0; i < keys.length; i++) {
            System.out.println(i + " : " + keys[i]);
        }
        /**
         * 0 : JFK
         1 : MCO
         2 : ORD
         3 : DEN
         4 : HOU
         5 : DFW
         6 : PHX
         7 : ATL
         8 : LAX
         9 : LAS
         */
        System.out.println("=====================获取符号名=====================");
        for (int i = 0; i < symbolDigraph.keys.length; i++) {
            System.out.print(symbolDigraph.name(i) + " ");
        }


//        String filename = "movies.txt";
//        SymbolDigraph symbolGraph = new SymbolDigraph(filename, "/");
//        //文件太大，只根据需要打印
//        while (StdIn.hasNextLine()) {
//            String source = StdIn.readLine();
//            for (int v : symbolGraph.G().adj(symbolGraph.index(source))) {
//                System.out.println("    " + symbolGraph.name(v));
//            }
//        }

    }

}
