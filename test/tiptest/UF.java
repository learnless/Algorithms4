package tiptest;

import edu.princeton.cs.algs4.StdOut;

/**
 * union-find算法
 * Created by learnless on 18.2.13.
 */
public class UF {
    private int count;
    private int[] id;

    public UF(int N) {
        id = new int[N];
        count = N;
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public void union(int v, int w) {
        int vId = find(v);
        int wId = find(w);
        if (vId == wId) return;
        for (int i = 0; i < id.length; i++) {
            if (id[i] == vId)   id[i] = wId;
        }
        count--;
    }

    public int find(int v) {
        return id[v];
    }

    public boolean connected(int v, int w) {
        return find(v) == find(w);
    }

    public int count() {
        return count;
    }

    public static void main(String[] args) {
        UF uf = new UF(10);
        uf.union(0, 1);
        uf.union(0, 6);
        uf.union(0, 7);
        StdOut.println("分量为:" + uf.count());
        for (int i = 0; i < uf.id.length; i++) {
            StdOut.print(uf.id[i] + " ");
        }
    }

}
