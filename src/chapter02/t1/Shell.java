package chapter02.t1;

import org.util.ReadUtil;

import edu.princeton.cs.algs4.StdOut;

/**
 * 希尔排序，分治思想
 * 
 * @author Administrator
 * 
 */
public class Shell {

	// 排序,将数组切分为片段
	public static void sort(Comparable[] a) {
		int N = a.length;
		int h = 1;
		while (h < N / 3)
			h = 3 * h + 1; // 数组切分的片段数
		while (h >= 1) {
			for (int i = h; i < N; i++)
				// 将a[i]输入到a[i-h],a[i-2h]...之中
				for (int j = i; j >= h && less(a[j], a[j - h]); j -= h)
					exch(a, j, j - h);
			h = h / 3;
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
