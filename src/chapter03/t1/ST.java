package chapter03.t1;

import java.util.*;

/**
 * 有序泛型符号表
 * Created by learnless on 17.11.12.
 */
public class ST<Key extends Comparable<Key>, Value> implements Iterable<Key> {

    private TreeMap<Key, Value> st;

    public ST() {
        //TODO 自己实现TreeMap，不使用jdk自带
        st = new TreeMap<>();
    }

    /**
     * key-value存储
     * @param key
     * @param value
     */
    public void put(Key key, Value value) {
        if(key == null) throw new IllegalArgumentException("key不能为空");
        if(value == null)
            st.remove(key);
        else
            st.put(key, value);
    }

    /**
     * 由key获取value
     * @param key
     * @return
     */
    public Value get(Key key) {
        if(key == null) throw new IllegalArgumentException("key不能为空");
        return st.get(key);
    }

    public Value delete(Key key) {
        if(key == null) throw new IllegalArgumentException("key不能为空");
        return st.remove(key);
    }

    public boolean contains(Key key) {
        if(key == null) throw new IllegalArgumentException("key不能为空");
        return st.containsKey(key);
    }

    public boolean isEmpty() {
        return st.isEmpty();
    }

    public int size() {
        return st.size();
    }

    /**
     * [lo...hi]之间键的数量
     * @param lo
     * @param hi
     * @return
     */
    public int size(Key lo, Key hi) {
        if(lo.compareTo(hi) > 0)    throw new IllegalArgumentException();
        //1.计算低于lo排名
        int l = rank(lo);
        //2.计算小于等于hi排名
        int h = rankeq(hi);
        return h - 1;
    }

    /**
     * 所有键的集合
     * @return
     */
    public Iterable<Key> keys() {
        return st.keySet();
    }

    /**
     * 在[lo...hi]之间键的集合
     * @param lo
     * @param hi
     * @return
     */
    public Iterable keys(Key lo, Key hi) {
        if(lo.compareTo(hi) > 0)    throw new IllegalArgumentException();
        List<Key> list = new ArrayList<>();
        Set<Key> keys = st.keySet();
        Iterator<Key> iterator = keys.iterator();
        int i = 0;
        for (; iterator.hasNext();) {
            Key key = iterator.next();
            if(key.compareTo(lo) >= 0 && key.compareTo(hi) <= 0)    //符合指定区间，添加到list集合中
                list.add(key);
            else if(key.compareTo(hi) > 0)  //超出最大key，退出
                break;
        }
        return list;
    }

    public Iterable values() {
        return st.values();
    }

    @Override
    public Iterator<Key> iterator() {
        return st.keySet().iterator();
    }

    /**
     * 最小的key
     * @return
     */
    public Key min() {
        if(isEmpty())   throw new NoSuchElementException("符号表为空");
        return st.firstKey();
    }

    /**
     * 最大的key
     * @return
     */
    public Key max() {
        if(isEmpty())   throw new NoSuchElementException("符号表为空");
        return st.lastKey();
    }

    /**
     * 小于等于key最大的key
     * @return
     */
    public Key floor(Key key) {
        if(key == null) throw new IllegalArgumentException("key不能为空");
        Key k = st.floorKey(key);
        if(k == null)   throw new NoSuchElementException("所有的key都大于"+key);
        return k;
    }

    /**
     * 大于等于key最小的key
     * @param key
     * @return
     */
    public Key ceiling(Key key) {
        if(key == null) throw new IllegalArgumentException("key不能为空");
        Key k = st.ceilingKey(key);
        if(k == null)   throw new NoSuchElementException("所有的key都小于"+key);
        return k;
    }

    /**
     * 小于key的键的数量
     * @param key
     * @return
     */
    public int rank(Key key) {
        Set<Key> keys = st.keySet();
        int i = 0;
        Iterator<Key> iterator = keys.iterator();
        for (; iterator.hasNext(); i++) {
            Key k = iterator.next();
            if(k.compareTo(key) >= 0) {  //大于等于指定的key，直接退出
                break;
            }
        }
        return i;
    }

    public int rankeq(Key key) {
        Set<Key> keys = st.keySet();
        int i = 0;
        Iterator<Key> iterator = keys.iterator();
        for (; iterator.hasNext(); i++) {
            Key k = iterator.next();
            if(k.compareTo(key) > 0) {  //大于等于指定的key，直接退出
                break;
            }
        }
        return i;
    }


    /**
     * 排名为k的key
     * @param k
     * @return
     */
    public Key select(int k) {
        if(k < 0 || k > st.size())  throw new IndexOutOfBoundsException();
        Set<Key> keys = st.keySet();
        Iterator<Key> iterator = keys.iterator();
        Key key = null;
        for (int i = 0; iterator.hasNext(); i++) {
            key = iterator.next();
            if(i == k)
                break;
        }
        return key;
    }

    /**
     * 删除最小的键
     */
    public void delMin() {
        delete(min());
    }

    /**
     * 删除最大的键
     * @return
     */
    public void delMax() {
        delete(max());
    }


    public static void main(String[] args) {
        ST<String, Integer> st = new ST<>();
        st.put("b", 0);
        st.put("a", 1);
        st.put("m", 2);
        st.put("o", 3);
        st.put("y", 4);
        st.put("i", 5);
        st.put("o", 6);
        Iterable values = st.values();
//        st.put("y", null);
//        st.select(3);
        int m = st.rank("o");
        String select = st.select(2);
        String select1 = st.select(5);
        int o = st.rankeq("o");

        int size = st.size("b", "n");
        boolean c = st.contains("b");
        Iterable keys = st.keys("a", "o");
        String h = st.floor("h");
        String j = st.ceiling("j");
        st.delMin();
        st.delMax();
        for (String s : st.keys()) {
            System.out.println(s);
        }

        System.out.println(11);
    }
}
