package tiptest;

import edu.princeton.cs.algs4.StdOut;

/**
 * union-find算法
 * Created by learnless on 18.2.13.
 */
public class UF1 {
    private int count;
    private int[] id;

    public UF1(int N) {
        id = new int[N];
        count = N;
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public void union(int v, int w) {
        int vRoot = find(v);
        int wRoot = find(w);
        if (vRoot == wRoot) return;
        id[wRoot] = vRoot;
        count--;
    }

    public int find(int v) {
        while (v != id[v])  v = id[v];
        return v;
    }

    public boolean connected(int v, int w) {
        return find(v) == find(w);
    }

    public int count() {
        return count;
    }

    public static void main(String[] args) {
        UF1 uf = new UF1(10);
        uf.union(0, 1);
        uf.union(1, 2);
        uf.union(0, 6);
        uf.union(0, 7);
        //0 0 0 3 4 5 0 0 8 9
        StdOut.println("分量为:" + uf.count());
        for (int i = 0; i < uf.id.length; i++) {
            StdOut.print(uf.id[i] + " ");
        }
        System.out.println("\n"+uf.connected(0, 1));
        System.out.println(uf.connected(0, 6));
        System.out.println(uf.connected(0, 7));
        System.out.println(uf.connected(0, 4));
    }

}
