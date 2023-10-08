package dev.cephx.makeru.expr;

public class AmbiguousConstraintDefinitionException extends VisitorException {
    public AmbiguousConstraintDefinitionException() {
        super();
    }

    public AmbiguousConstraintDefinitionException(final String message) {
        super(message);
    }

    public AmbiguousConstraintDefinitionException(final Throwable cause) {
        super(cause);
    }

    public AmbiguousConstraintDefinitionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
