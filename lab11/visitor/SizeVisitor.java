package lab11.visitor;

import lab11.iterator.Iterator;
import lab11.mvc.Set;

public class SizeVisitor implements Visitor {
    private int size;
    public SizeVisitor() {
        size = 0;
    }
    @Override
    public <T> void visit(Set<T> e) {
        Iterator<T> iterator = e.createIterator();
        iterator.first();
        while(!iterator.isDone()) {
            size++;
            iterator.next();
        }
    }
    public int getSize() {
        return size;
    }
}
