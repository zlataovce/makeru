package dev.cephx.makeru.expr.table;

import dev.cephx.makeru.expr.StatementBaseSQLExpression;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.jetbrains.annotations.NotNull;

@Value
@With
@Builder
public class CreateTableSQLExpression implements StatementBaseSQLExpression {
    @lombok.Builder.Default
    boolean ifNotExists = false;
    @NotNull
    String tableName;
}
