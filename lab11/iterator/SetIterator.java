package lab11.iterator;

import lab11.mvc.Set;

public class SetIterator<T> implements Iterator<T> {
    private final Set<T> set;
    private int current;

    public SetIterator(Set<T> set) {
        this.set = set;
        current = 0;
    }

    @Override
    public void first() {
        current = 0;
    }

    @Override
    public void next() {
        current++;
    }

    @Override
    public boolean isDone() {
        return current >= set.size();
    }

    @Override
    public T currentItem() throws IndexOutOfBoundsException {
        if (isDone()) {
            throw new IndexOutOfBoundsException();
        }
        return set.get(current);
    }
}
