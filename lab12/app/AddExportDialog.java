package lab12.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddExportDialog extends JDialog implements ActionListener {
    private final JButton acceptButton;
    private JLabel nameLabel;
    private JLabel countryLabel;
    private JLabel quantityLabel;
    private JTextField nameText;
    private JTextField countryText;
    private JTextField quantityText;
    Export export;

    public AddExportDialog(JFrame frame, Export export) {
        super(frame, "add export", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(dimension.width / 2 - 40, dimension.height / 2 - 35, 80, 70);
        this.export = export;
        acceptButton = new JButton("ok");
        acceptButton.addActionListener(this);
        initLabels();
        initTextFields();
        initForm();
        setVisible(true);
    }

    private void initForm() {
        setLayout(new GridLayout(4, 2));
        add(nameLabel);
        add(nameText);
        add(countryLabel);
        add(countryText);
        add(quantityLabel);
        add(quantityText);
        add(new JLabel());
        add(acceptButton);
        pack();
    }

    private void initTextFields() {
        nameText = new JTextField(export.getName(), 10);
        countryText = new JTextField(export.getCountry(), 10);
        quantityText = new JTextField(String.valueOf(export.getQuantity()), 10);
    }

    private void initLabels() {
        nameLabel = new JLabel("name");
        countryLabel = new JLabel("country");
        quantityLabel = new JLabel("quantity");
    }

    private void initData() throws NumberFormatException {
        export.setName(nameText.getText());
        export.setCountry(countryText.getText());
        export.setQuantity(Integer.parseInt(quantityText.getText()));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(acceptButton)) {
            try {
                initData();
                if (export.getName().equals("")) {
                    JOptionPane.showMessageDialog(this, "Enter name of export", "WARNING", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (export.getCountry().equals("")) {
                    JOptionPane.showMessageDialog(this, "Enter country", "WARNING", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (export.getQuantity() == 0) {
                    JOptionPane.showMessageDialog(this, "Enter quantity of export", "WARNING", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                setVisible(false);
            }
            catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(this, err, "ERROR", JOptionPane.PLAIN_MESSAGE);
            }
        }

    }
}
