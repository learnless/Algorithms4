package chapter01.t5;

import org.util.ReadUtil;

import edu.princeton.cs.algs4.StdOut;

/**
 * 先简单用数组来实现
 * 
 * @author Administrator
 * 
 */
public class UF {
	private int[] id; // 分量id
	private int N; // 数组大小

	public UF(int N) {
		this.N = N;
		id = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
		}
	}

	// 获取数组大小
	public int size() {
		return N;
	}

	// 连接操作
	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);
		if (pRoot == qRoot)
			return;

		for (int i = 0; i < id.length; i++) {
			if (id[i] == pRoot)
				id[i] = qRoot;
		}
		N--;
	}

	// 查找
	public int find(int p) {
		return id[p];
	}

	// 是否连接
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	public static void main(String[] args) {
		int[] a = ReadUtil.getInt("tinyUF.txt");
		// UF uf = new UF(a[0]);
//		QuickUF uf = new QuickUF(a[0]);
		WeightedQuickUF uf = new WeightedQuickUF(a[0]);
		for (int i = 1; i < a.length; i += 2) {
			int p = a[i];
			int q = a[i + 1];
			if (uf.connected(p, q))
				continue;
			uf.union(p, q);
			StdOut.println(p + " " + q);
		}
		StdOut.println(uf.size() + " components");
	}

}

/**
 * 快速查找,只需修改UF连接时分量指向即可
 */
class QuickUF {
	private int[] id;
	private int N;

	public QuickUF(int N) {
		this.N = N;
		id = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
		}
	}

	public int size() {
		return N;
	}

	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);
		if (pRoot == qRoot)
			return;
		id[pRoot] = qRoot;
		N--;
	}

	// 查找时只查找根节点
	private int find(int p) {
		while (p != id[p])
			p = id[p];
		return p;
	}

	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

}

// 加权的QuickUF
class WeightedQuickUF {
	private int[] id;
	private int[] sz; // 加权
	private int N;

	public WeightedQuickUF(int N) {
		this.N = N;
		id = new int[N];
		sz = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
			sz[i] = 1;
		}
	}

	public int size() {
		return N;
	}

	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);
		if (pRoot == qRoot)
			return;
		if (sz[pRoot] > sz[qRoot]) {
			id[qRoot] = pRoot;
			sz[pRoot] += sz[qRoot];
		} else {
			id[pRoot] = qRoot;
			sz[qRoot] += sz[pRoot];
		}
		N--;
	}

	// 查找时只查找根节点
	private int find(int p) {
		while (p != id[p])
			p = id[p];
		return p;
	}

	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

}
