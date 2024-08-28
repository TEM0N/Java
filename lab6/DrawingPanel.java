package lab6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawingPanel extends JPanel {
    private Point lastPoint;

    private Point previousPoint;
    private Color color;
    BufferedImage image;

    ArrayList<Line> lines = new ArrayList<>();

    public void setColor(Color color) {
        this.color = color;
    }

    public DrawingPanel(Color color) {
        super();
        this.color = color;
        setBackground(Color.BLACK);
        DrawingPanelMouseHandler handler = new DrawingPanelMouseHandler();
        addMouseMotionListener(handler);
        addMouseListener(handler);

    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (image == null) {
            return;
        }
        graphics.drawImage(image, 0, 0, this);
        previousPoint = lastPoint;
    }

    private static class Line {
        private final Point a;
        private final Point b;

        private final Color color;

        Line(Point a, Point b, Color color) {
            this.a = a;
            this.b = b;
            this.color = color;
        }
    }

    private class DrawingPanelMouseHandler extends MouseAdapter implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            lastPoint = mouseEvent.getPoint();
            if (previousPoint == null) {
                previousPoint = lastPoint;
            }
            lines.add(new Line(previousPoint, lastPoint, color));

            if (image == null) {
                image = new BufferedImage(DrawingPanel.this.getWidth(), DrawingPanel.this.getHeight(), BufferedImage.TYPE_INT_RGB);
                DrawingPanel.this.paint(image.getGraphics());
            }
            Graphics g = image.getGraphics();
            for (Line line : lines) {
                g.setColor(line.color);
                g.drawLine(line.a.x, line.a.y, line.b.x, line.b.y);
            }
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            previousPoint = null;
            lastPoint = e.getPoint();
        }
    }

    public void loadImage(BufferedImage bufferedImage) {
        if (image == null) {
            image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            this.paint(image.getGraphics());
        }
        image = bufferedImage;
        image.getGraphics().drawImage(bufferedImage,0,0,this);
        lines.clear();
        lastPoint = null;
        previousPoint = null;
        repaint();
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    }
}