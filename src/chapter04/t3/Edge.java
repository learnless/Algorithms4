package chapter04.t3;

import java.util.NoSuchElementException;

/**
 * 加权无向图 边工具类
 * Created by learnless on 18.2.16.
 */
public class Edge implements Comparable<Edge> {
    private final double weight;
    private final int v;
    private final int w;

    public Edge(double weight, int v, int w) {
        this.weight = weight;
        this.v = v;
        this.w = w;
    }

    public int eight() {
        return v;
    }

    public int other(int v) {
        if (this.v == v) return this.w;
        else if(this.w == v) return this.v;
        else throw new NoSuchElementException("no find such element");
    }

    public double weight() {
        return weight;
    }

    @Override
    public int compareTo(Edge that) {
        if (this.weight() > that.weight())  return 1;
        else if(this.weight() == that.weight()) return 0;
        else return -1;
    }

    @Override
    public String toString() {
        return v + "-" + w + " weight is " + weight;
    }
}
