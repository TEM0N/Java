package lab9;

public interface Iterator<T> {
    void first();
    void next();
    boolean isDone();
    T currentItem() throws IndexOutOfBoundsException;
}
