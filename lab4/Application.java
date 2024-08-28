package lab4;

import gui.AbstractApplication;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;

public class Application extends AbstractApplication {
    private Series series;
    private int n;
    private double firstElement;
    private double delta;

    private JTextField nTextField;
    private JTextField fTextField;
    private JTextField dTextField;

    private JPanel parametersPanel;

    private JButton saveButton;
    JRadioButton linear;
    JRadioButton exponential;

    JTextArea output;
    JLabel elementsLabel;

    public Application() {
        super("Series", 400, 300);
        setLayout(new GridLayout(0, 1, 0, 0));
        initFields();
        setVisible(true);
    }

    private void initFields() {
        initPathFields();
        initTypeButtons();
        initParameters();
        initCreateButton();
        initOutput();
    }

    private void initOutput() {
        JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        elementsLabel = new JLabel("elements:");
        elementsLabel.setVisible(false);
        output = new JTextArea(1, getHeight() / 30);
        output.setVisible(false);
        outputPanel.add(elementsLabel);
        outputPanel.add(output);
        add(outputPanel);
    }

    private void initCreateButton() {
        JButton create = new JButton("create series");
        JPanel createButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        createButtonPanel.add(create);
        add(createButtonPanel);

        create.addActionListener(actionEvent -> {
            try {
                n = Integer.parseInt(nTextField.getText());
                firstElement = Double.parseDouble(fTextField.getText());
                delta = Double.parseDouble(dTextField.getText());
                series = setProgression();
                saveButton.setEnabled(true);
                output.setText(series.toString());
                elementsLabel.setVisible(true);
                output.setVisible(true);
            } catch (NumberFormatException e) {
                //saveButton.setEnabled(false);
                JOptionPane.showMessageDialog(this, "Wrong parameters type!", "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private Series setProgression() {
        if (linear.isSelected()) {
            return new Liner(n, firstElement, delta);
        }
        return new Exponential(n, firstElement, delta);
    }

    private void initParameters() {
        parametersPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nTextField = addParameterTextField("number of elements:");
        fTextField = addParameterTextField("first element:");
        dTextField = addParameterTextField("delta:");
        add(parametersPanel);
    }

    private JTextField addParameterTextField(String s) {
        JTextField field = new JTextField(getWidth() / 90);
        JLabel label = new JLabel(s);
        parametersPanel.add(label);
        parametersPanel.add(field);
        return field;
    }

    private void initPathFields() {
        saveButton = new JButton("save");
        saveButton.setEnabled(false);
        JTextField path = new JTextField(getHeight() / 30);
        JLabel label = new JLabel("path:");
        JPanel pathPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pathPanel.add(label);
        pathPanel.add(path);
        pathPanel.add(saveButton);
        add(pathPanel);
        saveButton.addActionListener(actionEvent -> {
            try {
                series.saveToFile(Paths.get(path.getText()));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "WRONG FILE", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initTypeButtons() {
        linear = new JRadioButton("linear");
        linear.setSelected(true);
        exponential = new JRadioButton("exponential");
        ButtonGroup typeButtonGroup = new ButtonGroup();
        typeButtonGroup.add(linear);
        typeButtonGroup.add(exponential);

        JPanel typeButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        typeButtonsPanel.add(linear);
        typeButtonsPanel.add(exponential);
        add(typeButtonsPanel);
    }
}
