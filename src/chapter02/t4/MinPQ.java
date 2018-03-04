package chapter02.t4;

import org.util.NoSuchElementException;

import java.util.Comparator;
import java.util.Iterator;

public class MinPQ<Key> implements Iterable<Key> {
	private Key[] pq;	//队列
	private int N;	//个数
	private Comparator<Key> comparator;
	
	/**
	 * 默认构造器
	 */
	public MinPQ() {
		this(1);
	}

	/**
	 * 创建容量为size优先队列
	 */
	public MinPQ(int size) {
		this.pq = (Key[]) new Object[size + 1];
		N = 0;
	}

	public MinPQ(Comparator<Key> comparator) {
		this(1, comparator);
	}

	public MinPQ(int size, Comparator<Key> comparator) {
		this.comparator = comparator;
		this.pq = (Key[]) new Object[size+1];
		N = 0;
	}
	
	/**
	 * a[]创建一个优先队列
	 */
	public MinPQ(Key[] keys) {
		this.N = keys.length;
		this.pq = (Key[]) new Object[N+1];
		//这里可以使用与MaxPQ不同的思路，MaxPQ逐渐插入队列，实际上将元素上浮，效率低，可以用下沉的方式构造
		for (int i = 0; i < N; i++) {
			pq[i+1] = keys[i];
		}

		for(int k = N/2; k >= 1; --k)
			sink(k);

	}
	
	/**
	 * 删除key
	 */
	public void delete(Key key) {
		//TODO
	}
	
	/**
	 * 删除最小元素
	 */
	public Key delMin() {
		if(isEmpty()) throw new NoSuchElementException("队列为空");
		if(N == pq.length/4)	resize(pq.length/2);
		Key min = pq[1];
		exch(1, N--);
		pq[N+1] = null;	//防止元素游离
		sink(1);
		return min;
	}

	/**
	 * 插入key
	 */
	public void insert(Key key) {
		if(N == pq.length -1) resize(2 * pq.length);
		pq[++N] = key;
		swim(N);
	}
	
	/**
	 * 返回最小元素
	 */
	public Key min() {
		if(isEmpty()) throw new NoSuchElementException("队列为空");
		return pq[1];
	}
	
	/**
	 * 动态跳转数组大小
	 * @param size 调整后数组大小
	 */
	private void resize(int size) {
		Key[] t = (Key[]) new Object[size];
		for (int i = 1; i <= N; i++)
			t[i] = pq[i];
		pq = t;
	}
	
	/**
	 * 队列是否为空
	 */
	public boolean isEmpty() {
		return N == 0;
	}
	
	/**
	 * 队列大小
	 */
	public int size() {
		return N;
	}
	
	/**
	 * 下沉
	 */
	public void sink(int k) {
		//获取下沉左右节点最小值
		int j = 0;
		while (2 * k <= N) {
			j = 2 * k;
			if(j < N && greater(j, j+1)) j++;
			if(!greater(k, j))	break;;
			//比k值大则退出
			exch(k, j);
			k = j;
		}
	}

	/**
	 * 上浮
	 * @param args
	 * @param args
	 */
	public void swim(int k) {
		while(k > 1 && greater(k/2, k)) {
			exch(k, k/2);
			k = k/2;
		}
	}
	
	/**
	 * 比较大小
	 */
	private boolean greater(int i, int j) {
		return this.comparator == null?
				((Comparable)this.pq[i]).compareTo(this.pq[j]) > 0
				:
				this.comparator.compare(this.pq[i], this.pq[j]) > 0;
	}
	
	/**
	 *交换值 
	 */
	public void exch(int i, int j) {
		Key t = pq[i];
		pq[i] = pq[j];
		pq[j] = t;
	}

	private boolean isMinHeap() {
		return this.isMinHeap(1);
	}

	private boolean isMinHeap(int k) {
		if(k > N)	return true;
		int left = 2*k;
		int right = 2*k+1;
		return left <= N && this.greater(k, left)?
				false
				:
				(right <= N && this.greater(k, right)?
						false
						:
						this.isMinHeap(left) && this.isMinHeap(right));
	}
	
	/**
	 * 迭代队列 
	 */
	public Iterator<Key> iterator() {
		return new HeapIterator();
	}
	
	private class HeapIterator implements Iterator<Key> {
		private MinPQ<Key> copy;
		
		public HeapIterator() {
			if(comparator == null) {
				copy = new MinPQ<>(N);
			} else {
				copy = new MinPQ<>(N, comparator);
			}
			for (int i = 1; i <= N; i++) {
				copy.insert(pq[i]);
			}
		}

		@Override
		public boolean hasNext() {
			return !copy.isEmpty();
		}

		@Override
		public Key next() {
			if(!this.hasNext())	throw new NoSuchElementException();
			return copy.delMin();
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	public static void main(String[] args) {
		
	}

}
