package lab12.app;

import gui.AbstractApplication;
import lab12.xml.DOMExportParser;
import lab12.xml.SAXExportParser;
import lab12.xml.XMLCreator;
import lab12.strategy.FilterByNameStrategyWithLoop;
import lab12.strategy.FilterByNameStrategyWithStream;
import lab12.strategy.FilterExportByNameStrategy;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.TreeMap;

public class Application extends AbstractApplication implements ActionListener {
    private JMenuItem menuItemSAX;
    private JMenuItem menuItemDOM;
    private JMenuItem save;
    private JMenu open;

    private JButton edit;
    private JButton add;
    private JButton delete;
    private List list;
    private List sortedWithStreamList;
    private List sortedWithLoopList;

    private JTextField nameFilter;
    private JLabel totalStreamQuantity;
    private JLabel totalLoopQuantity;

    private java.util.List<Export> collection;
    private TreeMap<String, Integer> sMap;
    private TreeMap<String, Integer> lMap;

    private FilterExportByNameStrategy sStrategy;
    private FilterExportByNameStrategy lStrategy;

    public static Application create() {
        return new Application();
    }

    private Application() {
        super("Lab 12", 1900, 400);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        initStrategy();
        menuBarInit();
        addNameFilter();
        listsInit();
        buttonsInit();
        changeFont(this, new Font("Courier", Font.PLAIN, 18));
        changeBackground(this, Color.BLACK);
        setVisible(true);
    }

