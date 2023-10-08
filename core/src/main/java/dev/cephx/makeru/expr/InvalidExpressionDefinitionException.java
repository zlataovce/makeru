package dev.cephx.makeru.expr;

public class InvalidExpressionDefinitionException extends VisitorException {
    public InvalidExpressionDefinitionException() {
        super();
    }

    public InvalidExpressionDefinitionException(final String message) {
        super(message);
    }

    public InvalidExpressionDefinitionException(final Throwable cause) {
        super(cause);
    }

    public InvalidExpressionDefinitionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
