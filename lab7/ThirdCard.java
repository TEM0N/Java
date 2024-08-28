package lab7;

import javax.swing.*;

public class ThirdCard extends JPanel {

    ThirdCard() {
        super();
        Box box = new Box(1);
        ButtonGroup buttonGroup = new ButtonGroup();
        ImageIcon[] icons = {
                new ImageIcon("src/lab7/img/grey.png"),
                new ImageIcon("src/lab7/img/green.png"),
                new ImageIcon("src/lab7/img/yellow.png"),
                new ImageIcon("src/lab7/img/red.png")};
        for (int i = 0; i < 4; i++) {
            JRadioButton temp = new JRadioButton(icons[0]);
            temp.setPressedIcon(icons[1]);
            temp.setRolloverIcon(icons[2]);
            temp.setSelectedIcon(icons[3]);
            buttonGroup.add(temp);
            box.add(temp);
        }
        add(box);
    }
}