    private void changeFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                changeFont(child, font);
            }
        }
    }

    private void changeBackground(Component component, Color color) {
        component.setBackground(color);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                changeBackground(child, color);
            }
        }
    }

    private void initStrategy() {
        lStrategy = new FilterByNameStrategyWithLoop();
        sStrategy = new FilterByNameStrategyWithStream();
    }

    private void addNameFilter() {
        Box box = new Box(2);
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.RED);

        nameFilterInit();

        JLabel totalLabel = new JLabel("Total:");
        totalLabel.setForeground(Color.RED);

        totalLoopQuantity = initTotalQuantityLabel();
        totalStreamQuantity = initTotalQuantityLabel();

        JPanel filterPanel = new JPanel();
        filterPanel.add(nameLabel);
        filterPanel.add(nameFilter);
        JPanel totalQuantityPanel = new JPanel();
        totalQuantityPanel.add(totalLabel);
        totalQuantityPanel.add(totalStreamQuantity);
        totalQuantityPanel.add(totalLoopQuantity);

        box.add(filterPanel);
        box.add(totalQuantityPanel);
        add(box);
    }

    private JLabel initTotalQuantityLabel() {
        JLabel label = new JLabel("0");
        label.setForeground(Color.YELLOW);
        return label;
    }

    private void nameFilterInit() {
        nameFilter = new JTextField(15);
        nameFilter.setForeground(Color.GREEN);
        nameFilter.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                showDataFromFilteredCollection(sStrategy, sortedWithStreamList, sMap, totalStreamQuantity);
                showDataFromFilteredCollection(lStrategy, sortedWithLoopList, lMap, totalLoopQuantity);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                showDataFromFilteredCollection(sStrategy, sortedWithStreamList, sMap, totalStreamQuantity);
                showDataFromFilteredCollection(lStrategy, sortedWithLoopList, lMap, totalLoopQuantity);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                showDataFromFilteredCollection(sStrategy, sortedWithStreamList, sMap, totalStreamQuantity);
                showDataFromFilteredCollection(lStrategy, sortedWithLoopList, lMap, totalLoopQuantity);
            }
        });
    }

    private void menuBarInit() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.setForeground(Color.CYAN);
        openMenuInit();
        save = new JMenuItem("save");
        save.addActionListener(this);
        menu.add(save);
        menu.add(open);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void openMenuInit() {
        open = new JMenu("open with");
        menuItemSAX = new JMenuItem("SAX");
        menuItemDOM = new JMenuItem("DOM");
        menuItemDOM.addActionListener(this);
        menuItemSAX.addActionListener(this);
        open.add(menuItemDOM);
        open.add(menuItemSAX);
    }

    protected void listsInit() {
        collection = new ArrayList<>();
        sMap = new TreeMap<>();
        lMap = new TreeMap<>();
        list = new List();
        sortedWithStreamList = new List();
        list.setForeground(Color.WHITE);
        sortedWithStreamList.setForeground(Color.WHITE);

        sortedWithLoopList = new List();
        sortedWithLoopList.setForeground(Color.WHITE);

        Box box = new Box(2);
        box.add(list);
        box.add(sortedWithStreamList);
        box.add(sortedWithLoopList);
        add(box);
    }

    private void buttonsInit() {
        edit = new JButton("Edit");
        add = new JButton("Add");
        delete = new JButton("Delete");
        Box box = new Box(2);
        addButtonToBox(edit, box);
        addButtonToBox(add, box);
        addButtonToBox(delete, box);
        add(box);
        pack();
    }

    private void addButtonToBox(JButton button, Box box) {
        button.addActionListener(this);
        JPanel panel = new JPanel();
        button.setForeground(Color.ORANGE);
        panel.add(button);
        box.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(menuItemSAX)) {
            Path path = getPathFromFileChooser("open");
            if (path == null) {
                return;
            }
            collection.clear();
            saxParse(path);
        } else if (actionEvent.getSource().equals(menuItemDOM)) {
            Path path = getPathFromFileChooser("open");
            if (path == null) {
                return;
            }
            collection.clear();
            domParse(path);
        } else if (actionEvent.getSource().equals(save)) {
            try {
                Path path = getPathFromFileChooser("save");
                XMLCreator creator = XMLCreator.create();
                creator.parseList(collection);
                if (path == null) {
                    return;
                }
                creator.transformation(path);
            } catch (ParserConfigurationException | TransformerException e) {
                JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else if (actionEvent.getSource().equals(add)) {
            Export export = new Export();
            new AddExportDialog(this, export);
            if (!export.equals(new Export())) {
                collection.add(export);
            }
        } else if (actionEvent.getSource().equals(edit)) {
            int index = list.getSelectedIndex();
            if (index != -1) {
                new AddExportDialog(this, collection.get(index));
            }
        } else if (actionEvent.getSource().equals(delete)) {
            int index = list.getSelectedIndex();
            if (index != -1) {
                collection.remove(index);
            }
        }
        showDataFromCollection();
        showDataFromFilteredCollection(sStrategy, sortedWithStreamList, sMap, totalStreamQuantity);
        showDataFromFilteredCollection(lStrategy, sortedWithLoopList, lMap, totalLoopQuantity);
    }

    private void domParse(Path path) {
        try {
            DOMExportParser parser = DOMExportParser.newInstance(path);
            collection.addAll(parser.parse());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saxParse(Path path){
        try {
            SAXExportParser parser = SAXExportParser.newInstance(path);
            collection.addAll(parser.getExportList());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showDataFromCollection() {
        if (collection != null) {
            list.removeAll();
            for (Export export : collection) {
                list.add(export.toString());
            }
        } else {
            JOptionPane.showMessageDialog(this, "No elements", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showDataFromFilteredCollection(FilterExportByNameStrategy strategy, List list, TreeMap<String, Integer> map, JLabel label) {
        list.removeAll();
        ArrayList<Export> sorted = (ArrayList<Export>) strategy.filterByName(collection, nameFilter.getText());
        label.setText(strategy.calculateTotalQuantity(sorted));
        map.clear();
        for (Export export : sorted) {
            if (map.containsKey(export.getCountry())) {
                map.put(export.getCountry(), map.get(export.getCountry()) + export.getQuantity());
            } else {
                map.put(export.getCountry(), export.getQuantity());
            }
        }
        for (String s : map.descendingKeySet()) {
            list.add(s + " : " + map.get(s));
        }
    }

    private Path getPathFromFileChooser(String s) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("xml", "xml");
        fileChooser.setFileFilter(filter);
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        if (fileChooser.showDialog(this, s) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().toPath();
        }
        return null;
    }
}
