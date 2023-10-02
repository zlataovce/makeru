package dev.cephx.makeru.expr.table;

import dev.cephx.makeru.expr.StatementBaseSQLExpression;
import dev.cephx.makeru.expr.annotations.LimitedFeatureSupport;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.With;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

@Value
@With
@Builder(toBuilder = true)
public class DropTableSQLExpression implements StatementBaseSQLExpression {
    @LimitedFeatureSupport(platform = "POSTGRESQL", since = "8_2")
    @lombok.Builder.Default
    boolean ifExists = false;
    @Singular
    @NotNull
    List<String> tableNames;
    @Nullable
    Action action;

    public enum Action {
        CASCADE,
        RESTRICT;

        @Override
        public String toString() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
