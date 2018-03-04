package chapter01.t4;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import org.util.TimerUtil;

import java.util.Arrays;

/**
 * 成倍产生数据，观察程序运行时间
 * @author Administrator
 *
 */
public class DoublingTest {
	
	public static double runtime(int size) {
		int MAX = 1000000;	//产生数在-100W 100W之间
		int[] a = new int[size];
		for (int i = 0; i < a.length; i++) {
			a[i] = StdRandom.uniform(-MAX, MAX);
		}
		Arrays.sort(a);
		TimerUtil timer = new TimerUtil();
//		ThreeSum.count(a);
		ThreeSumFast.threefast(a);
		return timer.stop();
	}
	
	public static void main(String[] args) {
		double prev = runtime(125);
		for (int i = 250; true; i *= 2) {
			double second = runtime(i);
			StdOut.printf("%7d %5.6f %5.1f\n", i, second, second/prev);
			prev = second;
		}
	}

}
