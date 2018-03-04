package chapter03.t1;

import chapter01.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * 基于无序链表查找
 * Created by learnless on 17.11.12.
 */
public class SequentialSearchST<Key, Value> {

    private Node first;
    private int N;

    /**
     * 内部类Node
     */
    private class Node {
        Key key;
        Value value;
        Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public Value get(Key key) {
        if(key == null) throw new IllegalArgumentException();
        for (Node n = first; n != null; n = n.next) {
            if(n.key.equals(key))
                return n.value;
        }
        return null;
    }

    public void put(Key key, Value value) {
        if(key == null) throw new IllegalArgumentException();
        if(value == null) { //value为空，表示删除操作
            delete(key);
            return;
        }
        //查找链表是否存在key
        for (Node n = first; n != null; n = n.next) {
            if(key.equals(n.key)) {  //命中
                //存在则更新值
                n.value = value;
                return;
            }
        }
        //不存在则新建Node,链表头指向新建元素
        first = new Node(key, value, first);
        N++;
    }

    public void delete(Key key) {
        if(key == null) throw new IllegalArgumentException();
        delete(first, key);
    }

    private void delete(Node n, Key key) {
        //只有一个元素
        Node pre = n;
        for(;n != null; n = n.next) {
            if(key.equals(n.key)) { //命中
                if(N == 1) {
                    first = null;

                } else {
                    pre.next = n.next;
                }
                n = null;   //处理游离
                N--;
                return;
            }
            pre = n;
        }
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public boolean contains(Key key) {
        if(key == null) throw new IllegalArgumentException();
        return get(key) != null;
    }

    public Iterable<Key> keys() {
        Queue queue = new Queue();
        for (Node n = first; n != null; n = n.next) {
            queue.enqueue(n.key);
        }

        return queue;
    }

    public static void main(String[] args) {
        SequentialSearchST<String, Integer> st = new SequentialSearchST<>();
        st.put("b", 0);
//        st.put("a", 1);
//        st.put("m", 2);
//        st.put("o", 3);
//        st.put("y", 4);
//        st.put("i", 5);
//        st.put("o", 6);

        for (String key : st.keys()) {
            StdOut.println(key + " " + st.get(key));
        }
        System.out.println("------------------------------");
        st.put("b", null);
        for (String key : st.keys()) {
            StdOut.println(key + " " + st.get(key));
        }

    }

}
