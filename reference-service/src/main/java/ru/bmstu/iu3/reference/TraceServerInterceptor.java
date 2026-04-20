package ru.bmstu.iu3.reference;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import ru.bmstu.iu3.grpc.TraceMetadata;

/**
 * Прокидывает x-trace-id в {@link Context} для логирования в обработчиках.
 */
public final class TraceServerInterceptor implements ServerInterceptor {

    static final Context.Key<String> TRACE_ID_CTX = Context.key("traceId");

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {
        String traceId = headers.get(TraceMetadata.TRACE_ID);
        if (traceId == null || traceId.isEmpty()) {
            traceId = "no-trace";
        }
        Context ctx = Context.current().withValue(TRACE_ID_CTX, traceId);
        return Contexts.interceptCall(ctx, call, headers, next);
    }
}
