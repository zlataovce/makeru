package dev.cephx.makeru;

import org.jetbrains.annotations.NotNull;

public interface ConnectionFactory<C extends Connection> {
    @NotNull
    C create();
}
