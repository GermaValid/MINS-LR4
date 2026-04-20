package ru.bmstu.iu3.reference;

import io.grpc.Context;

/**
 * Помогает разделить вывод логов от бизнес-логики gRPC-сервиса.
 */
public final class GrpcServiceLogger {

    private static final String SERVICE_PREFIX = "[reference-service]";

    private GrpcServiceLogger() {
        // утилитный класс
    }

    public static String traceId() {
        String id = TraceServerInterceptor.TRACE_ID_CTX.get(Context.current());
        return id != null ? id : "no-trace";
    }

    public static void log(String traceId, String message) {
        System.out.println(SERVICE_PREFIX + " [traceId=" + traceId + "] " + message);
    }
}
