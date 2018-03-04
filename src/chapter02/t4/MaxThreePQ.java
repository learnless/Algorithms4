package chapter02.t4;

/**
 * 三叉数优先队列
 * Created by learnless on 17.10.27.
 */
public class MaxThreePQ<Key extends Comparable<Key>> {

    private Key[] pq;
    private int N;

    public MaxThreePQ() {
        this(1);
    }

    public MaxThreePQ(int size) {
        pq = (Key[]) new Comparable[size + 1];
        N = 0;
    }

    public MaxThreePQ(Key[] keys) {
        this(1);
        for (Key key : keys) {
            insert(key);
        }
    }

    public void insert(Key key) {
        if (N >= pq.length - 1) resize(pq.length * 2);
        pq[++N] = key;
        swin(N);
    }

    public Key delMax() {
        Key key = pq[1];
        exch(1, N--);
        sink(1);
        pq[N+1] = null;
        return key;
    }

    private void sink(int k) {
        int j = 0;
        while (k * 3 - 1 <= N) {
            //子节点最小值
            j = k * 3 - 1;
            if(j + 1  <= N && less(j, j+1)) {    //有两子节点
                j++;
            } else if(j + 2 <= N) {
                j = less(j, j+1) ? (less(j+1, j+2) ? j+2 : j+1) : (less(j, j+2) ? j+2 : j);
            }

            if(!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private void swin(int k) {
        while (k > 1 && less((k + 1) / 3, k)) {
            exch((k + 1) / 3, k);
            k = (k + 1) / 3;
        }
    }

    private void exch(int i, int k) {
        Key key = pq[i];
        pq[i] = pq[k];
        pq[k] = key;
    }

    private boolean less(int i, int k) {
        return pq[i].compareTo(pq[k]) < 0;
    }

    private void resize(int size) {
        Key[] t = (Key[]) new Comparable[size];
        for (int i = 1; i <= N; i++)
            t[i] = pq[i];
        pq = t;
    }

    private int size() {
        return N;
    }

    private void print() {
        for (int i = 1; i <= N; i++) {
            System.out.print(pq[i] + " ");
        }
    }

    public static void main(String[] args) {
        String[] array = new String[]{"a", "e", "f", "b", "y", "m", "b", "w", "i", "q", "s"};
        MaxThreePQ maxThreePQ = new MaxThreePQ(array);
        maxThreePQ.print();
        System.out.println();
        System.out.println("------------------------");
        int size = maxThreePQ.size();
        for (int i = 0; i < size; i++) {
            System.out.print(maxThreePQ.delMax() + "     ");
        }
    }

}
