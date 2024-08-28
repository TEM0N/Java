package lab11.visitor;

import lab11.mvc.Set;

public interface Visitor {
    <T> void visit(Set<T> e);
}
