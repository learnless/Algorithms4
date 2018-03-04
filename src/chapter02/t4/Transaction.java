package chapter02.t4;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/**
 * 交易类
 * @author Administrator
 *
 */
public class Transaction implements Comparable<Transaction> {
	private final String who;	//人物
	private final Date when;	//地点
	private final double amount;	//交易金额
	
	public Transaction(String who, Date when, double amount) {
		this.who = who;
		this.when = when;
		this.amount = amount;
	}
	
	public Transaction(String transaction) {
		//分割字符串
		String[] a = transaction.split("\\s+");
		who = a[0];
		when = new Date(a[1]);
		amount = Double.parseDouble(a[2]);
	}

	@Override
	public int compareTo(Transaction that) {
		return Double.compare(this.amount, that.amount);
	}
	
	@Override
	public String toString() {
		return String.format("%-10s %10s %8.2f", who, when, amount);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((when == null) ? 0 : when.hashCode());
		result = prime * result + ((who == null) ? 0 : who.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		return (this.amount == other.amount) && (this.who.equals(other.who))
                && (this.when.equals(other.when));
	}

	public String who() {
		return this.who;
	}
	
	public Date when() {
		return this.when;
	}
	
	public double amount() {
		return this.amount;
	}

	/**
	 * 自定义比较
	 *
	 */
	public static class WhoOrder implements Comparator<Transaction> {
		@Override
		public int compare(Transaction v, Transaction w) {
			return v.who.compareTo(w.who);
		}
	}
	
	public static class WhenOrder implements Comparator<Transaction> {
		@Override
		public int compare(Transaction v, Transaction w) {
			return v.when.compareTo(w.when);
		}
	}
	
	public static class HowMuchOrder implements Comparator<Transaction> {
		@Override
		public int compare(Transaction v, Transaction w) {
			return Double.compare(v.amount, w.amount);
		}
	}
	
	public static void main(String[] args) {
        Transaction[] a = new Transaction[4];
        a[0] = new Transaction("Turing   6/17/1990  644.08");
        a[1] = new Transaction("Tarjan   3/26/2002 4121.85");
        a[2] = new Transaction("Knuth    6/14/1999  288.34");
        a[3] = new Transaction("Dijkstra 8/22/2007 2678.40");

        StdOut.println("Unsorted");
        for (int i = 0; i < a.length; i++)
            StdOut.println(a[i]);
        StdOut.println();
        
        StdOut.println("Sort by date");
        Arrays.sort(a, new Transaction.WhenOrder());
        for (int i = 0; i < a.length; i++)
            StdOut.println(a[i]);
        StdOut.println();

        StdOut.println("Sort by customer");
        Arrays.sort(a, new Transaction.WhoOrder());
        for (int i = 0; i < a.length; i++)
            StdOut.println(a[i]);
        StdOut.println();

        StdOut.println("Sort by amount");
        Arrays.sort(a, new Transaction.HowMuchOrder());
        for (int i = 0; i < a.length; i++)
            StdOut.println(a[i]);
        StdOut.println();
    }
	
}
