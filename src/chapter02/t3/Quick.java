package chapter02.t3;

import chapter02.t1.Insertion;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.util.ReadUtil;

/**
 * 快速排序
 * 
 * @author Administrator
 * 
 */
public class Quick {

	// 排序，将数组切分，某位置左边小于该值，右边大于该值
	public static void sort(Comparable[] a) {
		StdRandom.shuffle(a);
		sort(a, 0, a.length - 1);
	}

	private static void sort(Comparable[] a, int lo, int hi) {
		// 1.快速算法改进1，小数组使用插入排序算法
		final int M = 10; // M与系统相关，一般为5-15
		 if (lo >= hi) return;
		if (lo + M >= hi) {
			Insertion.sort(a, lo, hi);
			return;
		}
		int j = partition(a, lo, hi); // 快速切分
		sort(a, lo, j - 1);
		sort(a, j + 1, hi);

	}

	// 获取该位置在数组中的位置，并排序左右两边顺序
	private static int partition(Comparable[] a, int lo, int hi) {
		// 2.快速排序改进2，取样问题，可三取样切分，取出中位数来切分数组
		 Comparable v = a[lo];
		//TODO
//		Comparable v = MedianUtil.getMedian(a, lo, hi, 3);

		int i = lo, j = hi + 1;
		// 左小右大，不满足退出
		while (true) {
			while (less(a[++i], v))
				if (i == hi)
					break;
			while (less(v, a[--j]))
				if (j == lo)
					break;
			if (i >= j)
				break;
			exch(a, i, j);
		}
		exch(a, lo, j);

		return j;
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
		Assert.assertTrue(isSorted(a));
		show(a);
	}

}
