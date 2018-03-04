package chapter01;

import java.util.Scanner;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * 可视化累加器
 * @author Administrator
 *
 */
public class VisualAccumulator {
	private double total;
	private int count;
	
	public VisualAccumulator(int trials, double max) {
		this.total = 0;
		this.count = 0;
		
		StdDraw.setXscale(0, trials);
		StdDraw.setYscale(0, max);
		StdDraw.setPenRadius(.005);
	}
	
	public void add(double val) {
		count++;
		total += val;
		StdDraw.setPenColor(StdDraw.DARK_GRAY);
		StdDraw.point(count, val);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.point(count, mean());
	}
	
	public double mean() {
		return total / count;
	}

	@Override
	public String toString() {
		return "VisualAccumulator [total=" + total + ", count=" + count + "]";
	}
	
	
	public static void main(String[] args) {
		int count = new Scanner(System.in).nextInt();
		VisualAccumulator va = new VisualAccumulator(count, 1.0);
		for (int i = 0; i < count; i++) {
			va.add(StdRandom.random());
		}
		StdOut.println(va);
	}
	
}
