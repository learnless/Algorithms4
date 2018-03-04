package chapter01.t4;

import java.util.Arrays;

import org.util.BinarySearch;
import org.util.ReadUtil;
import org.util.TimerUtil;

import edu.princeton.cs.algs4.StdOut;

/**
 * 数组中两不同元素之和为零，用二分查找
 * @author Administrator
 *
 */
public class TwoSumFast {
	
	public static double two(int[] a) {
		int N = a.length;
		int count = 0;
		TimerUtil timer = new TimerUtil();
		
		Arrays.sort(a);
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				if(a[i] + a[j] == 0)
					count++;
			}
		}
		
		return timer.stop();
	}
	
	public static double twofast(int[] a) {
		int N = a.length;
		int count = 0;
		TimerUtil timer = new TimerUtil();
		
		Arrays.sort(a);
		for (int i = 0; i < N; i++) {
			if(BinarySearch.rank(-a[i], a) > i) {
				count++;
			}
		}
		
		return timer.stop();
	}
	
	public static void main(String[] args) {
		int[] a = ReadUtil.getInt("8Kints.txt");
		double second = twofast(a);
		double second2 = two(a);
		StdOut.printf("%5.8f\n", second);
		StdOut.printf("%5.8f\n", second2);
	}
	
}
