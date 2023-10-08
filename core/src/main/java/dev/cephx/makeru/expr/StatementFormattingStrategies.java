package dev.cephx.makeru.expr;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum StatementFormattingStrategies implements StatementFormattingStrategy {
    DEFAULT,
    UPPER_CASE {
        @Override
        public @NotNull String formatKeyword(@NotNull String keyword) {
            return keyword.toUpperCase(Locale.ROOT);
        }
    },
    LOWER_CASE {
        @Override
        public @NotNull String formatKeyword(@NotNull String keyword) {
            return keyword.toLowerCase(Locale.ROOT);
        }
    }
}
