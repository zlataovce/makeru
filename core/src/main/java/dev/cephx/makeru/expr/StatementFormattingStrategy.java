package dev.cephx.makeru.expr;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface StatementFormattingStrategy {
    default boolean skipUnsupported() {
        return false;
    }

    default @Nullable String formatKeyword(@NotNull String keyword) {
        return null;
    }
}
