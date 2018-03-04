package chapter03.t04;

import chapter01.Queue;
import chapter03.t1.SequentialSearchST;
import edu.princeton.cs.algs4.StdOut;

/**
 * 基于拉链的散列表
 * Created by learnless on 17.12.12.
 */
public class SeparateChainingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    private int N;  //key-value总数
    private int M;  //散列表大小
    private SequentialSearchST<Key, Value>[] st;

    public SeparateChainingHashST() {
        this(INIT_CAPACITY);
    }

    public SeparateChainingHashST(int M) {
        this.M = M;
        st = new SequentialSearchST[M];
        for (int i = 0; i < M; i++)
            st[i] = new SequentialSearchST();
    }

    public void put(Key key, Value val) {
        if (key == null)    throw new IllegalArgumentException();
        if (val == null)    delete(key);
        if (N >= 10*M)  resize(2*M);
        int hash = hash(key);
        if (st[hash].contains(key))
            ++N;
        st[hash].put(key, val);
    }

    public Value get(Key key) {
        if (key == null)    throw new IllegalArgumentException();
        return st[hash(key)].get(key);
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int size) {
        SeparateChainingHashST<Key,Value> t = new SeparateChainingHashST(size);
        for (int i = 0; i < M; i++)
            for (Key key : st[i].keys())
                t.put(key, st[i].get(key));
        M = t.M;
        N = t.N;
        st = t.st;
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        if (key == null)    return false;
        return get(key) != null;
    }

    public void delete(Key key) {
        if (key == null)    return;
        if (!contains(key)) return;
        int hash = hash(key);
        st[hash].delete(key);
        N--;
        if (M > INIT_CAPACITY && N <= 2*M)  resize(M/2);
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for (int i = 0; i < M; i++)
            st[i].keys().forEach(key -> queue.enqueue(key));
        return queue;
    }

    public static void main(String[] args) {
        SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
        st.put("b", 0);
        st.put("a", 1);
        st.put("m", 2);
        st.put("o", 3);
        st.put("y", 4);
        st.put("i", 5);
        st.put("o", 6);
        st.put("n", 7);
        st.put("r", 8);
        st.put("s", 9);

        boolean s = st.contains("s");
        st.delete("m");
        st.delete("a");
        st.delete("b");
        st.delete("i");
        st.delete("y");

        st.keys().forEach(key-> StdOut.print(key + " "));
    }



}
