package chapter05.t1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Alphabet类的应用，统计字母的个数
 * Created by learnless on 18.2.24.
 */
public class Count {
    public static void main(String[] args) {
        Alphabet alphabet = new Alphabet("ABCDR");
        final int R = alphabet.radix();
        int[] count = new int[R];

        In in = new In("abra.txt");
        String s = in.readAll();
        int N = s.length();
        for (int i = 0; i < N; i++) {
            if (alphabet.contains(s.charAt(i)))
                count[alphabet.toIndex(s.charAt(i))]++;
        }


        for (int c = 0; c < R; c++)
            StdOut.println(alphabet.toChar(c) + " " + count[c]);
    }
}
