package chapter01;

import java.util.Scanner;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * 模拟投币概率
 * @author Administrator
 *
 */
public class Flips {

	public static void main(String[] args) {
		int count = new Scanner(System.in).nextInt();
		Counter head = new Counter("head");
		Counter tail = new Counter("tail");
		for (int i = 0; i < count; i++) {
			if(StdRandom.bernoulli(0.5))
				head.increment();
			else
				tail.increment();
		}
		StdOut.println(head);
		StdOut.println(tail);
		int d = head.getCount() - tail.getCount();
		StdOut.println("正反面相差：" + Math.abs(d));

	}

}
