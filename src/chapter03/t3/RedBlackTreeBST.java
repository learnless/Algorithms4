package chapter03.t3;

import chapter01.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

/**
 * 红黑树
 * Created by learnless on 17.11.18.
 */
public class RedBlackTreeBST<Key extends Comparable<? super Key>, Value> {
    private Node root;
    private boolean RED = true, BLACK = false;

    private class Node {
        Key key;
        Value val;
        boolean color;
        int N;  //该子树的总节点数
        Node left, right;

        public Node(Key key, Value val, int N, boolean color) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
        }
    }

    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException();
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1, RED);  //新插入节点为red

        int comp = key.compareTo(x.key);
        if (comp < 0) x.left = put(x.left, key, val);
        else if (comp > 0) x.right = put(x.right, key, val);
        else x.val = val;    //相等时，只需重置val

        //重新调整red-black平衡
        //右节点为red,且左节点不为red
        if (isRed(x.right) && !isRed(x.left)) x = rrRotation(x);
        //连续左节点为red
        if (isRed(x.left) && isRed(x.left.left)) x = llRotation(x);
        //左右节点都为red
        if (isRed(x.left) && isRed(x.right)) flipColors(x);

        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }

    public Value get(Key key) {
        if (root == null) throw new NoSuchElementException();
        if (key == null) return null;
        Node node = get(root, key);
        return node == null ? null : node.val;
    }

    private Node get(Node x, Key key) {
        if (x == null) return null;

        int comp = key.compareTo(x.key);
        if (comp < 0) return get(x.left, key);
        else if (comp > 0) return get(x.right, key);
        return x;
    }

    public void delete(Key key) {
        if (root == null) throw new NoSuchElementException();
        if (key == null || !contains(key)) return;
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node x, Key key) {
        int comp = key.compareTo(x.key);
        if (comp < 0) {
            if (!isRed(x.left) && !isRed(x.left.left)) x = moveRedLeft(x);
            x.left = delete(x.left, key);
        } else {
            if (isRed(x.left)) x = llRotation(x);
            if (comp == 0 && x.right == null) return null;
            if (!isRed(x.right) && !isRed(x.right.left)) x = moveRedLeft(x);
            if (comp == 0) {
                Node min = min(x.right);
                x.key = min.key;
                x.val = min.val;
                x.right = deleteMin(x.right);
            } else
                x.right = delete(x.right, key);
        }

        return balance(x);
    }

    public void deleteMin() {
        if (root == null) throw new NoSuchElementException();
        //如果两子节点都为black，设置root为red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMin(Node h) {
        if (h.left == null) return null;
        if (!isRed(h.left) && !isRed(h.left.left)) h = moveRedLeft(h);
        h.left = deleteMin(h.left);
        return balance(h);
    }

    private Node balance(Node h) {
        if (isRed(h.right)) h = rrRotation(h);
        if (isRed(h.left) && isRed(h.left.left)) h = llRotation(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
        h.N = size(h.left) + size(h.right) + 1;

        return h;
    }

    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = llRotation(h.right);
            h = rrRotation(h);
            flipColors(h);
        }
        return h;
    }

    public void deleteMax() {

    }

    public Node llRotation(Node x) {
        Node left = x.left;
        x.left = left.right;
        left.right = x;

        left.color = x.color;
        x.color = RED;  //为何要设置为red,参考2-3树
        left.N = x.N;
        x.N = 1 + size(x.left) + size(x.right);
        return left;
    }

    public Node rrRotation(Node x) {
        Node right = x.right;
        x.right = right.left;
        right.left = x;

        right.color = x.color;
        x.color = RED;
        right.N = x.N;
        x.N = 1 + size(x.left) + size(x.right);
        return right;
    }

    public boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    public void flipColors(Node x) {
        x.left.color = BLACK;
        x.right.color = BLACK;
        x.color = !x.color;
    }

    public Key floor(Key key) {
        return null;
    }

    public Key ceiling(Key key) {
        return null;
    }

    public Key select(int i) {
        return null;
    }

    public int rank(Key key) {
        return 0;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    public int size(Node x) {
        return x == null ? 0 : x.N;
    }

    public Key min() {
        if (root == null) throw new NoSuchElementException();
        Node node = min(root);
        return node == null ? null : node.key;
    }

    public Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public Key max() {
        if (root == null) throw new NoSuchElementException();
        Node node = max(root);
        return node == null ? null : node.key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    public Iterable<Key> keys() {
        if (root == null) throw new NoSuchElementException();
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if (root == null) throw new NoSuchElementException();
        if (lo.compareTo(hi) > 0) throw new IllegalArgumentException();
        Queue<Key> queue = new Queue<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) return;

        int complo = lo.compareTo(x.key);
        int comphi = hi.compareTo(x.key);

        if (complo < 0) keys(x.left, queue, lo, hi);
        if (complo <= 0 && comphi >= 0) queue.enqueue(x.key);
        if (comphi > 0) keys(x.right, queue, lo, hi);
    }

    public static void main(String[] args) {
        RedBlackTreeBST<String, Integer> st = new RedBlackTreeBST<>();
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
//        st.deleteMin();
//        st.delete("s");
        Integer s = st.get("s");

        Iterable<String> keys = st.keys();
        for (String key : keys) {
            StdOut.print(key + " ");
        }
        StdOut.println("============red:=================");
        for (String key : keys) {
            if (st.get(st.root, key).color) {
                StdOut.print(key + " ");
            }
        }
    }

}
