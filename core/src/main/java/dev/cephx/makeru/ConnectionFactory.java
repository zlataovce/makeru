package dev.cephx.makeru;

import org.jetbrains.annotations.NotNull;

public interface ConnectionFactory {
    @NotNull
    Connection create();
}
