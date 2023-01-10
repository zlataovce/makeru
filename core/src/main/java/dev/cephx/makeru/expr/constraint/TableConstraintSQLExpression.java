package dev.cephx.makeru.expr.constraint;

import org.jetbrains.annotations.Nullable;

public interface TableConstraintSQLExpression extends ConstraintSQLExpression {
    @Nullable
    String getName();
}
