package chapter03.t2;

import chapter01.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

/**
 * 基于二叉查找数的符号表
 * Created by learnless on 17.11.13.
 */
public class BST<Key extends Comparable<Key>, Value> {
    private Node root;

    class Node {
        private Key key;    //键
        private Value val;  //值
        private Node left, right;   //左右节点
        private int N;  //子树（包括自己）总数

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException();
        if (val == null) {   //val为null，默认为删除行为
            delete(key);
            return;
        }

        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) //当前节点为空，则在该位置新建元素并且插入
            return new Node(key, val, 1);

        int comp = key.compareTo(x.key);
        if (comp < 0)    //=比当前节点小，递归左子树
            x.left = put(x.left, key, val);
        else if (comp > 0)   //比当前节点大，递归右子树
            x.right = put(x.right, key, val);
        else    //相等时，更新值即可
            x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException();
        if (root == null) throw new NoSuchElementException();
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null)
            return null;
        int comp = key.compareTo(x.key);
        if (comp < 0)
            return get(x.left, key);
        else if (comp > 0)
            return get(x.right, key);
        else
            return x.val;
    }

    public void delete(Key key) {
        if (root == null) return;
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null)
            return null;
        //与当前节点key比较
        int comp = key.compareTo(x.key);
        if (comp < 0) {
            x.left = delete(x.left, key);
        } else if (comp > 0) {
            x.right = delete(x.right, key);
        } else {
            //找到要删除的节点,可以获取该节点右子树最小的节点
            //1.右子树为空
            if (x.right == null)
                return x.left;
            //2.左子树为空
            if (x.left == null)
                return x.right;
            //3.获取右子树最小的节点,更新改节点的链接
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);   //技巧，由于该节点的右子树必须去掉最小节点，以更新右子树的状态
            x.left = t.left;
        }
        //4.更新计数器
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }


    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException();
        Value value = get(key);
        return value != null;
    }

    public int size() {
        return size(root);
    }

    /**
     * 指定节点子树个数
     *
     * @param x
     * @return
     */
    public int size(Node x) {
        if (x == null) return 0;
        return x.N;
    }

    public Key min() {
        if (root == null)
            return null;
        return min(root).key;
    }

    private Node min(Node x) {
        //查找直到判断其左子树为空
        if (x.left == null)
            return x;
        return min(x.left);
    }

    public Key max() {
        if (root == null)
            return null;
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null)
            return x;
        return max(x.right);
    }

    /**
     * 向下取整
     * @param key
     * @return
     */
    public Key floor(Key key) {
        if(root == null)    throw new NoSuchElementException();
        if(key == null) return null;
        Node node = floor(root, key);
        return node == null? null : node.key;
    }

    private Node floor(Node x, Key key) {
        if(x == null)
            return null;

        int comp = key.compareTo(x.key);
        if(comp == 0)   //相等时，当前的节点为向上取整的节点
            return x;
        else if(comp < 0)
            return floor(x.left, key);
        //key大于当前节点，递归查找右子树,由返回的节点，判断是否有右子树，没有直接返回该节点，有则返回查找的结果
        Node t = floor(x.right, key);
        if(t != null)
            return t;
        return x;
    }

    /**
     * 向下取整
     * @param key
     * @return
     */
    public Key ceiling(Key key) {
        if (root == null) throw new NoSuchElementException();
        if (key == null) return null;
        Node node = ceiling(root, key);
        return node == null ? null : node.key;
    }

    private Node ceiling(Node x, Key key) {
        if(x == null)
            return null;

        int comp = key.compareTo(x.key);
        if(comp == 0)
            return x;
        else if(comp > 0)
            return ceiling(x.right, key);
        Node t = ceiling(x.left, key);
        if(t != null)
            return t;
        return x;
    }

    public Key select(int i) {
        if(i < 0 || i >= size(root))   throw new IndexOutOfBoundsException();
        if (root == null) throw new NoSuchElementException();
        return select(root, i).key;
    }

    private Node select(Node x, int i) {
        int j = size(x.left);
        if (j > i)   //节点的左子树个数大于当前排名
            return select(x.left, i);
        else if (j < i)
            return select(x.right, i - j - 1);
        else
            return x;
    }

    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException();
        if (root == null) throw new NoSuchElementException();
        return rank(root, key);
    }

    private int rank(Node x, Key key) {
        int comp = key.compareTo(x.key);
        if (comp > 0) {  //比当前节点键大，则递归右子树
            return size(x.left) + rank(x.right, key) + 1;
        } else if (comp < 0) {
            return rank(x.left, key);
        } else {
            return size(x.left);
        }
    }

    public void deleteMin() {
        if (root == null) return;
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            //找到最小节点，其左节点必为空
            return x.right;
        }

        x.left = deleteMin(x.left); //更新左链接
        x.N = size(x.left) + size(x.right) + 1;    //更新节点计数器
        return x;
    }

    public void deleteMax() {
        if (root == null) return;
        deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) {
            return x.left;
        }

        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Iterable<Key> keys() {
        if (root == null) throw new NoSuchElementException();
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo.compareTo(hi) > 0) return null;
        Queue<Key> queue = new Queue<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if(x == null)   return;
        int complo = lo.compareTo(x.key);
        int comphi = hi.compareTo(x.key);
        //1. lo < x.key,查询左子树
        if (complo < 0)
            keys(x.left, queue, lo, hi);
        //2. lo <= x.key <= hi
        if (complo <= 0 && comphi >= 0)     //符合条件，进队列
            queue.enqueue(x.key);
        //3. hi > x.key
        if (comphi > 0)    //查询右子树
            keys(x.right, queue, lo, hi);

    }


    public static void main(String[] args) {
        BST<String, Integer> st = new BST<>();
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

        int y = st.get("y");
        int c = st.get("m");

//        st.deleteMin();
//        st.deleteMax();

//        st.delete("o");
        boolean o = st.contains("c");
        String select = st.select(5);
        int o1 = st.rank("o");
        int s = st.rank("s");
        String j = st.floor("j");
        String d = st.floor("d");
        String n = st.floor("n");
        String p = st.floor("p");
        String d1 = st.ceiling("d");
        String j1 = st.ceiling("j");
        String o2 = st.ceiling("o");
        String t = st.ceiling("t");

        Iterable<String> keys = st.keys();
        for (String key : keys) {
            StdOut.println(key + " " + st.get(key));
        }

        StdOut.println();
    }


}
