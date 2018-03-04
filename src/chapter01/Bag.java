package chapter01;

import edu.princeton.cs.algs4.StdOut;
import org.util.ReadUtil;

import java.util.Iterator;

/**
 * 背包实现（也就是集合）
 * 
 * @author Administrator
 * 
 */
public class Bag<Item> implements Iterable<Item> {
	private Node first;
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

	// 集合添加,跟堆栈完全一样
	public void add(Item item) {
		Node n = new Node();
		n.item = item;
		n.next = null;
		if (isEmpty()) {
			first = n;
		} else {
			n.next = first;
			first = n;
		}
		N++;
	}

	public boolean contains(Item item) {
		Iterator<Item> iterator = iterator();
		while (iterator.hasNext()) {
			Item next = iterator.next();
			if (item.equals(item)) {
				return true;
			}
		}
		return false;
	}

	// 删除 (区分情况，对set和list)
	public Item remove() {
		Item item = first.item;
		N--;
		return item;
	}

	//指定项删除
	public Item remove(Item item) {
		//对特殊情况进行处理，空包或者删除节点为首元素
		if (first == null)	return null;
		if (item.equals(first.item)) {
			first = first.next;
			N--;
			return item;
		}

		Node current = first;
		Node prev = first;
		while (current != null) {
			if (item.equals(current.item)) {
				prev.next = current.next;
				N--;
				return item;
			}
			prev = current;
			current = current.next;
		}
		return null;
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
		Bag bag = new Bag();
		String[] s = ReadUtil.getString("tobe.txt");
		for (String item : s) {
			// 若遇到"-"表示出栈
			if (!item.equals("-")) {
				bag.add(item);
			} else if (!bag.isEmpty()) {
				// StdOut.print(bag.remove() + " ");
			}
		}
		StdOut.println("( " + bag.size() + " left on stack )");

		// 迭代
		Iterator<String> iterator = bag.iterator();
		while (iterator.hasNext()) {
			StdOut.print(iterator.next() + " ");
		}
		System.out.println();
		String remove = (String) bag.remove("fuck");
		Iterator<String> iterator1 = bag.iterator();
		while (iterator1.hasNext()) {
			StdOut.print(iterator1.next() + " ");
		}
	}

}
