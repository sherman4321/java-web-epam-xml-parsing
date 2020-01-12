package by.epam.xmlparsing.parser;

import by.epam.xmlparsing.bean.*;
import by.epam.xmlparsing.exception.ParserException;
import by.epam.xmlparsing.parser.dom.DomParser;
import by.epam.xmlparsing.parser.sax.SaxParser;
import by.epam.xmlparsing.parser.stax.StaxParser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class DrugParserTest {
    private static final String FILE_PATH = "src/test/resources/drugs.xml";
    private static final DomParser domParser = new DomParser();
    private static final SaxParser saxParser = new SaxParser();
    private static final StaxParser staxParser = new StaxParser();
    private static Drug drug1;
    private static Drug drug2;
    private static List<Drug> drugsSax;
    private static List<Drug> drugsDom;
    private static List<Drug> drugsStax;

    @BeforeClass
    public static void initializeDrugs(){
        drug1 = new Drug(1, "Ibufen", "Bayer", DrugGroup.ANTIBIOTIC);
        drug1.setAnalog("Ibuprofen");
        drug1.setAnalog("Iburofen");
        DrugVersion version1 = new DrugVersion(DrugForm.TABLETS);
        LocalDate date1;
        date1 = LocalDate.parse("2030-09-21");
        Certificate certificate1 = new Certificate(12354, date1,"Minzdrav");
        DrugPackage drugPackage1 = new DrugPackage(DrugPackageType.BOX, 10, BigDecimal.valueOf(19.99));
        DrugManufacturer manufacturer1 = new DrugManufacturer("Bayer",certificate1, drugPackage1,2);
        version1.setManufacturer(manufacturer1);
        drug1.setVersion(version1);

        drug2 = new Drug(2, "ACC", "Belmed", DrugGroup.ANTIBIOTIC);
        drug2.setAnalog("ABB");
        drug2.setAnalog("ADD");
        DrugVersion version2 = new DrugVersion(DrugForm.TABLETS);
        LocalDate date2;
        date2 = LocalDate.parse("2020-01-01");
        Certificate certificate2 = new Certificate(6767, date2,"Minzdrav");
        DrugPackage drugPackage2 = new DrugPackage(DrugPackageType.BOX, 30, BigDecimal.valueOf((9.99)));
        DrugManufacturer manufacturer2 = new DrugManufacturer("Belmed",certificate2, drugPackage2,3);
        version2.setManufacturer(manufacturer2);
        drug2.setVersion(version2);
        try {
            drugsDom = domParser.parse(FILE_PATH);
            drugsSax = saxParser.parse(FILE_PATH);
            drugsStax = staxParser.parse(FILE_PATH);
        }
        catch (ParserException e){
            e.printStackTrace();
        }

    }

    @Test
    public void drugsParsingDomTwoElements(){
        final int sizeExpected = 2;
        Assert.assertEquals(sizeExpected, drugsDom.size());
    }

    @Test
    public void drugsParsingSaxTwoElements(){
        final int sizeExpected = 2;
        Assert.assertEquals(sizeExpected, drugsSax.size());
    }
    @Test
    public void drugsParsingStaxTwoElements(){
        final int sizeExpected = 2;
        Assert.assertEquals(sizeExpected, drugsStax.size());
    }

    @Test
    public void drug1DomTrue(){
        Assert.assertTrue(drug1.equals(drugsDom.get(0)));
    }
    @Test
    public void drug1SaxTrue(){
        Assert.assertTrue(drug1.equals(drugsSax.get(0)));
    }
    @Test
    public void drug1StaxTrue(){
        Assert.assertTrue(drug1.equals(drugsStax.get(0)));
    }

    @Test
    public void drug2DomTrue(){
        Assert.assertTrue(drug2.equals(drugsDom.get(1)));
    }
    @Test
    public void drug2SaxTrue(){
        Assert.assertTrue(drug2.equals(drugsSax.get(1)));
    }
    @Test
    public void drug2StaxTrue(){
        Assert.assertTrue(drug2.equals(drugsStax.get(1)));
    }
}
