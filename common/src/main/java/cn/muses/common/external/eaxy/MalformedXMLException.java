package cn.muses.common.external.eaxy;

public class MalformedXMLException extends RuntimeException {

    public MalformedXMLException(String message, int lineNumber) {
        super(message + " on line " + lineNumber);
    }

}
