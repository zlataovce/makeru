package dev.cephx.makeru.expr.impl;

import dev.cephx.makeru.expr.AmbiguousConstraintDefinitionException;
import dev.cephx.makeru.expr.AbstractSQLStatementVisitor;
import dev.cephx.makeru.expr.constraint.*;
import dev.cephx.makeru.expr.table.CreateTableSQLExpression;

public class PostgreSQL14SQLStatementVisitor extends AbstractSQLStatementVisitor {
    public PostgreSQL14SQLStatementVisitor(int mod) {
        super(mod);
    }

    @Override
    public void visitCreateTable(CreateTableSQLExpression expr) {
        writeKeyword("create table ");
        if (expr.isIfNotExists()) {
            writeKeyword("if not exists ");
        }
        write(expr.getTableName());
    }

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
        write(String.join(", ", expr.getRefColumns()));
        write(")");

        final ForeignKeyConstraintReferentialAction onUpdate = expr.getOnUpdate();
        if (onUpdate != null) {
            writeKeyword(" on update ");
            visitForeignKeyReferentialAction(onUpdate);
        }
        final ForeignKeyConstraintReferentialAction onDelete = expr.getOnDelete();
        if (onDelete != null) {
            writeKeyword(" on delete ");
            visitForeignKeyReferentialAction(onDelete);
        }
    }

    protected void visitForeignKeyReferentialAction(ForeignKeyConstraintReferentialAction action) {
        writeKeyword(action.getType().toString());

        if (!action.getColumns().isEmpty()) {
            switch (action.getType()) {
                case SET_NULL:
                case SET_DEFAULT:
                    write(" (");
                    write(String.join(", ", action.getColumns()));
                    write(")");
                default:
                    throw new AmbiguousConstraintDefinitionException("Column subset selection is only supported for SET NULL and SET DEFAULT foreign key referential actions");
            }
        }
    }

    public void visitPrimaryKeyColumnConstraint() {
        writeKeyword(" primary key");
    }

    public void visitUniqueColumnConstraint(UniqueConstraintSQLExpression expr) {
        writeKeyword(" unique");
        if (!expr.isNullsDistinct()) {
            throw new UnsupportedOperationException("NULLS NOT DISTINCT in a UNIQUE constraint is not supported in " + this.getClass().getSimpleName());
        }
    }

    // support referential action column subset selection
    @Override
    public void visitForeignKeyTableConstraint(ForeignKeyConstraintSQLExpression expr) {
        writeKeyword("foreign key (");
        write(String.join(", ", expr.getColumnNames()));
        writeKeyword(") references ");
        write(expr.getRefTable());
        write(" (");
        write(String.join(", ", expr.getRefColumns()));
        write(")");

        final ForeignKeyConstraintReferentialAction onUpdate = expr.getOnUpdate();
        if (onUpdate != null) {
            writeKeyword(" on update ");
            writeKeyword(onUpdate.getType().toString());

            if (!onUpdate.getColumns().isEmpty()) {
                write(" (");
                write(String.join(", ", onUpdate.getColumns()));
                write(")");
            }
        }
        final ForeignKeyConstraintReferentialAction onDelete = expr.getOnDelete();
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
