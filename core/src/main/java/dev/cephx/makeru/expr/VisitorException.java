package dev.cephx.makeru.expr;

public class VisitorException extends RuntimeException {
    public VisitorException() {
        super();
    }

    public VisitorException(final String message) {
        super(message);
    }

    public VisitorException(final Throwable cause) {
        super(cause);
    }

    public VisitorException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
