package by.epam.xmlparsing.parser.stax;

import by.epam.xmlparsing.bean.*;
import by.epam.xmlparsing.exception.ParserException;
import by.epam.xmlparsing.parser.util.DrugFields;
import by.epam.xmlparsing.parser.util.XmlFields;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaxParser {
    private static Logger logger = LogManager.getLogger(StaxParser.class);
    private List<Drug> drugs  = new ArrayList<>();
    private XMLInputFactory xmlInputFactory;
    private XMLStreamReader xmlStreamReader;
    private StringBuilder information;
    private Drug drug;
    private DrugManufacturer manufacturer;
    private List<DrugManufacturer> manufacturers;
    private Certificate certificate;
    private DrugPackage drugPackage;
    private List<DrugVersion> versions;
    private DrugVersion version;

    public List<Drug> parse(String filePath) throws ParserException {
        try {
            logger.info("Start parsing StAX");
            xmlInputFactory = XMLInputFactory.newInstance();
            xmlStreamReader = xmlInputFactory.createXMLStreamReader(new StreamSource(filePath));
            while (xmlStreamReader.hasNext()) {
                int eventType = xmlStreamReader.next();
                switch (eventType) {
                    case XMLStreamReader.START_ELEMENT:
                        information=new StringBuilder();
                        startElement(xmlStreamReader);
                        break;
                    case XMLStreamReader.END_ELEMENT:
                        endElement(xmlStreamReader);
                        break;
                    case XMLStreamReader.CHARACTERS:
                        information.append(xmlStreamReader.getText());
                        break;
                }
            }
        } catch (XMLStreamException e) {
            throw new ParserException(e.getMessage(),e.getCause());
        }
        logger.info("Xml document is parsed successfully by StAX");
        return this.drugs;
    }

    private void startElement(XMLStreamReader xmlStreamReader) {
        XmlFields tag = XmlFields.valueOf(xmlStreamReader.getLocalName().toUpperCase());
        switch (tag) {
            case DRUG:
                drug = new Drug();
                drug.setId(Integer.parseInt(xmlStreamReader.getAttributeValue(0)));
                String group = xmlStreamReader.getAttributeValue(1);
                group = group.toUpperCase();
                drug.setGroup(DrugGroup.valueOf(group));
                break;
            case VERSIONS:
                versions = new ArrayList<>();
                break;
            case VERSION:
                version = new DrugVersion();
                break;
            case MANUFACTURERS:
                manufacturers = new ArrayList<>();
                break;
            case MANUFACTURER:
                manufacturer = new DrugManufacturer();
                break;
            case PACKAGE:
                drugPackage = new DrugPackage();
                break;
            case CERTIFICATE:
                certificate = new Certificate();
                break;
        }
    }

    private void endElement(XMLStreamReader xmlStreamReader) {
        XmlFields drugTag = XmlFields.valueOf(xmlStreamReader.getLocalName().toUpperCase());
        switch (drugTag) {
            case DRUG:
                drugs.add(drug);
                break;
            case NAME:
                if(drug.getName() != null){
                    break;
                }
                drug.setName(information.toString());
                break;
            case PHARM:
                drug.setPharm(information.toString());
                break;
            case ANALOG:
                drug.setAnalog(information.toString());
                break;
            case VERSION:
                versions.add(version);
                break;
            case VERSIONS:
                drug.setVersions(versions);
                break;
        }

        XmlFields versionTag = XmlFields.valueOf(xmlStreamReader.getLocalName().toUpperCase());
        switch (versionTag){
            case MANUFACTURER:
                manufacturers.add(manufacturer);
                break;
            case MANUFACTURERS:
                version.setManufacturers(manufacturers);
                break;
            case FORM:
                version.setForm(DrugForm.valueOf(information.toString().toUpperCase()));
                break;
        }

        XmlFields manufacturerTag = XmlFields.valueOf(xmlStreamReader.getLocalName().toUpperCase());
        switch (manufacturerTag){
            case NAME:
                if(manufacturer == null || manufacturer.getName() != null){
                    break;
                }
                manufacturer.setName(information.toString());
            case CERTIFICATE:
                manufacturer.setCertificate(certificate);
                break;
            case PACKAGE:
                manufacturer.setDrugPackage(drugPackage);
                break;
            case DOSAGE:
                manufacturer.setDosage(Integer.parseInt(information.toString()));
                break;
        }

        XmlFields certificateTag = XmlFields.valueOf(xmlStreamReader.getLocalName().toUpperCase());
        switch (certificateTag){
            case ID:
                certificate.setId(Integer.parseInt(information.toString()));
                break;
            case DATEOFISSUE:
                certificate.setDateOfIssue(LocalDate.parse(information));
                break;
            case REGISTERORGANIZATION:
                certificate.setRegisterOrganization(information.toString());
                break;
        }

        XmlFields drugPackageTag = XmlFields.valueOf(xmlStreamReader.getLocalName().toUpperCase());
        switch (drugPackageTag){
            case TYPE:
                drugPackage.setType(DrugPackageType.valueOf(information.toString().toUpperCase()));
                break;
            case NUMBER:
                drugPackage.setNumber(Integer.parseInt(information.toString()));
                break;
            case PRICE:
                drugPackage.setPrice(BigDecimal.valueOf(Double.parseDouble(information.toString())));
                break;
        }

    }
}
