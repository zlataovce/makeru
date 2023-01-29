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
@Builder
public class DropTableSQLExpression implements StatementBaseSQLExpression {
    @LimitedFeatureSupport({
            "POSTGRESQL_15",
            "POSTGRESQL_14",
            "POSTGRESQL_13",
            "POSTGRESQL_12",
            "POSTGRESQL_11",
            "POSTGRESQL_10",
            "POSTGRESQL_9_6",
            "POSTGRESQL_9_5",
            "POSTGRESQL_9_4",
            "POSTGRESQL_9_3",
            "POSTGRESQL_9_2",
            "POSTGRESQL_9_1",
            "POSTGRESQL_9_0",
            "POSTGRESQL_8_4",
            "POSTGRESQL_8_3",
            "POSTGRESQL_8_2"
    })
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
