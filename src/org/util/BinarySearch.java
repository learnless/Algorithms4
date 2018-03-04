package org.util;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


/**
 * 二叉查找
 * @author Administrator
 *
 */
public class BinarySearch {
	
	//实现一，非递归
	public static int rank(int key, int[] a) {
		int lo = 0;
		int hi = a.length-1;
		while(lo <= hi) {
			int mid = (lo + hi) /2;
			if(key > a[mid]) {
				lo = mid +1;
			} else if(key < a[mid]) {
				hi = mid -1;
			} else {	//找到相等值
				return mid;
			}
		}
		
		return -1;
	}
	
	//实现二，递归
	public static int rankRecursion(int key, int[] a) {
		return rank(key, a, 0, a.length-1);
	}
	
	
	private static int rank(int key, int[] a, int lo, int hi) {
		if(lo > hi)	return -1;
		int mid = (lo + hi) /2;
		if(key > a[mid])	return rank(key, a, mid+1, hi);
		else if(key < a[mid])	return rank(key, a, lo, mid-1);
		else	return mid;
	}

	public static void main(String[] args) throws Exception {
		int[] a = ReadUtil.getInt("largeW.txt");
		Arrays.sort(a);
		
		System.out.println("请输入要查找的key:");
		while(!StdIn.isEmpty()) {
			int key = StdIn.readInt();
			int result = rankRecursion(key, a);
			StdOut.println("查找结果：" + result);
		}
		
	}

}
