package lab11.mvc;

import lab11.iterator.Iterator;

import javax.swing.*;

public class View<T> {
    private final DefaultListModel<T> listModel;
    private final Set<T> model;
    private final JList<T> list;

    public JList<T> getList() {
        return list;
    }

    public View(Set<T> model) {
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        this.model = model;
        repaintList();
    }

    public void repaintList() {
        listModel.clear();
        Iterator<T> iterator = model.createIterator();
        iterator.first();
        while (!iterator.isDone()) {
            listModel.addElement(iterator.currentItem());
            iterator.next();
        }
    }
}
