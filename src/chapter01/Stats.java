package chapter01;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * 背包简单实现
 * @author Administrator
 *
 */
public class Stats {
	
	public static void main(String[] args) {
		Bag<Double> bag = new Bag<>();
		while(!StdIn.isEmpty()) {
			bag.add(StdIn.readDouble());
		}
		//求平均值
		double sum = 0;
		for (Double d : bag) {
			sum += d;
		}
		double avg = sum / bag.size();
		
		//算术平均值
		sum = 0;
		for (Double d : bag) {
			sum += Math.pow(d-avg, 2);
		}
		double std = Math.sqrt(sum);
		
		StdOut.printf("平均值为：%.2f\n", avg);
		StdOut.printf("算术平均值：%.2f\n", sum);
		
	}

}
