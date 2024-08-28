package lab6;

import gui.AbstractApplication;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawingFrame extends AbstractApplication {
    DrawingPanel contentPanel;

    JPanel toolkitPanel;

    public DrawingFrame() {
        super("Drawing Frame", 800, 800);
        contentPanel = new DrawingPanel(Color.ORANGE);
        toolkitPanel = new JPanel();
        toolkitPanel.setBackground(Color.BLACK);
        initColorButtons();
        initSaving();
        initScrollPane();
        setVisible(true);
    }

    private void initDefaultSet() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(dimension.width / 2 - 500, dimension.height / 2 - 300, 1000, 600);
        setMinimumSize(new Dimension(1000, 600));
        setBackground(Color.BLACK);
    }

    private void initColorButtons() {
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton redButton = initColorButton("red", Color.RED);
        JRadioButton greenButton = initColorButton("green", Color.GREEN);
        JRadioButton blueButton = initColorButton("blue", Color.BLUE);

        buttonGroup.add(redButton);
        buttonGroup.add(greenButton);
        buttonGroup.add(blueButton);

        toolkitPanel.add(redButton);
        toolkitPanel.add(greenButton);
        toolkitPanel.add(blueButton);

        add(toolkitPanel, BorderLayout.NORTH);
    }

    private JRadioButton initColorButton(String name, Color color) {
        JRadioButton colorButton = new JRadioButton(name);
        colorButton.addActionListener(actionEvent -> contentPanel.setColor(color));
        return colorButton;
    }

    private void initSaving() {
        JButton saveButton = new JButton("save");
        toolkitPanel.add(saveButton);
        saveButton.addActionListener(actionEvent -> {
            try {
                savePanelContentAsImage();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "ERROR", JOptionPane.PLAIN_MESSAGE);
            }
        });

        JButton openButton = new JButton("open");
        toolkitPanel.add(openButton);
        openButton.addActionListener(actionEvent -> {
            try {
                openImage();
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(this, exception.getLocalizedMessage(), "ERROR", JOptionPane.PLAIN_MESSAGE);
            }
        });
    }

    private void savePanelContentAsImage() throws IOException {
        BufferedImage img = new BufferedImage(contentPanel.getWidth(), contentPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
        contentPanel.paint(img.getGraphics());
        File file = setFile();
        if (file == null) {
            return;
        }
        ImageIO.write(img, "png", file);
    }

    private void openImage() throws IOException {
        File file = getFile();
        if (file == null) {
            return;
        }
        contentPanel.loadImage(ImageIO.read(file));
        //  pack();

    }

    private File setFile() {
        JFileChooser chooser = createFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    private File getFile() {
        JFileChooser chooser = createFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    private JFileChooser createFileChooser() {
        JFileChooser chooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);
        return chooser;
    }

    private void initScrollPane() {
        JScrollPane scrollPane = new JScrollPane(contentPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.createVerticalScrollBar();
        scrollPane.createHorizontalScrollBar();
        contentPanel.setPreferredSize(new Dimension(1200, 800));
        scrollPane.setVisible(true);
        add(scrollPane);
    }


}
