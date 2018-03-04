package chapter02.t1;

import java.util.Scanner;

import org.util.TimerUtil;

import chapter02.t2.Merge;
import chapter02.t2.MergeBU;
import chapter02.t3.Quick;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * 比较排序算法速度
 * @author Administrator
 *
 */
public class SortCompare {
	
	public static double time(String alg, Comparable[] a) {
		TimerUtil timer = new TimerUtil();
		if(alg.equals("Selection"))	Selection.sort(a);
		if(alg.equals("Insertion"))	Insertion.sort(a);
		if(alg.equals("Shell"))	Shell.sort(a);
		if(alg.equals("Merge"))	Merge.sort(a);
		if(alg.equals("MergeBU"))	MergeBU.sort(a);
		if(alg.equals("Quick"))	Quick.sort(a);
		
		return timer.stop();
	}
	
	/**
	 * 随机生成数组，排序获取总时间
	 * @param N 数组大小
	 * @param T 总排序次数
	 */
	public static double timeRandom(String alg, int N ,int T) {
		double total = 0.0;
		Double[] a = new Double[N];
		for (int i = 0; i < T; i++) {
			for (int j = 0; j < N; j++) {	//随机生成数组
				a[j] = StdRandom.uniform();
			}
			total += time(alg, a);
		}
		
		return total;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String alg1 = sc.next();
		String alg2 = sc.next();
		int N = sc.nextInt();
		int T = sc.nextInt();
		
		double t1 = timeRandom(alg1, N, T);
		double t2 = timeRandom(alg2, N, T);
		
		StdOut.printf("For %d random Doubles\n   %s is", N, alg1);
		StdOut.printf(" %.4f times faster than %s\n", t2/t1, alg2);
		
	}

}
