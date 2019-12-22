package by.epam.xmlparsing.exception;

public class ParserExcpetion extends Exception {
    public ParserExcpetion() {
        super();
    }

    public ParserExcpetion(String message) {
        super(message);
    }

    public ParserExcpetion(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserExcpetion(Throwable cause) {
        super(cause);
    }
}
