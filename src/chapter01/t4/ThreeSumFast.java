package chapter01.t4;

import java.util.Arrays;

import org.util.BinarySearch;
import org.util.ReadUtil;
import org.util.TimerUtil;

import edu.princeton.cs.algs4.StdOut;

/**
 * 数组中三不同元素之和为零，用二分查找，可参考TwoSumFast
 * @author Administrator
 *
 */
public class ThreeSumFast {
	
	public static double three(int[] a) {
		int N = a.length;
		int count = 0;
		TimerUtil timer = new TimerUtil();
		
		for (int i = 0; i < N; i++)
			for (int j = i+1; j < N; j++)
				for (int k = j+1; k < N; k++)
					if(a[i]+a[j]+a[k] == 0)
						count++;
		return timer.stop();
	}
	
	public static double threefast(int[] a) {
		int N = a.length;
		int count = 0;
		Arrays.sort(a);
		TimerUtil timer = new TimerUtil();
		
		for (int i = 0; i < N; i++)
			for (int j = i+1; j < N; j++)
				if(BinarySearch.rank(-a[i]-a[j], a) > j)
					count++;
				
		return timer.stop();
	}
	
	public static void main(String[] args) {
		int[] a = ReadUtil.getInt("4Kints.txt");
		double second = threefast(a); 
		double second2 = three(a);
		StdOut.printf("%5.8f\n", second);
		StdOut.printf("%5.8f\n", second2);
	}

}
