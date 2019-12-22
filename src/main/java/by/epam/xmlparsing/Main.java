package by.epam.xmlparsing;

import by.epam.xmlparsing.exception.ParserExcpetion;
import by.epam.xmlparsing.parser.dom.DomParser;

public class Main {
    public static void main(String[] args) {
        DomParser parser = new DomParser();
        try {
            parser.parse("src/main/resources/drugs");
        }
        catch (ParserExcpetion e){
            e.printStackTrace();
        }
    }
}