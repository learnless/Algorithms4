package chapter02.t4;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by learnless on 17.11.1.
 */
public class IndexMinPQ<Item extends Comparable<Item>> implements Iterable<Integer> {

    private int maxN;   //最大n值
    private int n;
    private int[] pq;   //pa[k] = n
    private int[] qp;   // qp[pq[i]] = pq[qp[i]] = i
    private Item[] items;   //keys[i] = priority of i

    public IndexMinPQ() {
        this(10);
    }

    public IndexMinPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException();
        this.maxN = maxN;
        n = 0;
        items = (Item[]) new Comparable[maxN + 1];
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];
        for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
    }

    public void insert(int k, Item item) {
        if (k < 0 || k >= maxN) throw new IndexOutOfBoundsException();
        if(contains(k)) throw new IllegalArgumentException("键key已经存在");
        n++;
        qp[k] = n;
        pq[n] = k;
        items[k] = item;
        swin(n);
        if (n >= maxN - 1)  resize(maxN*2+1);
    }

    /**
     * 自动增长
     * @param size
     */
    public void resize(int size) {
        int[] pqt = new int[size];
        int[] qpt = new int[size];
        Item[] itemst = (Item[]) new Comparable[size];
        for (int i = 0; i <= n; i++) {
            pqt[i] = pq[i];
            qpt[i] = qp[i];
            itemst[i] = items[i];
        }
        for (int i = n + 1; i < size; i++) {
            qpt[i] = -1;
        }

        pq = pqt;
        qp = qpt;
        items = itemst;
        maxN = size;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int minIndex() {
        if(n == 0)  throw new NoSuchElementException("队列为空");
        return pq[1];
    }

    public Item minItem() {
        if(n == 0)  throw new NoSuchElementException("队列为空");
        return items[pq[1]];
    }

    public int delMin() {
        if(n == 0)  throw new NoSuchElementException("队列为空");
        int min = pq[1];
        exch(1, n--);
        sink(1);
        qp[min] = -1;   //delete
        pq[n+1] = -1;
        items[min] = null;

//        if (n <= maxN / 4)  resize(maxN/2);
        return min;
    }

    public Item itemOf(int k) {
        if (k < 0 || k > maxN) throw new IndexOutOfBoundsException();
        if(!contains(k))    throw new NoSuchElementException("队友没有对应key");
        return items[k];
    }

    public void changeItem(int k, Item item) {
        if (k < 0 || k > maxN) throw new IndexOutOfBoundsException();
        if(!contains(k))    throw new NoSuchElementException("队友没有对应key");
        items[k] = item;
        swin(qp[k]);
        sink(qp[k]);
    }

    public void change(int k, Item item) {
        changeItem(k, item);
    }

    public void decreaseKey(int k, Item item) {
        if (k < 0 || k > maxN) throw new IndexOutOfBoundsException();
        if(!contains(k))    throw new NoSuchElementException("队友没有对应key");
        if(items[k].compareTo(item) <= 0)   throw new IllegalArgumentException("decreaseKey方法不符合上浮条件");
        items[k] = item;
        swin(qp[k]);
    }

    public void increaseKey(int k, Item item) {
        if (k < 0 || k > maxN) throw new IndexOutOfBoundsException();
        if(!contains(k))    throw new NoSuchElementException("队友没有对应key");
        if(items[k].compareTo(item) <= 0)   throw new IllegalArgumentException("increasekey方法不符合下沉条件");
        items[k] = item;
        sink(qp[k]);
    }

    public void delete(int k) {
        if (k < 0 || k > maxN) throw new IndexOutOfBoundsException();
        if(!contains(k))    throw new NoSuchElementException("队友没有对应key");
        int index = qp[k];
        exch(index, n--);
        swin(index);
        sink(index);
        items[k] = null;    //游离元素
        qp[k] = -1;

//        if (n <= maxN / 4)  resize(maxN / 2);
    }

    public boolean contains(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        return qp[i] != -1;
    }

    private boolean greater(int i, int j) {
        return items[pq[i]].compareTo(items[pq[j]]) > 0;
    }

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void swin(int k) {
        while(k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if(j < n && greater(j, j+1))    j++;
            if(!greater(k, j))  break;
            exch(k, j);
            k = j;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Integer> {
        private IndexMinPQ<Item> copy;

        public HeapIterator() {
            copy = new IndexMinPQ<>(pq.length - 1);
            for (int i = 1; i <= n; i++)
                copy.insert(pq[i], items[pq[i]]);
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }

    public static void main(String[] args) {
        String[] strings = {"it", "was", "the", "best", "of", "times", "it", "was", "the", "worst"};

//        IndexMinPQ<String> pq = new IndexMinPQ<>(strings.length);
        IndexMinPQ<String> pq = new IndexMinPQ<>(1);
        for (int i = 0; i < strings.length; i++)
            pq.insert(i, strings[i]);

        while (!pq.isEmpty()) {
            int i = pq.delMin();
            StdOut.println(i + " " + strings[i]);
        }
        StdOut.println();

        //重新构造队列
        for (int i = 0; i < strings.length; i++)
            pq.insert(i, strings[i]);

        for (int i : pq)
            StdOut.println(i + " " + strings[i]);

        while (!pq.isEmpty())
            pq.delMin();

    }

}
