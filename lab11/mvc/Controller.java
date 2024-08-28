package lab11.mvc;


import lab11.Export;
import lab11.visitor.SizeVisitor;

import javax.swing.*;

public class Controller {
    private final Set<Export> leftSet;
    private final Set<Export> rightSet;
    private final Set<Export> resultSet;
    private final View<Export> leftView;
    private final View<Export> rightView;
    private final View<Export> resultView;
    public Controller(Set<Export> leftSet, Set<Export> rightSet, Set<Export> resultSet, View<Export> leftView, View<Export> rightView, View<Export> resultView) {
        this.leftSet = leftSet;
        this.rightSet = rightSet;
        this.resultSet = resultSet;
        this.leftView = leftView;
        this.rightView = rightView;
        this.resultView = resultView;
    }

    public void union() {
        resultSet.clear();
        resultSet.addAll(leftSet.union(rightSet));
        resultView.repaintList();
    }
    public void intersection() {
        resultSet.clear();
        resultSet.addAll(leftSet.intersection(rightSet));
        resultView.repaintList();
    }

    public void difference() {
        resultSet.clear();
        resultSet.addAll(leftSet.difference(rightSet));
        resultView.repaintList();
    }
    public void moveRight() {
        rightSet.addAll(leftSet);
        rightView.repaintList();
    }
    public void moveLeft() {
        leftSet.addAll(rightSet);
        leftView.repaintList();
    }

    public void add(View<Export> view, Set<Export> set, Export export) {
        set.add(export);
        view.repaintList();
    }
    public void remove(View<Export> view, Set<Export> set) {
        int[] ints = view.getList().getSelectedIndices();
        for (int i = 0; i < ints.length; i++) {
            set.remove(set.get(i));
        }
        view.repaintList();
    }
    public void clear(View<Export> view, Set<Export> set) {
        set.clear();
        view.repaintList();
    }

    public void equals(JFrame frame) {
        String s = leftSet.equals(rightSet) ? "The sets are equals" : "The sets are different";
        JOptionPane.showMessageDialog(frame, s, "EQUALS", JOptionPane.INFORMATION_MESSAGE);
    }

    public void size(JFrame frame, Set<Export> set) {
        SizeVisitor sizeVisitor = new SizeVisitor();
        set.accept(sizeVisitor);
        String s = "Set contains " + sizeVisitor.getSize() + " element";
        if (sizeVisitor.getSize() != 1) {
            s += "s";
        }
        JOptionPane.showMessageDialog(frame, s, "SIZE", JOptionPane.INFORMATION_MESSAGE);
    }
}
