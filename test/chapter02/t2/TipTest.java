package chapter02.t2;

import org.junit.Test;

/**
 * Created by learnless on 17.10.24.
 */
public class TipTest {

    private Comparable[] aux;

    @Test
    public void shell() {
        Comparable[] a = {3, 2, 8, 1, 9};
        shellSort(a);
        for (Comparable i : a) {
            System.out.println(i);
        }
    }

    @Test
    public void merge() {
        Comparable[] a = {3, 2, 8, 1, 9, 32, 18, 5, -2, 13};
        aux = new Comparable[a.length];
        mergeSort(a, 0, a.length - 1);
        for (Comparable c : a) {
            System.out.println(c);
        }
    }

    @Test
    public void mergeBU() {
        Comparable[] a = {3, 2, 8, 1, 9, 32, 18, 5, -2, 13};
        aux = new Comparable[a.length];
        mergeBUSort(a);
        for (Comparable c : a)
            System.out.println(c);
    }

    @Test
    public void quick() {
        Comparable[] a = {3, 2, 8, 1, 9, 32, 18, 5, -2, 13, 100, 0};
        quickSort(a, 0, a.length - 1);
        print(a);
    }

    @Test
    public void quick3Way() {
        Comparable[] a = {3, 2, 8, 1, 9, 32, 18, 5, -2, 13, 100, 0};
        quickSort3Way(a, 0, a.length - 1);
        print(a);
    }

    /**
     * shell 排序
     *
     * @param a
     */
    private void shellSort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) h = 3 * h + 1;

        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; (j >= h) && less(a[j], a[j - h]); j = j - h) {
                    exch(a, j, j - h);
                }
            }

            h = h / 3;
        }
    }

    /**
     * 归并排序
     *
     * @param a
     */
    private void mergeSort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        int mid = (lo + hi) / 2;
        mergeSort(a, lo, mid);
        mergeSort(a, mid + 1, hi);
        merge(a, lo, mid, hi);
    }

    private void mergeBUSort(Comparable[] a) {
        for (int sz = 1; sz < a.length; sz = sz + sz)
            for (int lo = 0; lo < a.length - sz; lo += 2 * sz)
                merge(a, lo, lo + sz - 1, Math.min(lo + 2 * sz - 1, a.length - 1));

    }

    /**
     * 快速排序
     * @param a
     * @param lo
     * @param hi
     */
    private void quickSort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        quickSort(a, lo, j - 1);
        quickSort(a, j + 1, hi);
    }

    /**
     * 三向切分快速排序
     * @param a
     * @param lo
     * @param hi
     */
    private void quickSort3Way(Comparable[] a, int lo, int hi) {
        if(lo >= hi)    return;
        int it = lo, i = lo + 1, gt = hi;
        Comparable v = a[lo];
        while (i <= gt) {
            int result = a[i].compareTo(v);
            if(result > 0) {
                exch(a, i, gt--);
            } else if(result < 0) {
                exch(a, i++, it++);
            } else {
                i++;
            }
        }

        quickSort3Way(a, lo, it -1);
        quickSort3Way(a, gt+1, hi);
    }

    private int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            while (less(a[++i], v))
                if (i >= hi)
                    break;
            while (less(v, a[--j]))
                if(j <= lo)
                    break;
            if(i >= j)
                break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[i], aux[j])) a[k] = aux[i++];
            else a[k] = aux[j++];
        }

    }

    private void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private boolean less(Comparable a, Comparable b) {
        if (a.compareTo(b) < 0)
            return true;
        return false;
    }

    private void print(Comparable[] a) {
        for (Comparable c : a)
            System.out.println(c);
    }

}
