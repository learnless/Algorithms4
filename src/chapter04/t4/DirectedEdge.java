package chapter04.t4;

/**
 * 加权有向图边
 * Created by learnless on 18.2.20.
 */
public class DirectedEdge implements Comparable<DirectedEdge> {
    private final int v;
    private final int w;
    private final double weight;

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    /**
     * 起始点
     * @return
     */
    public int from() {
        return v;
    }

    /**
     * 终点
     * @return
     */
    public int to() {
        return w;
    }

    @Override
    public String toString() {
        return String.format("%d -> %d %.2f", v, w, weight);
    }

    @Override
    public int compareTo(DirectedEdge that) {
        if (this.weight() > that.weight())  return 1;
        else if(this.weight() == that.weight()) return 0;
        else return -1;
    }
}
