package dev.cephx.makeru.expr.constraint;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.With;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

@Value
@With
@Builder
public class ForeignKeyConstraintSQLExpression implements MultiColumnConstraintSQLExpression {
    @Nullable
    String name;
    @Singular
    @Unmodifiable
    List<String> columnNames;
    @NotNull
    String refTable;
    @Singular
    @Unmodifiable
    List<String> refColumns;

    // TODO: referential actions
}
