package org.util;

import chapter01.t4.ThreeSum;
import edu.princeton.cs.algs4.StdOut;

/**
 * 计时器
 * @author Administrator
 *
 */
public class TimerUtil {
	private long start;

	public TimerUtil() {
		start = System.currentTimeMillis();
	}
	
	public void start() {
		start = System.currentTimeMillis();
	}
	
	public double stop() {
		return (System.currentTimeMillis() - start) / 1000.0;	//返回秒
	}
	
	public static void main(String[] args) {
		int[] a = ReadUtil.getInt("4Kints.txt");
		TimerUtil timer = new TimerUtil();
		int count = ThreeSum.count(a);
		double second = timer.stop();
		StdOut.print("数组个数为："+a.length+",   三个不同元素为零个数：" + count +",  花费时间为："+second + "s");
	}

}
