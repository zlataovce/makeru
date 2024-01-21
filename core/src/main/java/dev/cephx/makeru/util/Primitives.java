package dev.cephx.makeru.util;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class Primitives {
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER = new LinkedHashMap<>(16);
    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE = new LinkedHashMap<>(16);

    static {
        add(boolean.class, Boolean.class);
        add(byte.class, Byte.class);
        add(char.class, Character.class);
        add(double.class, Double.class);
        add(float.class, Float.class);
        add(int.class, Integer.class);
        add(long.class, Long.class);
        add(short.class, Short.class);
        add(void.class, Void.class);
    }

    private Primitives() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static void add(Class<?> prim, Class<?> wrap) {
        PRIMITIVE_TO_WRAPPER.put(prim, wrap);
        WRAPPER_TO_PRIMITIVE.put(wrap, prim);
    }

    @SuppressWarnings("unchecked")
    public static <T> @NotNull Class<T> wrap(@NotNull Class<T> type) { // primitive to wrapper
        Objects.requireNonNull(type, "type");

        return (Class<T>) PRIMITIVE_TO_WRAPPER.getOrDefault(type, type);
    }

    @SuppressWarnings("unchecked")
    public static <T> @NotNull Class<T> unwrap(@NotNull Class<T> type) { // wrapper to primitive
        Objects.requireNonNull(type, "type");

        return (Class<T>) WRAPPER_TO_PRIMITIVE.getOrDefault(type, type);
    }
}
