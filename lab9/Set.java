package lab9;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Set<T> implements IterableSet<T> {
    private final List<T> data;
    public static <T> Set<T> create() {
        return new Set<>();
    }

    public static <T> Set<T> copy(Set<T> set) {
        return new Set<>(set);
    }

    private Set(Set<T> set) {
        data = new ArrayList<>();
        data.addAll(set.data);
    }

    private Set() {
        data = new ArrayList<>();
    }

    @Override
    public Iterator<T> createIterator() {
        return new SetIterator<>(this);
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public void clear() {
        data.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Set<?> set = (Set<?>) o;
        for (Object element : set.data) {
            if (!data.contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return "Set{" +
                "data=" + data +
                '}';
    }

    public ListModel<T> getListModel() {
        DefaultListModel<T> model = new DefaultListModel<>();
        for (T element : data) {
            model.addElement(element);
        }
        return model;
    }

    public boolean add(T element) {
        if (!data.contains(element)) {
            data.add(element);
            return true;
        }
        return false;
    }

    public boolean remove(T element) {
        return data.remove(element);
    }

    public boolean addAll(Set<T> set) {
        boolean flag = true;
        for (T element : set.data) {
            if (!add(element)) {
                flag = false;
            }
        }
        return flag;
    }

    public Set<T> union(Set<T> set) {
        Set<T> unionSet = new Set<>(this);
        Iterator<T> iterator = set.createIterator();
        iterator.first();
        while (!iterator.isDone()) {
            unionSet.add(iterator.currentItem());
            iterator.next();
        }
        return unionSet;
    }

    public Set<T> intersection(Set<T> set) {
        Set<T> intersectionSet = new Set<>();
        Iterator<T> iterator = set.createIterator();
        iterator.first();
        while (!iterator.isDone()) {
            T element = iterator.currentItem();
            if (data.contains(element)) {
                intersectionSet.add(element);
            }
            iterator.next();
        }
        return intersectionSet;
    }

    public Set<T> difference(Set<T> set) {
        Set<T> differenceSet = new Set<>();
        Iterator<T> iterator = createIterator();
        iterator.first();
        while (!iterator.isDone()) {
            T element = iterator.currentItem();
            if (!set.data.contains(element)) {
                differenceSet.add(element);
            }
            iterator.next();
        }
        return differenceSet;
    }

    public T get(int index) {
        return data.get(index);
    }
}