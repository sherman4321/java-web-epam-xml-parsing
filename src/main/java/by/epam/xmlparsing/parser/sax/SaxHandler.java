package by.epam.xmlparsing.parser.sax;

import by.epam.xmlparsing.bean.*;
import by.epam.xmlparsing.parser.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaxHandler extends DefaultHandler {

    private List<Drug> drugs = new ArrayList<>();
    private static Logger logger = LogManager.getLogger(SaxHandler.class);
    private Drug drug;
    private DrugManufacturer manufacturer;
    private List<DrugManufacturer> manufacturers;
    private Certificate certificate;
    private DrugPackage drugPackage;
    private List<DrugVersion> versions;
    private DrugVersion version;
    private StringBuilder information;

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void characters(char[] ch, int start, int length) {
        information.append(ch, start, length);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        information = new StringBuilder();
        XmlFields tag = XmlFields.valueOf(qName.toUpperCase());
        switch (tag) {
            case DRUGS:
                drugs = new ArrayList<>();
                break;
            case DRUG:
                drug = new Drug();
                drug.setId(Integer.parseInt(attributes.getValue(DrugFields.ID.name().toLowerCase())));
                String group = attributes.getValue(DrugFields.GROUP.name().toLowerCase());
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

    public void endElement(String uri, String localName, String qName) {
        XmlFields drugTag = XmlFields.valueOf(qName.toUpperCase());
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

        XmlFields versionTag = XmlFields.valueOf(qName.toUpperCase());
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

        XmlFields manufacturerTag = XmlFields.valueOf(qName.toUpperCase());
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

        XmlFields certificateTag = XmlFields.valueOf(qName.toUpperCase());
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

        XmlFields drugPackageTag = XmlFields.valueOf(qName.toUpperCase());
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











