package tiptest;

import edu.princeton.cs.algs4.In;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by learnless on 17.11.12.
 */
public class Test1 {

    @Test
    public void t1() throws Exception {
        int s = 'a';
        for (int i = 0; i < 5; i++) {
            s += 1;
            System.out.println((char)s);
        }
    }

    @Test
    public void t2() throws Exception {
        String s = null;
        String s1 = s == null ? "a" : "b";
        System.out.println(s1);
    }

    @Test
    public void t3() throws Exception {
        String s = null;
        if ("aaa".equals(s)) {
        }
        String[] split = "aaa".split(" ");
        System.out.println();
    }

    @Test
    public void t4() throws Exception {
        In in = new In("movies.txt");
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] split = line.split("/");
            System.out.println();
        }
    }

    @Test
    public void t5() throws Exception {
        In in = new In("routes.txt");
        Set<String> set = new HashSet<>();
        while (in.hasNextLine()) {
            set.addAll(Arrays.asList(in.readLine().split(" ")));
        }
        System.out.println("size = " + set.size());
        set.forEach(System.out::println);
    }

}
