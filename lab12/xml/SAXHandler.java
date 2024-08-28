package lab12.xml;

import lab12.app.Export;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SAXHandler extends DefaultHandler {
    public static final String EXPORTS_TAG = "Exports";
    public static final String EXPORT_TAG = "Export";
    public static final String NAME_TAG = "name";
    public static final String COUNTRY_TAG = "country";
    public static final String QUANTITY_TAG = "quantity";

    public List<Export> getExportList() {
        return exportList;
    }

    private List<Export> exportList;

    private Export currentExport;
    private String currentElement;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentElement = qName;
        switch (currentElement) {
            case EXPORTS_TAG:
                exportList = new ArrayList<>();
                break;

            case EXPORT_TAG:
                currentExport = new Export();
                break;

        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String text = new String(ch, start, length);
        if (text.contains("<") || currentElement == null) {
            return;
        }
        switch (currentElement) {
            case NAME_TAG:
                currentExport.setName(text);
                break;

            case COUNTRY_TAG:
                currentExport.setCountry(text);
                break;

            case QUANTITY_TAG:
                currentExport.setQuantity(Integer.parseInt(text));
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals(EXPORT_TAG)) {
            exportList.add(currentExport);
            currentExport = null;
        }
        currentElement = null;
    }
}
