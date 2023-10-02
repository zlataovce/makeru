package dev.cephx.makeru.expr.table;

import dev.cephx.makeru.expr.StatementBaseSQLExpression;
import dev.cephx.makeru.expr.annotations.LimitedFeatureSupport;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.jetbrains.annotations.NotNull;

@Value
@With
@Builder(toBuilder = true)
public class CreateTableSQLExpression implements StatementBaseSQLExpression {
    @LimitedFeatureSupport(platform = "POSTGRESQL", since = "9_1")
    @lombok.Builder.Default
    boolean ifNotExists = false;
    @NotNull
    String tableName;
}
