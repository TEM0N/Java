package lab10.first;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.EventObject;

public class KeyPressedAdaptor implements Observer {

    private final JLabel keyName;

    public static KeyPressedAdaptor newInstance(JLabel label) {
        return new KeyPressedAdaptor(label);
    }

    private KeyPressedAdaptor(JLabel label) {
        keyName = label;
    }
    @Override
    public void update(EventObject object) {
        KeyEvent event = (KeyEvent) object;
        keyName.setText(KeyEvent.getKeyText(event.getKeyCode()));
    }
}
