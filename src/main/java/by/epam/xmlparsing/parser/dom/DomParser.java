package by.epam.xmlparsing.parser.dom;

import by.epam.xmlparsing.bean.*;
import by.epam.xmlparsing.exception.ParserException;
import by.epam.xmlparsing.parser.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DomParser {
    private static Logger logger = LogManager.getLogger(DomParser.class);
    private List<Drug> drugs = new ArrayList<>();

    public List<Drug> parse(String filePath) throws ParserException {
        logger.info("Start parsing DOM");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);
            document.getDocumentElement().normalize();
            Element root = document.getDocumentElement();
            NodeList nodeList = root.getElementsByTagName(DrugFields.DRUG.name().toLowerCase());
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Drug drug = new Drug();
                Element element = (Element) node;
                NamedNodeMap attributes = node.getAttributes();
                drug.setGroup(DrugGroup.valueOf(element.getAttribute(DrugFields.GROUP.name().toLowerCase())
                        .toUpperCase()));
                drug.setId(Integer.parseInt(attributes.getNamedItem(DrugFields.ID.name().toLowerCase())
                        .getNodeValue()));
                drug.setName(getValue(element, DrugFields.NAME.name().toLowerCase()));
                drug.setPharm(getValue(element, DrugFields.PHARM.name().toLowerCase()));
                drug.setAnalogs(getValueOfList(element,
                        DrugFields.ANALOGS.name().toLowerCase(),DrugFields.ANALOG.name().toLowerCase()));
                drug.setVersions(parseVersions(element, DrugFields.VERSIONS.name().toLowerCase(),
                        DrugFields.VERSION.name().toLowerCase()));

                this.drugs.add(drug);
            }
        }
        catch (Exception e){
            logger.error("Error during parsing DOM");
            throw new ParserException(e);
        }
        return drugs;
    }

    private static List<DrugVersion> parseVersions(Element element, String nameRoot, String nameElement) {
        logger.info("Start parsing drug versions DOM");
        NodeList nodeList = getNodes(element, nameRoot, nameElement);

        List<DrugVersion> versions = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            DrugVersion version = new DrugVersion();
            Element currentElement = (Element) node;
            version.setForm(DrugForm.valueOf(getValue(currentElement, VersionFields.FORM.name().toLowerCase())
                    .toUpperCase()));
            version.setManufacturers(parseManufacturers(currentElement, VersionFields.MANUFACTURERS.name()
                    .toLowerCase(), VersionFields.MANUFACTURER.name().toLowerCase()));
            versions.add(version);
        }
        return versions;
    }

    private static List<DrugManufacturer> parseManufacturers(Element element, String nameRoot, String nameElement) {
        logger.info("Start parsing drug manufacturers DOM");
        NodeList nodeList = getNodes(element, nameRoot, nameElement);
        List<DrugManufacturer> manufacturers = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            DrugManufacturer manufacturer = new DrugManufacturer();
            Element currentElement = (Element) node;
            manufacturer.setName(getValue(element, ManufacturerFields.NAME.name().toLowerCase()));
            manufacturer.setCertificate(parseCertificate(
                    currentElement, ManufacturerFields.CERTIFICATE.name().toLowerCase()));
            manufacturer.setDrugPackage(parseDrugPackage(
                    currentElement, ManufacturerFields.PACKAGE.name().toLowerCase()));
            manufacturer.setDosage(Integer.parseInt(getValue(
                    currentElement, ManufacturerFields.DOSAGE.name().toLowerCase())));
            manufacturers.add(manufacturer);
        }
        return manufacturers;
    }

    private static Certificate parseCertificate(Element element, String nameRoot){
        logger.info("Start parsing drug certificate DOM");
        NodeList nodeList = element.getElementsByTagName(nameRoot);
        Element firstElement = (Element) nodeList.item(0);
            Node node = nodeList.item(0);
            Certificate certificate = new Certificate();
            Element currentElement = (Element) node;
        certificate.setId(Integer.parseInt(getValue(element, CertificateFields.ID.name().toLowerCase())));
        certificate.setDateOfIssue(LocalDate.parse(getValue(
                element, CertificateFields.DATEOFISSUE.name().toLowerCase())));
        certificate.setRegisterOrganization(getValue(
                element, CertificateFields.REGISTERORGANIZATION.name().toLowerCase()));
        return certificate;
    }

    private static DrugPackage parseDrugPackage(Element element, String nameRoot){
        logger.info("Start parsing drug package DOM");
        NodeList nodeList = element.getElementsByTagName(nameRoot);
        Element firstElement = (Element) nodeList.item(0);
        Node node = nodeList.item(0);
        DrugPackage drugPackage = new DrugPackage();
        Element currentElement = (Element) node;
        drugPackage.setType(DrugPackageType.valueOf(
                getValue(currentElement, DrugPackageFields.TYPE.name().toLowerCase()).toUpperCase()));
        drugPackage.setNumber(Integer.parseInt(getValue(currentElement, DrugPackageFields.NUMBER.name().toLowerCase())));
        drugPackage.setPrice(BigDecimal.valueOf(
                Double.parseDouble(getValue(currentElement, DrugPackageFields.PRICE.name().toLowerCase()))));
        return drugPackage;
    }


    private static NodeList getNodes(Element element, String nameRoot, String nameElement){
        NodeList nodeList = element.getElementsByTagName(nameRoot);
        Element firstElement = (Element) nodeList.item(0);
        nodeList = firstElement.getElementsByTagName(nameElement);
        return nodeList;
    }


    private static String getValue(Element element, String name) {
        NodeList nodeList = element.getElementsByTagName(name);
        Element firstElement = (Element) nodeList.item(0);
        Text text = (Text) firstElement.getFirstChild();
        return text.getNodeValue();
    }

    private static List<String> getValueOfList(Element element, String nameRoot, String nameElement) {
        NodeList nodeList = getNodes(element, nameRoot, nameElement);
        List<String> list = new ArrayList<>();
        for (int i = 0; i< nodeList.getLength(); ++i){
            list.add(nodeList.item(i).getFirstChild().getNodeValue());
        }
        return list;
    }
}