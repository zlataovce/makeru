package dev.cephx.makeru;

import org.jetbrains.annotations.NotNull;

public interface Statement<R extends Result<? extends Row>> extends StatementLike<Statement<R>> {
    void execute();
    @NotNull
    Iterable<R> executeAsQuery();
    long executeAsUpdate();
}
