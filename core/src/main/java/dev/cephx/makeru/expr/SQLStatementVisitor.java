package dev.cephx.makeru.expr;

import dev.cephx.makeru.expr.constraint.ColumnConstraintSQLExpression;
import dev.cephx.makeru.expr.constraint.TableConstraintSQLExpression;

public interface SQLStatementVisitor {
    boolean visit(StatementBaseSQLExpression expr);
    void visitColumns();
    void visitColumn(ColumnSQLExpression expr);
    boolean visitColumnConstraint(ColumnConstraintSQLExpression expr);
    boolean visitTableConstraint(TableConstraintSQLExpression expr);
    void visitColumnsEnd();
    void visitEnd();
}
