package chapter02.t1;

import edu.princeton.cs.algs4.StdOut;

/**
 * @author Administrator
 * 
 */
public class BaseSort {

	// 排序
	public static void sort(Comparable[] a) {
		
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
			if(less(a[i], a[i-1]))	return false;
		}
		return true;
	}

}
