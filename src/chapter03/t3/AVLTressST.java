package chapter03.t3;

import chapter01.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

/**
 * 1.满足平衡二叉树
 * 2.每个节点的左右子树高度相差至多为1（与红黑树重要区别）
 * Created by learnless on 17.11.18.
 */
public class AVLTressST<Key extends Comparable<Key>, Value> {

    private Node root;
    private int N;

    public AVLTressST() {
        N = 0;
    }

    class Node {
        private Key key;
        private Value val;
        private int n;
        private int height;
        private Node left, right;

        public Node(Key key, Value val, int n, int height) {
            this.key = key;
            this.val = val;
            this.n = n;
            this.height = height;
        }
    }

    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException();
        if (val == null) {
            delete(key);
            return;
        }

        root = put(root, key, val);

    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) {
            N++;
            return new Node(key, val, 1, 0);
        }

        int comp = key.compareTo(x.key);
        if (comp < 0) {  //插入左子树
            x.left = put(x.left, key, val);
            if (height(x.left) - height(x.right) == 2)
                if (key.compareTo(x.left.key) < 0)
                    x = llRotation(x);
                else
                    x = lrRotation(x);
        } else if (comp > 0) {
            x.right = put(x.right, key, val);
            if (height(x.right) - height(x.left) == 2)
                if (key.compareTo(x.right.key) > 0)
                    x = rrRotation(x);
                else
                    x = rlRotation(x);
        } else {
            x.val = val;
            return x;
        }

        x.n = size(x.left) + size(x.right) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }

    public Value get(Key key) {
        if (root == null) throw new NoSuchElementException();
        if (key == null) throw new IllegalArgumentException();
        Node node = get(root, key);
        return node == null ? null : node.val;
    }

    private Node get(Node x, Key key) {
        if (x == null) {
            return null;
        }

        int comp = key.compareTo(x.key);
        if (comp < 0)
            return get(x.left, key);
        else if (comp > 0)
            return get(x.right, key);
        else
            return x;
    }

    public Key min() {
        if (root == null) throw new NoSuchElementException();
        Node node = min(root);
        return node == null ? null : node.key;
    }

    public Key min(Key key) {
        if (key == null) throw new IllegalArgumentException();
        Node x = get(root, key);
        return min(x).key;
    }

    public Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public Key max() {
        if (root == null) throw new NoSuchElementException();
        return max(root).key;
    }

    public Key max(Key key) {
        if (key == null) throw new IllegalArgumentException();
        Node x = get(root, key);
        return max(x).key;
    }

    public Node max(Node x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    /**
     * 最复杂操作
     *
     * @param key
     */
    public void delete(Key key) {
        if (root == null) throw new NoSuchElementException();
        if (key == null) throw new IllegalArgumentException();
        if (!contains(key)) return;
        root = delete(root, key);

    }

    public Node delete(Node x, Key key) {
        if (x == null) return null;

        int comp = key.compareTo(x.key);
        if (comp < 0) {
            x.left = delete(x.left, key);
            //删除节点后，失去平衡进行调节
            if (height(x.right) - height(x.left) == 2) {
                //已经失去平衡，判断类型
                //RR，节点的右节点的右子树高度高于其左子树
                if (height(x.right.right) >= height(x.right.left))
                    x = rrRotation(x);
                    //RL
                else
                    x = rlRotation(x);
            }
        } else if (comp > 0) {
            x.right = delete(x.right, key);
            //同上
            if (height(x.left) - height(x.right) == 2) {
                //LL
                if (height(x.left.left) >= height(x.left.right))
                    x = llRotation(x);
                    //LR
                else
                    x = lrRotation(x);
            }
        } else {    //找到节点
            //左右节点都非空
            if (x.left != null && x.right != null) {
                if (height(x.left) >= height(x.right)) {
                    //如果左子树高于右子树，应从左子树选取一个最大的节点，代替当前节点
                    // 这类似于用"tree的左子树中最大节点"做当前节点的替身；
                    // 采用这种方式的好处是：删除"tree的左子树中最大节点"之后，AVL树仍然是平衡的
                    Node max = max(x.left);
                    x.key = max.key;
                    x.val = max.val;
                    x.left = delete(x.left, max.key);
                } else {
                    //右子树高于左子树，从右子树选取一个最小节点
                    Node min = min(x.right);
                    x.key = min.key;
                    x.val = min.val;
                    x.right = delete(x.right, min.key);
                }
            } else {
                //左右节点有一个为空，指向非空的节点
                N--;
                return x.left == null ? x.right : x.left;
            }
        }

        x.n = 1 + size(x.left) + size(x.right);
        x.height = 1 + max(height(x.left), height(x.right));
        return x;
    }

    public void deleteMin() {
        if (root == null) return;
        delete(root, min());
    }

    public void deleteMax() {
        if (root == null) return;
        delete(root, max());
    }

    public Node rrRotation(Node x) {
        if (x == null) return null;

        Node right = x.right;
        x.right = right.left;   //right的左链接连接到x的右链接
        right.left = x; //right左链接指向x

        right.n = x.n;
        x.height = max(height(x.left), height(x.right)) + 1;
        x.n = size(x.left) + size(x.right) + 1;
        right.height = max(height(right.left), height(right.right)) + 1;

        return right;
    }

    public Node llRotation(Node x) {
        if (x == null) return null;

        Node left = x.left;
        x.left = left.right;
        left.right = x;

        left.n = x.n;
        x.height = max(height(x.left), height(x.right)) + 1;
        x.n = size(x.left) + size(x.right) + 1;
        left.height = max(height(left.left), height(left.right)) + 1;

        return left;
    }

    /**
     * 元素插入节点的左节点的右节点，破环avl，故先将其左旋转，再右旋转纠正
     *
     * @param x
     * @return
     */
    public Node lrRotation(Node x) {
        if (x == null) return null;

        x.left = rrRotation(x.left);
        return llRotation(x);
    }

    /**
     * 元素插入节点的右节点的左节点，旋转使其正确
     *
     * @param x
     * @return
     */
    public Node rlRotation(Node x) {
        if (x == null) return null;

        x.right = llRotation(x.right);
        return rrRotation(x);
    }

    public Key floor(Key key) {
        if (root == null) throw new NoSuchElementException();
        if (key == null) return null;
        Node x = floor(root, key);
        return x == null ? null : x.key;
    }

    public Node floor(Node x, Key key) {
        if (x == null) return null;

        int comp = key.compareTo(x.key);
        if (comp < 0) return floor(x.left, key);
        else if (comp == 0) return x;
        Node t = floor(x.right, key);
        if (t != null) return t;
        return x;
    }

    public Key ceiling(Key key) {
        if (root == null) throw new NoSuchElementException();
        if (key == null) return null;
        Node x = ceiling(root, key);
        return x == null ? null : x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;

        int comp = key.compareTo(x.key);
        if (comp == 0) return x;
        else if (comp > 0) return ceiling(x.right, key);
        Node t = ceiling(x.left, key);
        if (t != null) return t;
        return x;
    }

    public Key select(int i) {
        if (root == null) throw new NoSuchElementException();
        Node x = select(root, i);
        return x == null ? null : x.key;
    }

    public Node select(Node x, int i) {

        int j = size(x.left);
        if(j > i)   return select(x.left, i);
        else if(j < i)  return select(x.right, i - j - 1);
        else    return x;
    }

    public int rank(Key key) {
        if (root == null) throw new NoSuchElementException();
        if (key == null) throw new IllegalArgumentException();

        return rank(root, key);
    }

    public int rank(Node x, Key key) {
        if(x == null)   return -1;

        int comp = key.compareTo(x.key);
        if(comp < 0)    return rank(x.left, key);
        else if(comp > 0)   return 1 + size(x.left) + rank(x.right, key);
        else return size(x.left);
    }

    public int size() {
        return N;
    }

    public int size(Node x) {
        return x == null ? 0 : x.n;
    }

    public int max(int i, int j) {
        return i > j ? i : j;
    }

    public int height() {
        if (root == null) return -1;
        return height(root);
    }

    public int height(Node x) {
        return x == null ? -1 : x.height;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public boolean contains(Key key) {
        if (root == null) throw new NoSuchElementException();
        if (key == null) return false;
        return get(key) != null;
    }

    public Iterable<Key> keys() {
        if (root == null) throw new NoSuchElementException();
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo.compareTo(hi) > 0) return null;
        Queue<Key> queue = new Queue<>();
        keys(lo, hi, queue, root);
        return queue;
    }

    /**
     * 按层来遍历
     * @return
     */
    public Iterable<Key> keysLevelOrder() {
        Queue<Key> queue = new Queue<Key>();
        if (!isEmpty()) {
            Queue<Node> queue2 = new Queue<Node>();
            queue2.enqueue(root);
            while (!queue2.isEmpty()) {
                Node x = queue2.dequeue();
                queue.enqueue(x.key);
                if (x.left != null) {
                    queue2.enqueue(x.left);
                }
                if (x.right != null) {
                    queue2.enqueue(x.right);
                }
            }
        }
        return queue;
    }

    public void keys(Key lo, Key hi, Queue<Key> queue, Node x) {
        if (x == null) return;

        int complo = lo.compareTo(x.key);
        int comphi = hi.compareTo(x.key);
        if (complo < 0) keys(lo, hi, queue, x.left);
        if (complo <= 0 && comphi >= 0) queue.enqueue(x.key);
        if (comphi > 0) keys(lo, hi, queue, x.right);

    }

    /**
     * 前序遍历 根-左-右
     */
    public Iterable<Key> preOrder() {
        if (root == null) throw new NoSuchElementException();
        Queue<Key> queue = new Queue<>();
        preOrder(root, queue);
        return queue;
    }

    public Iterable<Key> preOrder(Node x, Queue<Key> queue) {
        if (x == null) return null;
        queue.enqueue(x.key);
        preOrder(x.left, queue);
        preOrder(x.right, queue);
        return queue;
    }

    /**
     * 中序遍历 左-根-右
     */
    public Iterable<Key> inOrder() {
        if (root == null) throw new NoSuchElementException();
        Queue<Key> queue = new Queue<>();
        inOrder(root, queue);
        return queue;
    }

    public Iterable<Key> inOrder(Node x, Queue<Key> queue) {
        if (x == null) return null;
        inOrder(x.left, queue);
        queue.enqueue(x.key);
        inOrder(x.right, queue);
        return queue;
    }

    /**
     * 后续遍历 左-右-根
     */
    public Iterable<Key> postOrder() {
        if (root == null) throw new NoSuchElementException();
        Queue<Key> queue = new Queue<>();
        postOrder(root, queue);
        return queue;
    }

    public Iterable<Key> postOrder(Node x, Queue<Key> queue) {
        if (x == null) return null;

        postOrder(x.left, queue);
        postOrder(x.right, queue);
        queue.enqueue(x.key);
        return queue;
    }


    public static void main(String[] args) {
        AVLTressST<String, Integer> st = new AVLTressST<>();
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

        int b = st.get("b");
        int m = st.get("m");
        int o = st.get("o");

        String b1 = st.min("b");
        String o1 = st.min("o");
        String s = st.min("s");

        String max = st.max();
        String m1 = st.max("m");
        String b2 = st.max("b");
        String o2 = st.max("o");

        String c = st.floor("c");
        String n = st.floor("n");
        String p = st.floor("p");

        String p1 = st.ceiling("p");
        String c1 = st.ceiling("c");
        String j = st.ceiling("j");

        String select = st.select(3);
        String select1 = st.select(7);

        int i = st.rank("i");
        int p2 = st.rank("p");

        Iterable<String> strings = st.keysLevelOrder();
        for (String string : strings) {
            StdOut.println(string + " " + st.get(string));
        }

//        st.delete("m"); //right
//        st.delete("s");
//        st.put("s", null);
//        st.deleteMin();
//        st.deleteMax();


        StdOut.println("--------------keys()----------------------");
        Iterable<String> keys = st.keys();
        for (String key : keys) {
            StdOut.println(key + " " + st.get(key));
        }

        StdOut.println("--------------preOrder()----------------------");
        Iterable<String> preOrder = st.preOrder();
        for (String key : preOrder) {
            StdOut.println(key + " " + st.get(key));
        }

        StdOut.println("--------------inOrder()----------------------");
        Iterable<String> inOrder = st.inOrder();
        for (String key : inOrder) {
            StdOut.println(key + " " + st.get(key));
        }

        StdOut.println("--------------postOrder()----------------------");
        Iterable<String> postOrder = st.postOrder();
        for (String key : postOrder) {
            StdOut.println(key + " " + st.get(key));
        }

        System.out.println();
    }


}
