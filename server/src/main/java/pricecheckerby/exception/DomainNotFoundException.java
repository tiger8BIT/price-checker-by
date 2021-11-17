package pricecheckerby.exception;

public class DomainNotFoundException extends RuntimeException {
    public DomainNotFoundException() {
        super("Domain not supported");
    }
}
