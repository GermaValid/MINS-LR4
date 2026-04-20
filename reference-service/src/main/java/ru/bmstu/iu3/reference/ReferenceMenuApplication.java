package ru.bmstu.iu3.reference;

import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import ru.bmstu.iu3.menu.Menu;
import ru.bmstu.iu3.menu.MenuBootstrap;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Сервис B — справочник меню (gRPC).
 */
public final class ReferenceMenuApplication {

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getenv().getOrDefault("REFERENCE_GRPC_PORT", "50051"));
        Menu menu = MenuBootstrap.loadFromClasspath();
        Server server;
        try {
            server = NettyServerBuilder.forAddress(new InetSocketAddress("0.0.0.0", port))
                    .addService(new MenuCatalogGrpcServiceImpl(menu))
                    .intercept(new TraceServerInterceptor())
                    .build()
                    .start();
        } catch (IOException e) {
            if (hasCause(e, BindException.class)) {
                System.err.println("[reference-service] Порт " + port + " уже занят (скорее всего, сервис B уже запущен в другом окне).");
                System.err.println("  Закройте старый процесс или задайте другой порт: REFERENCE_GRPC_PORT=50052 (и тот же порт в A).");
            } else {
                System.err.println("[reference-service] Не удалось запустить сервер: " + e.getMessage());
            }
            e.printStackTrace();
            System.exit(1);
            return;
        }
        System.out.println("[reference-service] gRPC сервер запущен на 0.0.0.0:" + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                server.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));
        server.awaitTermination();
    }

    private static boolean hasCause(Throwable t, Class<? extends Throwable> type) {
        while (t != null) {
            if (type.isInstance(t)) {
                return true;
            }
            t = t.getCause();
        }
        return false;
    }
}
