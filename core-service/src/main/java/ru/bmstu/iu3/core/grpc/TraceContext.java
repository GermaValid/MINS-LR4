package ru.bmstu.iu3.core.grpc;

import java.util.UUID;

/**
 * Идентификатор запроса для корреляции логов между сервисом A и B (один trace на действие пользователя в консоли).
 */
public final class TraceContext {

    private static final ThreadLocal<String> CURRENT = new ThreadLocal<>();

    private TraceContext() {
    }

    public static void set(String traceId) {
        CURRENT.set(traceId);
    }

    public static String get() {
        return CURRENT.get();
    }

    public static String getOrCreate() {
        String s = CURRENT.get();
        if (s == null) {
            s = UUID.randomUUID().toString();
            CURRENT.set(s);
        }
        return s;
    }

    public static void clear() {
        CURRENT.remove();
    }
}
