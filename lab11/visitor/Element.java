package lab11.visitor;

public interface Element {
    void accept(Visitor v);
}
