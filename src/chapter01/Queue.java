package chapter01;

import java.util.Iterator;

import org.util.ReadUtil;

import edu.princeton.cs.algs4.StdOut;

/**
 * 队列实现，跟堆栈差不多
 * 
 * @author Administrator
 * 
 */
public class Queue<Item> implements Iterable<Item> {
	private Node first;
	private Node last;
	private int N;

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

	// 进队列
	public void enqueue(Item item) {
		Node n = new Node();
		n.item = item;
		n.next = null;
		if (isEmpty()) {	//进队列为空时
			first = last = n;
		} else {
			last.next = n;
			last = n;
		}
		N++;
	}

	// 出队列
	public Item dequeue() {
		if (isEmpty())
			return null;
		Item item = first.item;
		first = first.next;
		if(isEmpty()) {	//只有一个节点，出队列后将末尾设为空，方便进队列操作
			last = null;
		}
		N--;
		return item;
	}

	@Override
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item> {
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
		Queue queue = new Queue();
		String[] s = ReadUtil.getString("tobe.txt");
		for (String item : s) {
			//若遇到"-"表示出栈
			if(!item.equals("-")) {
				queue.enqueue(item);
			} else if(!queue.isEmpty()) {
				StdOut.print(queue.dequeue() + " ");
			}
		}
		StdOut.println("( " + queue.size() + " left on stack )");
		
		//迭代
		Iterator<String> iterator = queue.iterator();
		while(iterator.hasNext()) {
			StdOut.print(iterator.next() + " ");
		}

	}

}
