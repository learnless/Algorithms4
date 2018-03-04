package chapter03.t1;

import chapter01.Queue;

import java.util.NoSuchElementException;

/**
 * 基于有序数组二分查找
 * Created by learnless on 17.11.13.
 */
public class BinarySearchST<Key extends Comparable<Key>, Value> {
    private Key[] keys;
    private Value[] vals;
    private int N;

    public BinarySearchST() {
        this(10);
    }

    public BinarySearchST(int n) {
        keys = (Key[]) new Comparable[n];
        vals = (Value[]) new Object[n];
        N = 0;
    }

    public void put(Key key, Value val) {
        if(key == null) throw new IllegalArgumentException();
        if(val == null) {   //val为空，表示删除该元素
            delete(key);
            return;
        }
        /*//查找数组是否存在key
        for (int i = 0; i < N; i++) {
            if(key.compareTo(keys[i]) == 0) {
                //存在更新val
                vals[i] = val;
                return;
            }
        }
        //不存在按顺序插入元素
        if(N == 0) {
            keys[0] = key;
            vals[0] = val;
            N++;
            return;
        }
        int i = N - 1;
        for (; i >= 0; i--) {
            if(key.compareTo(keys[i]) >= 0) {    //从后面进行比较，当key大于等于keys[i]退出
                if(i > 0)   i++;    //当位置不是0时，应将游标向后移动一位
                break;
            }
        }
        //N=N-1,N-1=N-2,...,i+1=新建元素
        if(i < 0)   i = 0;
        for (int j = N; j > i; j--) {
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }*/

        //使用二分查找
//        int i = rank(key, 0, N-1);
        int i = rank(key);
        if(i < N && key.compareTo(keys[i]) == 0) {
            vals[i] = val;
            return;
        }
        if(N == keys.length)    resize(N*2);
        for (int j = N; j > i; j--) {
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    public Value get(Key key) {
        if(key == null) throw new IllegalArgumentException();
        if(N == 0)  return null;
//        int i = 0;
//        for (; i < N; i++) {
//            if(key.compareTo(keys[i]) == 0) { //命中
//                break;
//            }
//        }
//        if(i < N)
//            return vals[i];
        //优化算法，使用二分查找
//        int i = rank(key, 0, N-1);
        int i = rank(key);
        if(i < N && key.compareTo(keys[i]) == 0)
            return vals[i];
        return null;
    }

    public void delete(Key key) {
        if(key == null) throw new IllegalArgumentException();
        if(N == 0)  return;
        //命中则删除
//        for (int i = 0; i < N; i++) {
//            if(key.compareTo(keys[i]) == 0) {
//                delete(i);
//            }
//        }
        //使用二分查找
//        int i = rank(key, 0, N-1);
        int i = rank(key);
        if(i < N && key.compareTo(keys[i]) == 0)
            delete(i);
    }

    public void delete(int index) {
        if(N <= 0)  throw new NullPointerException();
        if(index >= N || index < 0) throw new IndexOutOfBoundsException();
        if(N == keys.length / 4)    resize(N/2);
        //index = index+1,index+1=index+2,...,N-2 = N-1
        for (int i = index; i < N -1; i++) {
            keys[i] = keys[i+1];
            vals[i] = vals[i+1];
        }
        //将最后一个元素置空
        keys[N-1] = null;
        vals[N-1] = null;
        N--;
    }

    public void resize(int size) {
        Key[] k = (Key[]) new Comparable[size];
        Value[] v = (Value[]) new Object[size];
        for (int i = 0; i < N; i++) {
            k[i] = keys[i];
            v[i] = vals[i];
        }
        keys = k;
        vals = v;
    }

    public int size() {
        return N;
    }

    public int size(Key lo, Key hi) {
        if(lo == null || hi == null)    throw new IllegalArgumentException();
        if(lo.compareTo(hi) > 0)    return 0;
        int i = rank(lo);
        int j = rank(hi);
        if(contains(hi))
            return j - i + 1;
        return j - i;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * 小于key的键的数量,使用二分查找，非递归
     * @param key
     * @return
     */
    public int rank(Key key) {
        int lo = 0, hi = N -1;
        while (lo <= hi) {
            int mid = lo + (hi-lo) / 2;
            int comp = key.compareTo(keys[mid]);
            if(comp < 0)
                hi = mid - 1;
            else if(comp > 0)
                lo = mid + 1;
            else
                return mid;
        }

        return lo;
    }

    /**
     * 小于key的键的数量,使用递归二分查找查找
     * @param key
     * @param lo
     * @param hi
     * @return
     */
 /*   public int rank(Key key, int lo, int hi) {
        if(lo > hi)
            return lo;
        int mid = (lo+hi) / 2;
        if(key.compareTo(keys[mid]) > 0) {    //右查找
            return rank(key, mid+1, hi);
        } else if (key.compareTo(keys[mid]) < 0) {    //左查找
            return rank(key, lo, mid-1);
        } else {
            return mid;
        }
    }*/

    public Key min() {
        if(N == 0)  throw new NoSuchElementException();
        return keys[0];
    }

    public Key max() {
        if(N == 0)  throw new NoSuchElementException();
        return keys[N-1];
    }

    public Key select(int k) {
        return keys[k];
    }

    /**
     * 小于等于key的最大键
     * @param key
     * @return
     */
    public Key floor(Key key) {
        if(key == null) throw new IllegalArgumentException();
        if (contains(key)) return key;
        int i = rank(key);
        //排名为0且不包含当前key，表示key比keys任意key都小，返回空
        if (i == 0) {
            return null;
        }
        //不包含当前key，应将排名-1
        return keys[--i];
    }

    /**
     * 大于等于key的最小的键
     * @param key
     * @return
     */
    public Key ceiling(Key key) {
        if(key == null) throw new IllegalArgumentException();
        int i = rank(key);
        if(i == N) //key比keys任一元素都大
            return null;
        return keys[i];
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for (int i = 0; i < N; i++)
            queue.enqueue(keys[i]);
        return queue;
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if(lo == null || hi == null || lo.compareTo(hi) > 0)    throw new IllegalArgumentException();
        Queue<Key> queue = new Queue<>();
        int j = rank(hi);
        for (int i = rank(lo); i < j; i++)
            queue.enqueue(keys[i]);
        if(contains(hi))
            queue.enqueue(hi);
        return queue;
    }

    public void deleteMin() {
        if(N == 0)  throw new NoSuchElementException();
        delete(0);
    }

    public void deleteMax() {
        if(N == 0)  throw new NoSuchElementException();
        delete(N-1);
    }



    public static void main(String[] args) {
        BinarySearchST<String, Integer> st = new BinarySearchST<>();
        st.put("b", 0);
        st.put("a", 1);
        st.put("m", 2);
        st.put("o", 3);
        st.put("y", 4);
        st.put("i", 5);
        st.put("o", 6);

//        st.put("i", null);

//        st.delete(3);

        int a = st.get("a");
        int b = st.get("b");

        String h = st.floor("l");
        String m = st.floor("m");
        String n = st.floor("n");

        for (String s : st.keys()) {
            System.out.println(s + " " + st.get(s));
        }
        System.out.println("--------------------------------");
        for (String s : st.keys("b", "n")) {
            System.out.println(s + " " + st.get(s));
        }

        System.out.println();
    }


}
