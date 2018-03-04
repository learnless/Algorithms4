package chapter02.t4;

import edu.princeton.cs.algs4.StdOut;
import org.junit.Assert;
import org.util.NoSuchElementException;

import java.util.Comparator;
import java.util.Iterator;

public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;    //队列
    private int N;    //队列个数
    private Comparator<Key> comparator;    //比较元素

    /**
     * 默认构造器
     */
    public MaxPQ() {
        this(1);
    }

    /**
     * 创建容量为size优先队列
     */
    public MaxPQ(int size) {
        pq = (Key[]) new Comparable[size + 1];
        N = 0;
    }

    /**
     * a[]创建一个优先队列
     */
    public MaxPQ(Key[] keys) {
        this(1);
        for (Key k : keys) {
            insert(k);
        }
    }

    /**
     * 由key值删除
     */
    public void delete(Key key) {
        if (isEmpty()) throw new NoSuchElementException("队列为空!");
        if (N == pq.length / 4) resize(pq.length / 2);

        if (!contain(key)) return;
        int index = index(key);
        del(key, index);
    }

    private void del(Key key, int index) {
        exch(index, N--);
        pq[N+1] = null;
        int reIndex = index(key);
        if (reIndex == -1)  return;
        del(key, reIndex);
    }

    /**
     * 由坐标index删除
     */
    public void delete(int index) {
        if (isEmpty()) throw new NoSuchElementException("队列为空!");
        if (N == pq.length / 4) resize(pq.length / 2);
        if(index > N)   throw new NoSuchElementException("下标超出范围");

        exch(index, N--);
        sink(index);

        pq[N+1] = null;
    }

    /**
     * 删除最大元素,底部与头部交换值，然后下沉
     */
    public Key delMax() {
        if (isEmpty()) throw new NoSuchElementException("队列为空!");
        if (N == pq.length / 4) resize(pq.length / 2);
        Key max = pq[1];
        exch(1, N--);
        pq[N + 1] = null;    //防止元素游离
        sink(1);
        return max;
    }

    /**
     * 插入key,在底部插入后上浮
     */
    public void insert(Key key) {
        //动态调整数组大小
        if (N >= pq.length - 1) resize(2 * pq.length);
        pq[++N] = key;
        swim(N);
    }

    /**
     * 动态跳转数组大小
     *
     * @param size 调整后数组大小
     */
    private void resize(int size) {
        Key[] t = (Key[]) new Comparable[size];
        for (int i = 1; i <= N; i++)
            t[i] = pq[i];
        pq = t;
    }

    /**
     * 返回最大元素
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("队列为空!");
        return pq[1];
    }

    /**
     * 队列是否为空
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * 队列大小
     */
    public int size() {
        return N;
    }

    /**
     * 比较i<j true
     */
    public boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    /**
     * 交换数据
     */
    public void exch(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    /**
     * 下沉，直到遇到比k小
     */
    public void sink(int k) {
        int j = 0;
        while (2 * k <= N) {
            j = k * 2;
            if (j < N && less(j, j + 1)) j++;    //获取左右节点最大坐标
            if (!less(k, j)) break;    //遇到子节点不大于根节点退出
            exch(k, j);
            k = j;
        }

    }

    /**
     * 上浮，直到遇到比k大
     */
    public void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    /**
     * 是否包含指定值
     */
    public boolean contain(Key key) {
        for(int i = 1; i <= N; i++)
            if (pq[i].compareTo(key) == 0)
                return true;
        return false;
    }

    /**
     * 包含key值的下标,返回最前面
     */
    public int index(Key key) {
        for (int i = 1; i <= N; i++) {
            if (pq[i].compareTo(key) == 0)
                return i;
        }
        return -1;
    }

    public void print() {
        for(int i = 1; i <= N; i++)
            System.out.print(pq[i] + "  ");
    }

    /**
     * 循环队列
     */
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key> {
        private MaxPQ<Key> copy;

        public HeapIterator() {
            copy = new MaxPQ(size());
            for (int i = 1; i <= N; i++)
                copy.insert(pq[i]);
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public Key next() {
            if (!hasNext()) throw new NoSuchElementException("队列为空！");
            return copy.delMax();
        }

        @Override
        public void remove() {
            // TODO Auto-generated method stub

        }

    }

    public static void main(String[] args) {
//		MaxPQ<String> maxPQ = new MaxPQ<String>();
//        while (!StdIn.isEmpty()) {	//ctrl+d结束
//            String item = StdIn.readString();
//            if (!item.equals("-")) maxPQ.insert(item);
//            else if (!pq.isEmpty()) StdOut.println(maxPQ.delMax() + " ");
//        }
//        String[] array = new String[]{"a", "e", "f", "b", "y", "m", "b"};
        String[] array = new String[]{"e",""};
        MaxPQ maxPQ = new MaxPQ(array);
        Iterator it = maxPQ.iterator();
        while (it.hasNext())
            StdOut.print(it.next() + " ");
        System.out.println();
        maxPQ.print();
        int index = maxPQ.index("b");
        maxPQ.delete(2);
        maxPQ.delete("b");
        Assert.assertTrue(maxPQ.contain("y"));
        Assert.assertFalse(maxPQ.contain("i"));
        StdOut.println("(" + maxPQ.size() + " left on pq)");

    }

}
