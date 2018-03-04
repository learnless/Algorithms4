package chapter02.t4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 使用优先队列多向合并
 * Created by learnless on 17.11.11.
 */
public class Multiway {

    private static void merge(In[] streams) {
        int N = streams.length;
        IndexMinPQ<String> pq = new IndexMinPQ<>(N);

        //初始化队友，与文件出现的顺序有关
        for (int i = 0; i < N; i++)
            if(!streams[i].isEmpty())
                pq.insert(i, streams[i].readString());

        while (!pq.isEmpty()) {
            StdOut.print(pq.minItem() + " ");
            int k = pq.delMin();
            if(!streams[k].isEmpty())
                pq.insert(k, streams[k].readString());
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        int N = args.length;
        In[] streams = new In[N];
        for (int i = 0; i < streams.length; i++)
            streams[i] = new In(args[i]);
        merge(streams);
    }

}
