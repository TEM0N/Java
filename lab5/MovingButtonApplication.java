package lab5;

import gui.AbstractApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ThreadLocalRandom;

public class MovingButtonApplication extends AbstractApplication {
    private JPanel jPanel;

    public MovingButtonApplication() {
        super("Answer simple question", 400, 600);
        setLayout(new BorderLayout());
        JLabel jLabel = new JLabel("Радует ли вас размер стипендии?", SwingConstants.CENTER);
        jLabel.setForeground(Color.RED);
        add(jLabel, BorderLayout.NORTH);
        initPanel();
        initButtons();
        setVisible(true);
    }

    private void initDefaultSet() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(dimension.width / 2 - 300, dimension.height / 2 - 150, 600, 300);
        setMinimumSize(new Dimension(600, 300));
        getContentPane().setBackground(Color.BLACK);
    }

    private void initPanel() {
        jPanel = new JPanel(null);
        jPanel.setBackground(Color.BLACK);
        add(jPanel);
    }

    private void initButtons() {
        JButton answerButton = new JButton("ДА");
        JButton movingButton = new JButton("НЕТ");
        answerButton.setLocation(getWidth() / 3, getHeight() / 3);
        movingButton.setLocation(2 * getWidth() / 3, getHeight() / 3);
        answerButton.setSize(answerButton.getPreferredSize());
        movingButton.setSize(movingButton.getPreferredSize());
        jPanel.add(answerButton);
        jPanel.add(movingButton);
        answerButton.addActionListener(actionEvent -> JOptionPane.showMessageDialog(this, "Вы умеете экономить!", "Уважаемо", JOptionPane.INFORMATION_MESSAGE));
        movingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                int rndX = ThreadLocalRandom.current().nextInt(0, jPanel.getWidth() - movingButton.getWidth());
                int rndY = ThreadLocalRandom.current().nextInt(0, jPanel.getHeight() - movingButton.getHeight());
                movingButton.setLocation(rndX, rndY);
            }
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JOptionPane.showMessageDialog(MovingButtonApplication.this, "Ну, больше она не станет", "Успокойся", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
