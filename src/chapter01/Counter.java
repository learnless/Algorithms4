package chapter01;

/**
 * 计数器
 * @author Administrator
 *
 */
public class Counter {
	private String id;
	private int count;

	public Counter(String id) {
		this.id = id;
		this.count = 0;
	}
	
	public void increment() {
		count++;
	}
	
	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "Counter [id=" + id + ", count=" + count + "]";
	}
	
}
