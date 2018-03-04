package chapter01;

import java.util.Scanner;

import edu.princeton.cs.algs4.Interval1D;
import edu.princeton.cs.algs4.Interval2D;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;

/**
 * 生成画点，某个区域命中范围
 * @author Administrator
 *
 */
public class Interal2D {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		double x1 = scanner.nextDouble();
		double x2 = scanner.nextDouble();
		double y1 = scanner.nextDouble();
		double y2 = scanner.nextDouble();
		int T = scanner.nextInt();	//个数
		
		Interval1D xInterval = new Interval1D(x1, x2);
		Interval1D yInterval = new Interval1D(y1, y2);
		Interval2D box = new Interval2D(xInterval, yInterval);
		box.draw();
		
		Counter hits = new Counter("hits");
		for (int i = 0; i < T; i++) {
			double x = Math.random();
			double y = Math.random();
			Point2D p = new Point2D(x, y);
			//如果命中不画
			if(box.contains(p))	hits.increment();
			else	p.draw();
		}
		
		StdOut.println(hits);
		StdOut.println(box.area());
	}

}
