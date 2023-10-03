package dev.cephx.makeru.dialect.postgresql.expr;

import dev.cephx.makeru.expr.AbstractSQLStatementVisitor;
import dev.cephx.makeru.expr.AmbiguousConstraintDefinitionException;
import dev.cephx.makeru.expr.InvalidExpressionDefinitionException;
import dev.cephx.makeru.expr.StatementFormattingStrategy;
import dev.cephx.makeru.expr.constraint.*;
import dev.cephx.makeru.expr.table.DropTableSQLExpression;

public class PostgreSQL81SQLStatementVisitor extends AbstractSQLStatementVisitor {
    public PostgreSQL81SQLStatementVisitor(StatementFormattingStrategy strategy) {
        super(strategy);
    }

    // support multiple tables
    @Override
    public void visitDropTable(DropTableSQLExpression expr) {
        writeKeyword("drop table ");
        if (!strategy.skipUnsupported() && expr.isIfExists()) {
            throw new UnsupportedOperationException("IF EXISTS in DROP TABLE is not supported");
        }
        if (expr.getTableNames().isEmpty()) {
            throw new InvalidExpressionDefinitionException("At least one table must be specified in DROP TABLE");
        }
        write(String.join(", ", expr.getTableNames()));
        if (expr.getAction() != null) {
            writeKeyword(" " + expr.getAction());
        }
    }

    // support more column constraints
    @Override
    public boolean visitColumnConstraint(ColumnConstraintSQLExpression expr) {
        if (super.visitColumnConstraint(expr)) {
            return true;
        }
        if (expr instanceof CheckConstraintSQLExpression) {
            visitCheckColumnConstraint((CheckConstraintSQLExpression) expr);
            return true;
        } else if (expr instanceof ForeignKeyConstraintSQLExpression) {
            visitForeignKeyColumnConstraint((ForeignKeyConstraintSQLExpression) expr);
            return true;
        } else if (expr instanceof PrimaryKeyConstraintSQLExpression) {
            visitPrimaryKeyColumnConstraint();
            return true;
        } else if (expr instanceof UniqueConstraintSQLExpression) {
            visitUniqueColumnConstraint((UniqueConstraintSQLExpression) expr);
            return true;
        }
        return false;
    }

    public void visitCheckColumnConstraint(CheckConstraintSQLExpression expr) {
        writeKeyword(" check (");
        write(expr.getExpression());
        write(")");
    }

    public void visitForeignKeyColumnConstraint(ForeignKeyConstraintSQLExpression expr) {
        writeKeyword(" references ");
        write(expr.getRefTable());
        write(" (");
        if (expr.getRefColumns().isEmpty()) {
            throw new InvalidExpressionDefinitionException("At least one foreign column must be specified in FOREIGN KEY");
        }
        write(String.join(", ", expr.getRefColumns()));
        write(")");

        final ForeignKeyConstraintSQLExpression.ReferentialAction onUpdate = expr.getOnUpdate();
        if (onUpdate != null) {
            writeKeyword(" on update ");
            visitForeignKeyReferentialAction(onUpdate);
        }
        final ForeignKeyConstraintSQLExpression.ReferentialAction onDelete = expr.getOnDelete();
        if (onDelete != null) {
            writeKeyword(" on delete ");
            visitForeignKeyReferentialAction(onDelete);
        }
    }

    protected void visitForeignKeyReferentialAction(ForeignKeyConstraintSQLExpression.ReferentialAction action) {
        writeKeyword(action.getType().toString());

        if (!action.getColumns().isEmpty()) {
            switch (action.getType()) {
                case SET_NULL:
                case SET_DEFAULT:
                    write(" (");
                    write(String.join(", ", action.getColumns()));
                    write(")");
                default:
                    if (!strategy.skipUnsupported()) {
                        throw new AmbiguousConstraintDefinitionException("Column subset selection is only supported for SET NULL and SET DEFAULT");
                    }
            }
        }
    }

    public void visitPrimaryKeyColumnConstraint() {
        writeKeyword(" primary key");
    }

    public void visitUniqueColumnConstraint(UniqueConstraintSQLExpression expr) {
        writeKeyword(" unique");
        if (!strategy.skipUnsupported() && !expr.isNullsDistinct()) {
            throw new UnsupportedOperationException("NULLS NOT DISTINCT in UNIQUE is not supported");
        }
    }

    // support referential action column subset selection
    @Override
    public void visitForeignKeyTableConstraint(ForeignKeyConstraintSQLExpression expr) {
        writeKeyword("foreign key (");
        if (expr.getColumnNames().isEmpty()) {
            throw new InvalidExpressionDefinitionException("At least one column must be specified in FOREIGN KEY");
        }
        write(String.join(", ", expr.getColumnNames()));
        writeKeyword(") references ");
        write(expr.getRefTable());
        write(" (");
        if (expr.getRefColumns().isEmpty()) {
            throw new InvalidExpressionDefinitionException("At least one foreign column must be specified in FOREIGN KEY");
        }
        write(String.join(", ", expr.getRefColumns()));
        write(")");

        final ForeignKeyConstraintSQLExpression.ReferentialAction onUpdate = expr.getOnUpdate();
        if (onUpdate != null) {
            writeKeyword(" on update ");
            writeKeyword(onUpdate.getType().toString());

            if (!onUpdate.getColumns().isEmpty()) {
                write(" (");
                write(String.join(", ", onUpdate.getColumns()));
                write(")");
            }
        }
        final ForeignKeyConstraintSQLExpression.ReferentialAction onDelete = expr.getOnDelete();
        if (onDelete != null) {
            writeKeyword(" on delete ");
            writeKeyword(onDelete.getType().toString());

            if (!onDelete.getColumns().isEmpty()) {
                write(" (");
                write(String.join(", ", onDelete.getColumns()));
                write(")");
            }
        }
    }
}
