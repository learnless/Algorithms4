package org.util;

import edu.princeton.cs.algs4.StdRandom;

/**
 * 数组中位数工具类
 * @author Administrator
 *
 */
public class MedianUtil {

	/**
	 * 获取数组中位数
	 * @param a	数组
	 * @param lo	最低位
	 * @param hi	最高位
	 * @param num	在数组中取样个数
	 * @return
	 */
	public static Comparable getMedian(Comparable[] a, int lo, int hi, int num) {
		Comparable[] c = new Comparable[num];
		for (int i = 0; i < num; i++) {
			c[i] = a[StdRandom.uniform(lo, hi+1)];
		}
		//将数组c获取中位数，可以用插入排序好，方便获取下标
		for (int i = 1; i < num; i++)
			for (int j = i; j > 0 && less(c[j], c[j-1]); j--)
				exch(c, j, j-1);
		
		return c[num/2];
	}

	//数组交换值
	private static void exch(Comparable[] c, int j, int i) {
		Comparable t = c[j];
		c[j] = c[i];
		c[i] = t;
	}

	//数组比较大小
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	public static void main(String[] args) {
		Comparable[] a = {3, 2, 8, 1, 9, 32, 18, 5, -2, 13, 100, 0};
		Comparable c = getMedian(a, 0, a.length, 3);
		System.out.println(c);
	}
	

}
