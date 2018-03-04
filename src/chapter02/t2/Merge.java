package chapter02.t2;

import org.util.ReadUtil;

import edu.princeton.cs.algs4.StdOut;

/**
 * 归并排序，自顶向下
 * 
 * @author Administrator
 * 
 */
public class Merge {

	private static Comparable[] aux; // 辅助数组

	// 归并排序
	public static void sort(Comparable[] a) {
		aux = new Comparable[a.length];
		sort(a, 0, a.length - 1); // 归并排序
	}

	// 递归调用,将数组切分两段排序,直到lo<=hi退出
	private static void sort(Comparable[] a, int lo, int hi) {
		if (lo >= hi)
			return;
		int mid = (lo + hi) / 2;
		sort(a, lo, mid);
		sort(a, mid + 1, hi);
		merge(a, lo, mid, hi); // 归并结果
	}

	// 将两段排序好的数组进行归并排序,a[lo..mid] a[mid+1..hi]
	private static void merge(Comparable[] a, int lo, int mid, int hi) {
		int i = lo, j = mid + 1;
		// 将a数组复制到aux
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}
		// 归并排序
		for (int k = lo; k <= hi; k++) {
			if (i > mid)
				a[k] = aux[j++];
			else if (j > hi)
				a[k] = aux[i++];
			else {
				if (less(aux[i], aux[j]))
					a[k] = aux[i++];
				else
					a[k] = aux[j++];
			}
		}

	}

	// 比较
	public static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	// 交换
	public static void exch(Comparable[] a, int i, int j) {
		Comparable temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	// 展示
	public static void show(Comparable[] a) {
		for (Comparable c : a) {
			StdOut.print(c + " ");
		}
		StdOut.println();
	}

	// 是否已排序
	public static boolean isSorted(Comparable[] a) {
		for (int i = 1; i < a.length; i++) {
			if (less(a[i], a[i - 1]))
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		String[] a = ReadUtil.getString("sort2.txt");
		sort(a);
		assert isSorted(a);
		show(a);
	}

}
