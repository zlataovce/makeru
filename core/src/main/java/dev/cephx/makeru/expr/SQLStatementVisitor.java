package dev.cephx.makeru.expr;

import dev.cephx.makeru.expr.constraint.ColumnConstraintSQLExpression;
import dev.cephx.makeru.expr.constraint.TableConstraintSQLExpression;
import org.jetbrains.annotations.NotNull;

public interface SQLStatementVisitor {
    boolean visit(@NotNull StatementBaseSQLExpression expr);
    void visitColumns();
    boolean visitColumn(@NotNull ColumnSQLExpression expr);
    boolean visitColumnConstraint(@NotNull ColumnConstraintSQLExpression expr);
    boolean visitTableConstraint(@NotNull TableConstraintSQLExpression expr);
    void visitColumnsEnd();
    void visitEnd();
}
