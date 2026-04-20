package ru.bmstu.iu3.grpc;

import io.grpc.Metadata;

public final class TraceMetadata {

    public static final Metadata.Key<String> TRACE_ID =
            Metadata.Key.of("x-trace-id", Metadata.ASCII_STRING_MARSHALLER);

    private TraceMetadata() {
    }
}
