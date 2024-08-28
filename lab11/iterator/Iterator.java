package lab11.iterator;

public interface Iterator<T> {
    void first();
    void next();
    boolean isDone();
    T currentItem() throws IndexOutOfBoundsException;
}
