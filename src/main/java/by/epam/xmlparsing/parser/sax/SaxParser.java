package by.epam.xmlparsing.parser.sax;

import by.epam.xmlparsing.bean.Drug;
import by.epam.xmlparsing.exception.ParserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class SaxParser {
    private static Logger logger = LogManager.getLogger(SaxParser.class);

    private List<Drug> drugs;

    public List<Drug> parse(String filePath) throws ParserException {
        logger.info("Start parsing Sax");
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            SaxHandler saxHandler = new SaxHandler();
            saxParser.parse(filePath, saxHandler);
            drugs = saxHandler.getDrugs();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new ParserException(e.getMessage(), e.getCause());
        }
        logger.info("Xml document is parsed successfully by Sax");
        return drugs;
    }
}
