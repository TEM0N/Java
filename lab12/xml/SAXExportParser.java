package lab12.xml;

import lab12.app.Export;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class SAXExportParser {
    private final SAXHandler handler;
    public static SAXExportParser newInstance(Path path) throws ParserConfigurationException, SAXException, IOException {
        return new SAXExportParser(path);
    }
    private SAXExportParser(Path path) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        handler = new SAXHandler();
        reader.setContentHandler(handler);
        reader.parse(path.toString());
    }

    public List<Export> getExportList() {
        return handler.getExportList();
    }
}
