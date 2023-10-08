package dev.cephx.makeru;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Statement<R> {
    @Contract("-> this")
    @NotNull
    Statement<R> add();
    @Contract("_, _ -> this")
    @NotNull
    Statement<R> bind(int index, @NotNull Object value);
    @Contract("_, _ -> this")
    @NotNull
    Statement<R> bindNull(int index, @NotNull  Class<?> type);
    R execute();
}
