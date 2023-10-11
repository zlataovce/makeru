package dev.cephx.makeru;

import org.jetbrains.annotations.NotNull;

public interface Statement extends StatementLike<Statement> {
    void execute();
    @NotNull
    Iterable<? extends Result<? extends Row>> executeAsQuery();
    long executeAsUpdate();
}
