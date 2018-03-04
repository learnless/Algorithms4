package chapter02.t1;

import org.util.ReadUtil;

import edu.princeton.cs.algs4.StdOut;

/**
 * 选择排序
 * 
 * @author Administrator
 * 
 */
public class Selection {

	// 排序,冒泡排序
	public static void sort(Comparable[] a) {
		int N = a.length;
		for (int i = 0; i < N-1; i++) {
			int min = i;
			// 进行比较，取最小值的下标
			for (int j = i + 1; j < N; j++) {
				if (less(a[j], a[min]))
					min = j;
			}
			exch(a, i, min); // 交换
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
		String[] a = ReadUtil.getString("sort.txt");
		sort(a);
		assert isSorted(a);
		show(a);
	}

}
