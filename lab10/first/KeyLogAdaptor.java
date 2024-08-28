package lab10.first;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.EventObject;

public class KeyLogAdaptor implements Observer {
    DefaultListModel<String> listModel;

    public static KeyLogAdaptor newInstance(DefaultListModel<String> listModel) {
        return new KeyLogAdaptor(listModel);
    }
    private KeyLogAdaptor(DefaultListModel<String> listModel) {
        this.listModel = listModel;
    }

    @Override
    public void update(EventObject object) {
        KeyEvent event = (KeyEvent) object;
        listModel.addElement(KeyEvent.getKeyText(event.getKeyCode()));
    }
}
