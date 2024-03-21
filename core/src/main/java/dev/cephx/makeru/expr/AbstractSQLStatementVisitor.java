package dev.cephx.makeru.expr;

import dev.cephx.makeru.expr.constraint.*;
import dev.cephx.makeru.expr.table.CreateTableSQLExpression;
import dev.cephx.makeru.expr.table.DropTableSQLExpression;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Objects;

public abstract class AbstractSQLStatementVisitor implements SQLStatementVisitor {
    protected final StringBuilder _builder = new StringBuilder();
    protected final StatementFormattingStrategy strategy;

    protected boolean hasVisitedFirstColumn = false;

    public AbstractSQLStatementVisitor(@NotNull StatementFormattingStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy");
    }

    protected void write(@NotNull String s) {
        _builder.append(s);
    }

    protected void writeDelimited(@NotNull Iterable<String> elements) {
        writeDelimited(", ", elements);
    }

    protected void writeDelimited(@NotNull String delimiter, @NotNull Iterable<String> elements) {
        for (final Iterator<String> iterator = elements.iterator(); iterator.hasNext(); ) {
            write(iterator.next());

            if (iterator.hasNext()) {
                write(delimiter);
            }
        }
    }

    protected void writeKeyword(@NotNull String s) {
        final String formatted = strategy.formatKeyword(s);
        if (formatted != null) {
            _builder.append(formatted);
        } else {
            writeKeywordDatabaseDependent(s);
        }
    }

    protected void writeKeywordDatabaseDependent(@NotNull String s) {
        _builder.append(s);
    }

    @Override
    public boolean visit(@NotNull StatementBaseSQLExpression expr) {
        if (expr instanceof CreateTableSQLExpression) {
            visitCreateTable((CreateTableSQLExpression) expr);
            return true;
        } else if (expr instanceof DropTableSQLExpression) {
            visitDropTable((DropTableSQLExpression) expr);
            return true;
        }
        return false;
    }

    public void visitCreateTable(@NotNull CreateTableSQLExpression expr) {
        writeKeyword("create table ");
        if (!strategy.skipUnsupported() && expr.isIfNotExists()) {
            throw new UnsupportedOperationException("IF NOT EXISTS in CREATE TABLE is not supported");
        }
        write(expr.getTableName());
    }

    public void visitDropTable(@NotNull DropTableSQLExpression expr) {
        writeKeyword("drop table ");
        if (!strategy.skipUnsupported() && expr.isIfExists()) {
            throw new UnsupportedOperationException("IF EXISTS in DROP TABLE is not supported");
        }
        if (expr.getTableNames().isEmpty()) {
            throw new InvalidExpressionDefinitionException("At least one table must be specified in DROP TABLE");
        }
        if (!strategy.skipUnsupported() && expr.getTableNames().size() > 1) {
            throw new UnsupportedOperationException("Multiple tables in DROP TABLE are not supported");
        }
        write(expr.getTableNames().get(0));
        if (expr.getAction() != null) {
            writeKeyword(" " + expr.getAction());
        }
    }

    @Override
    public void visitColumns() {
        write(" (");
    }

    @Override
    public boolean visitColumn(@NotNull ColumnSQLExpression expr) {
        if (hasVisitedFirstColumn) {
            write(", ");
        }
        hasVisitedFirstColumn = true;

        if (expr instanceof ColumnDeclarationSQLExpression) {
            visitColumnDeclaration((ColumnDeclarationSQLExpression) expr);
            return true;
        } else if (expr instanceof ColumnNameSQLExpression) {
            visitColumnName((ColumnNameSQLExpression) expr);
            return true;
        } else if (expr instanceof ColumnValueSQLExpression) {
            visitColumnValue((ColumnValueSQLExpression) expr);
            return true;
        }
        return false;
    }

    public void visitColumnDeclaration(@NotNull ColumnDeclarationSQLExpression expr) {
        write(expr.getName());
        write(" ");
        write(expr.getType());
    }

    public void visitColumnName(@NotNull ColumnNameSQLExpression expr) {
        write(expr.getName());
    }

    public void visitColumnValue(@NotNull ColumnValueSQLExpression expr) {
        write(expr.getExpression());
    }

    private void checkConstraintType(@NotNull ConstraintSQLExpression expr, boolean expectSingleColumn) {
        if (expr instanceof MultiColumnConstraintSQLExpression) {
            final MultiColumnConstraintSQLExpression nExpr = (MultiColumnConstraintSQLExpression) expr;
            if (expectSingleColumn && nExpr.getColumnNames().size() > 1) {
                throw new AmbiguousConstraintDefinitionException("Expected a single-column constraint, multiple columns were specified: " + expr);
            } else if (!expectSingleColumn && nExpr.getColumnNames().isEmpty()) {
                throw new AmbiguousConstraintDefinitionException("Expected a multi-column constraint, no columns were specified: " + expr);
            }
        }
    }

