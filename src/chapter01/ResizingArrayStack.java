package chapter01;

import java.util.Iterator;

import org.util.ReadUtil;

import edu.princeton.cs.algs4.StdOut;

/**
 * 可动态调整数组栈
 * @author Administrator
 *
 */
public class ResizingArrayStack<Item> implements Iterable<Item> {
	private Item[] a = (Item[]) new Object[1];	//栈元素
	private int N;	//栈大小
	
	public boolean isEmpty() {
		return false;
	}
	
	public int size() {
		return N;
	}
	
	public void push(Item item) {
		if(a.length == N) {
			resize(2 * a.length);
		}
		a[N++] = item;
	}
	
	public Item pop() {
		Item item = a[--N];
		a[N] = null;
		if(N > 0 && N == a.length/4) {
			resize(a.length/2);
		}
		return item;
	}
	
	public void resize(int size) {
		Item[] temp = (Item[]) new Object[size];
		for (int i = 0; i < N; i++) {
			temp[i] = a[i];
		}
		a = temp;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new ReverseArrayIerator();
	}
	
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
		ResizingArrayStack stack = new ResizingArrayStack();
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
