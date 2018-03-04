package chapter01;

import java.util.Iterator;

import org.util.ReadUtil;

import edu.princeton.cs.algs4.StdOut;

/**
 * 定容字符栈抽象数据类型,泛型
 * @author Administrator
 *
 */
public class FixedCapacityStackOfStrings<Item> {
	
	private Item[] a;
	private int N;

	public FixedCapacityStackOfStrings(int n) {
		a = (Item[]) new Object[n];
	}
	
	//入栈,若栈达到数组大小，将其增大一倍
	public void push(Item item) {
		if(a.length == N) {
			resize(a.length * 2);
		}
		a[N++] = item;
	}
	
	//出栈,从栈顶移除,若栈小于数组的1/4,将其缩小一倍
	public Item pop() {
		Item item = a[--N];
		a[N] = null;	//将游离数组回收
		if(N > 0 && N == a.length/4) {
			resize(a.length/2);
		}
		return item;
	}
	
	public int size() {
		return N;
	}
	
	public boolean isEmpty() {
		return a.length == 0;
	}
	
	//调整数组的大小
	public void resize(int size) {
		Item[] temp = (Item[]) new Object[size];
		for (int i = 0; i < N; i++) {
			temp[i] = a[i];
		}
		a = temp;	//数组复制
	}
	
	//迭代器
	public Iterator<Item> iterator() {
		return new ReverseArrayIerator();
	}
	
	//内部类，由接口指向实现类
	private class ReverseArrayIerator implements Iterator<Item> {
		private int i = N;

		@Override
		public boolean hasNext() {
			return i > 0;
		}

		@Override
		public Item next() {
			return a[--i];
		}

		@Override
		public void remove() {
			a[--i] = null;
		}
		
	}

	
	
	public static void main(String[] args) {
		FixedCapacityStackOfStrings stack = new FixedCapacityStackOfStrings(100);
		String[] s = ReadUtil.getString("tobe.txt");
		for (String item : s) {
			//若遇到"-"表示出栈
			if(!item.equals("-")) {
				stack.push(item);
			} else if(!stack.isEmpty()) {
				StdOut.print(stack.pop() + " ");
			}
		}
		StdOut.println("( " + stack.size() + " left on stack )");
		
		//迭代
		Iterator<String> iterator = stack.iterator();
		while(iterator.hasNext()) {
			StdOut.print(iterator.next() + " ");
		}
	}

}
