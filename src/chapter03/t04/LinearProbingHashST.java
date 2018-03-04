package chapter03.t04;

import chapter01.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * 基于线性探测的符号表
 * Created by learnless on 17.12.12.
 */
public class LinearProbingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    private int N;  //键值对个数
    private int M = 16;  //符号表大小
    private Key[] keys;
    private Value[] vals;

    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    public LinearProbingHashST(int M) {
        N = 0;
        this.M = M;
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
    }

    public void put(Key key, Value val) {
        if (key == null)    throw new IllegalArgumentException();
        if (val == null)    delete(key);
        if (N >= M/2)   resize(2*M);
        int hash;
        for (hash = hash(key); keys[hash] != null; hash = (hash+1)%M)
            if (keys[hash].equals(key)) {
                vals[hash] = val;
                return;
            }
        keys[hash] = key;
        vals[hash] = val;
        N++;
    }

    public Value get(Key key) {
        if (key == null)    throw new IllegalArgumentException();
        for(int hash = hash(key); keys[hash] != null; hash = (hash+1)%M)
            if (keys[hash].equals(key))
                return vals[hash];
        return null;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int size) {
        LinearProbingHashST<Key, Value> t = new LinearProbingHashST<>(size);
        for (int i = 0; i < M; i++)
            if (keys[i] != null) {
                t.put(keys[i], vals[i]);
            }
        keys = t.keys;
        vals = t.vals;
        this.M = size;
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
        for (int hash = hash(key); keys[hash] != null; hash = (hash+1)%M) {
            if (keys[hash].equals(key)) {   //找到要删除的项,删除该项，将其后所有项重新插入
                keys[hash] = null;
                vals[hash] = null;
                for (int i = hash; keys[i] != null; i = (i+1)%M) {
                    // TODO: 18.2.11
                }

            }
        }
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for (int i = 0; i < M; i++)
            if (keys[i] != null)
                queue.enqueue(keys[i]);
        return queue;
    }

    public static void main(String[] args) {
        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<String, Integer>();
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

//        boolean s = st.contains("s");
//        st.delete("m");
//        st.delete("a");
//        st.delete("b");
//        st.delete("i");
//        st.delete("y");

        st.keys().forEach(key-> StdOut.print(key + " "));
    }


}
