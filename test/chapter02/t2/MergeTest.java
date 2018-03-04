package chapter02.t2;

import org.util.ReadUtil;

public class MergeTest {

	public static void merge(String[] a, String[] b) {
		int N = a.length, M = b.length;
		String[] c = new String[N + M];
		int i = 0, j = 0;
		for (int k = 0; k < c.length; k++) {
			if (i >= N)
				c[k] = b[j++];
			else if (j >= M)
				c[k] = a[i++];
			else if (a[i].compareTo(b[j]) < 0)
				c[k] = a[i++];
			else
				c[k] = b[j++];
		}

		// 读取数据
		for (String s : c) {
			System.out.print(s + " ");
		}
	}

	public static void main(String[] args) {
		String[] a = ReadUtil.getString("sort.txt");
		String[] b = ReadUtil.getString("sort2.txt");
		merge(a, b);
	}

}
