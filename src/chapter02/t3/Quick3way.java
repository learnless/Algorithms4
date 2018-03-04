package chapter02.t3;

import org.util.ReadUtil;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * 三向切分快速排序，应用于大量重复的元素
 * 
 * @author Administrator
 * 
 */
public class Quick3way {

	// 排序，将数组切分，某位置左边小于该值，右边大于该值
	public static void sort(Comparable[] a) {
		StdRandom.shuffle(a);
		sort(a, 0, a.length - 1);
	}

	private static void sort(Comparable[] a, int lo, int hi) {
		if (lo >= hi)	return;
		int lt = lo, i = lo + 1, gt = hi;
		Comparable v = a[lo];
		while(i <= gt) {
			int cmp = a[i].compareTo(v);
			//<
			if(cmp < 0)	exch(a, i++, lt++);
			//=
			else if(cmp == 0)	i++;
			//>
			else	exch(a, i, gt--);
		}
		sort(a, lo, lt-1);
		sort(a, gt+1, hi);
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