    @Override
    public boolean visitColumnConstraint(@NotNull ColumnConstraintSQLExpression expr) {
        checkConstraintType(expr, true);
        if (expr instanceof DefaultConstraintSQLExpression) {
            visitDefaultColumnConstraint((DefaultConstraintSQLExpression) expr);
            return true;
        } else if (expr instanceof NotNullConstraintSQLExpression) {
            visitNotNullColumnConstraint();
            return true;
        }
        return false;
    }

    public void visitDefaultColumnConstraint(@NotNull DefaultConstraintSQLExpression expr) {
        writeKeyword(" default ");
        write(expr.getExpression());
    }

    public void visitNotNullColumnConstraint() {
        writeKeyword(" not null");
    }

    @Override
    public boolean visitTableConstraint(@NotNull TableConstraintSQLExpression expr) {
        checkConstraintType(expr, false);
        if (expr instanceof CheckConstraintSQLExpression) {
            visitTableConstraintPrefix(expr);
            visitCheckTableConstraint((CheckConstraintSQLExpression) expr);
            visitTableConstraintSuffix(expr);
            return true;
        } else if (expr instanceof ForeignKeyConstraintSQLExpression) {
            visitTableConstraintPrefix(expr);
            visitForeignKeyTableConstraint((ForeignKeyConstraintSQLExpression) expr);
            visitTableConstraintSuffix(expr);
            return true;
        } else if (expr instanceof PrimaryKeyConstraintSQLExpression) {
            visitTableConstraintPrefix(expr);
            visitPrimaryKeyTableConstraint((PrimaryKeyConstraintSQLExpression) expr);
            visitTableConstraintSuffix(expr);
            return true;
        } else if (expr instanceof UniqueConstraintSQLExpression) {
            visitTableConstraintPrefix(expr);
            visitUniqueTableConstraint((UniqueConstraintSQLExpression) expr);
            visitTableConstraintSuffix(expr);
            return true;
        }
        return false;
    }

    protected void visitTableConstraintPrefix(@NotNull TableConstraintSQLExpression expr) {
        if (hasVisitedFirstColumn) {
            write(", ");
        }
        if (expr.getName() != null) {
            writeKeyword("constraint ");
            write(expr.getName());
            write(" ");
        }
    }

    protected void visitTableConstraintSuffix(@NotNull TableConstraintSQLExpression expr) {
        hasVisitedFirstColumn = true;
    }

    public void visitCheckTableConstraint(@NotNull CheckConstraintSQLExpression expr) {
        writeKeyword("check (");
        write(expr.getExpression());
        write(")");
    }

    public void visitForeignKeyTableConstraint(@NotNull ForeignKeyConstraintSQLExpression expr) {
        writeKeyword("foreign key (");
        if (expr.getColumnNames().isEmpty()) {
            throw new InvalidExpressionDefinitionException("At least one column must be specified in FOREIGN KEY");
        }
        writeDelimited(expr.getColumnNames());
        writeKeyword(") references ");
        write(expr.getRefTable());
        write(" (");
        if (expr.getRefColumns().isEmpty()) {
            throw new InvalidExpressionDefinitionException("At least one foreign column must be specified in FOREIGN KEY");
        }
        writeDelimited(expr.getRefColumns());
        write(")");

        final ForeignKeyConstraintSQLExpression.ReferentialAction onUpdate = expr.getOnUpdate();
        if (onUpdate != null) {
            writeKeyword(" on update ");
            writeKeyword(onUpdate.getType().toString());

            if (!strategy.skipUnsupported() && !onUpdate.getColumns().isEmpty()) {
                throw new UnsupportedOperationException("Column subset selection is not supported for ON UPDATE");
            }
        }
        final ForeignKeyConstraintSQLExpression.ReferentialAction onDelete = expr.getOnDelete();
        if (onDelete != null) {
            writeKeyword(" on delete ");
            writeKeyword(onDelete.getType().toString());

            if (!strategy.skipUnsupported() && !onDelete.getColumns().isEmpty()) {
                throw new UnsupportedOperationException("Column subset selection is not supported for ON DELETE");
            }
        }
    }

    public void visitPrimaryKeyTableConstraint(@NotNull PrimaryKeyConstraintSQLExpression expr) {
        writeKeyword("primary key (");
        if (expr.getColumnNames().isEmpty()) {
            throw new InvalidExpressionDefinitionException("At least one column must be specified in PRIMARY KEY");
        }
        writeDelimited(expr.getColumnNames());
        write(")");
    }

    public void visitUniqueTableConstraint(@NotNull UniqueConstraintSQLExpression expr) {
        writeKeyword("unique ");
        if (!strategy.skipUnsupported() && !expr.isNullsDistinct()) {
            throw new UnsupportedOperationException("NULLS NOT DISTINCT in UNIQUE is not supported");
        }
        write("(");
        if (expr.getColumnNames().isEmpty()) {
            throw new InvalidExpressionDefinitionException("At least one column must be specified in UNIQUE");
        }
        writeDelimited(expr.getColumnNames());
        write(")");
    }

    @Override
    public void visitColumnsEnd() {
        write(")");
        hasVisitedFirstColumn = false;
    }

    @Override
    public void visitEnd() {
        write(";");
    }

    @Override
    public String toString() {
        return _builder.toString();
    }
}
