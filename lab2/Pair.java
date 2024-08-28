package lab2;

public class Pair<T, V> {
    private T first;
    private V second;
    public Pair() { first = null; second = null; }
    public Pair(T first, V second) {
        this.first = first;
        this.second = second;
    }
    public T getFirst() { return first; }
    public V getSecond() { return second; }
    public void setFirst(T newValue) { first = newValue; }
    public void setSecond(V newValue) {second = newValue; }
}
