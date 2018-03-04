package chapter03.t1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.util.TimerUtil;

/**
 * 单词频率计数用例
 * Created by learnless on 17.11.12.
 */
public class FrequencyCounter {

    public static void main(String[] args) {
        int len = Integer.parseInt(args[0]);  //对短于Len单词过滤
//        In in = new In(args[1]);    //指定文件 tinytale.txt
//        In in = new In(args[2]);    //tabl.txt
        In in = new In(args[3]);    //leipzig1M,100m很大

//        ST<String, Integer> st = new ST<>();
        SequentialSearchST<String, Integer> st = new SequentialSearchST<>();
//        BinarySearchST<String, Integer> st = new BinarySearchST<>();


        TimerUtil timerUtil = new TimerUtil();
        timerUtil.start();
        while (!in.isEmpty()) {
            String word = in.readString();  //读取单词
            if(len > word.length())  continue;   //过滤短单词
            if(!st.contains(word))
                st.put(word, 1);    //符号表未包含指定key，初始化为1
            else
                st.put(word, st.get(word) + 1);
        }

        //找出频率最高的单词
        String max = " ";
        st.put(max, 0);
        for (String s : st.keys())
            if(st.get(s).compareTo(st.get(max)) > 0)
                max = s;

        StdOut.println(max + " " + st.get(max));

        double stop = timerUtil.stop();
        StdOut.println("花费的时间为" +  stop);
    }

}
