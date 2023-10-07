package dev.cephx.makeru;

import org.jetbrains.annotations.Contract;

public interface Statement<R> {
    @Contract("-> this")
    Statement<R> add();
    @Contract("_, _ -> this")
    Statement<R> bind(int index, Object value);
    @Contract("_, _ -> this")
    Statement<R> bindNull(int index, Class<?> type);
    R execute();
}
