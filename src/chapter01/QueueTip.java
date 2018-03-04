package chapter01;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

/**
 * 队列
 * @author Administrator
 *
 */
public class QueueTip {
	
	//队列简单实例
	public static int[] readInts(String name) {
		In in = new In(name);
		Queue<Integer> queue = new Queue<>();
		while(!in.isEmpty()) {
			queue.enqueue(in.readInt());
		}
		int N = queue.size();
		//队列转化为数组
		int[] a = new int[N];
		for (int i = 0; i < a.length; i++) {
			a[i] = queue.dequeue();
		}
		
		return a;
	}
	
	public static void main(String[] args) {
		String path = Class.class.getClass().getResource("/").getPath();
		int[] a = readInts(path+ "largeW.txt");
		System.out.println(a);
	}

}
