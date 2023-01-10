package dev.cephx.makeru.expr.constraint;

import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public interface MultiColumnConstraintSQLExpression extends ColumnConstraintSQLExpression, TableConstraintSQLExpression {
    @Unmodifiable
    List<String> getColumnNames();
}
