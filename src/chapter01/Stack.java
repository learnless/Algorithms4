package chapter01;

import java.util.Iterator;

import org.util.ReadUtil;

import edu.princeton.cs.algs4.StdOut;

/**
 * 下压堆栈，用链表实现
 * @author Administrator
 *
 */
public class Stack<Item> implements Iterable<Item> {
	private Node first;	//栈顶
	private int N;	//栈大小
	
	private class Node {
		Item item;
		Node next;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	public int size() {
		return N;
	}
	
	//进栈,栈顶替换成新节点
	public void push(Item item) {
		Node n = new Node();
		n.item = item;
		n.next = first;
		first = n;
		N++;
	}
	
	public Item pop() {
		if(isEmpty()) return null;	//空栈
		Item item = first.item;
		first = first.next;
		N--;
		return item;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new ReverseArrayIerator();
	}
	
	private class ReverseArrayIerator implements Iterator<Item> {
		private Node current = first;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			Item item = current.item;
			current = current.next;
			
			return item;
		}

		@Override
		public void remove() {
			current = current.next;
		}
		
	}
	
	public static void main(String[] args) {
		Stack stack = new Stack();
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
