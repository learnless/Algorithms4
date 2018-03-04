package chapter02.t4;

import chapter01.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * @作者: learnless
 * @描述: 最先队列实例，按照交易金额进行排序
 * @时间: 17.11.12
 */
public class TopM {

    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        MinPQ<Transaction> pq = new MinPQ<Transaction>(m+1);
        In stream = new In(args[1]);
        while (!stream.isEmpty()) {
            String line = stream.readLine();
            Transaction transaction = new Transaction(line);
            pq.insert(transaction);
            //超出指定队列大小
            if (pq.size() > m) {
                pq.delMin();
            }
        }
        //将优先最小队列按金额递增输出
        Stack<Transaction> stack = new Stack<>();
        while (!pq.isEmpty())
            stack.push(pq.delMin());
        for (Transaction t : stack)
            StdOut.println(t);
    }

}
