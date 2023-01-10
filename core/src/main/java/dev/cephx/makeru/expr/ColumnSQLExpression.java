package dev.cephx.makeru.expr;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.jetbrains.annotations.NotNull;

@Value
@With
@Builder
public class ColumnSQLExpression implements SQLExpression {
    @NotNull
    String name;
    @NotNull
    String type;
}
