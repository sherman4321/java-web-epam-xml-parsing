package by.epam.xmlparsing.parser.dom;

import by.epam.xmlparsing.bean.Drug;
import by.epam.xmlparsing.bean.DrugGroup;
import by.epam.xmlparsing.exception.ParserExcpetion;
import by.epam.xmlparsing.parser.util.DrugFields;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

public class DomParser {
    private static Logger logger = LogManager.getLogger(DomParser.class);
    private List<Drug> drugs = new ArrayList<>();

    public List<Drug> parse(String filePath) throws ParserExcpetion{
        logger.info("Start parsing DOM");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName(DrugFields.DRUG.name());
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Drug drug = new Drug();
                Element element = (Element) node;
                NamedNodeMap attributes = node.getAttributes();
                drug.setGroup(DrugGroup.valueOf(attributes.getNamedItem(DrugFields.GROUP.name()).getNodeValue()));
                drug.setId(Integer.parseInt(attributes.getNamedItem(DrugFields.ID.name()).getNodeValue()));
                drug.setName(getValue(element, DrugFields.NAME.name()));
                drug.setPharm(getValue(element, DrugFields.PHARM.name()));
                this.drugs.add(drug);
            }
        }
        catch (Exception e){
            throw new ParserExcpetion(e);
        }
        return drugs;
    }

    private static String getValue(Element element, String name) {
        NodeList nodeList = element.getElementsByTagName(name);
        Element firstElement = (Element) nodeList.item(0);
        Text text = (Text) firstElement.getFirstChild();
        return text.getNodeValue();
    }
}