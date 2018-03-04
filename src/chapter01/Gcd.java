package chapter01;

import java.util.Scanner;

/**
 * 最大公约数
 * 
 * @author Administrator
 * 
 */
public class Gcd {

	// 实现一，递归
	public static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		}
		int t = a % b;

		return gcd(b, t);
	}
	
	public static int gcd2(int a, int b) {
		int t = a % b;
		while(t!=0) {
			a = b;
			b = t;
			t = a % b;
		}
		
		return b;
	}
	

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		int b = scanner.nextInt();
		int result = gcd2(a, b);

		System.out.println(result);
	}

}
