package chapter01.t4;

import org.util.ReadUtil;

import edu.princeton.cs.algs4.StdOut;

/**
 * 简单统计数组三个不同元素和为零
 * @author Administrator
 *
 */
public class ThreeSum {
	
	public static int count(int[] a) {
		int N = a.length;
		int count = 0;
		for (int i = 0; i < N; i++) {
			for (int k = i+1; k < N; k++) {
				for (int j = k+1 ; j < N; j++) {
					if(a[i] + a[k] + a[j] == 0) {count++;}
				}
			}
		}
		
		return count;
	}
	
	public static void main(String[] args) {
		int[] a = ReadUtil.getInt("4Kints.txt");
		StdOut.print("数组个数为："+a.length+",   三个不同元素为零个数：" + count(a));
	}
}
