package chapter01;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * 使用自定义api求平均值
 * @author Administrator
 *
 */
public class Average {

	public static void main(String[] args) {
		double sum = 0;
		int count = 0;
		while(!StdIn.isEmpty()) {
			sum += StdIn.readDouble();
			count++;
		}
		double avg = sum /count;
		StdOut.printf("输入数据平均值为： %.5f\n", avg);

	}

}
