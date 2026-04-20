package ru.bmstu.iu3.reference;

import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import ru.bmstu.iu3.grpc.menu.DishItem;
import ru.bmstu.iu3.grpc.menu.GetMenuRequest;
import ru.bmstu.iu3.grpc.menu.GetMenuResponse;
import ru.bmstu.iu3.grpc.menu.MenuCatalogGrpc;
import ru.bmstu.iu3.grpc.menu.ValidateDishRequest;
import ru.bmstu.iu3.grpc.menu.ValidateDishResponse;
import ru.bmstu.iu3.menu.Dish;
import ru.bmstu.iu3.menu.Menu;

final class MenuCatalogGrpcServiceImpl extends MenuCatalogGrpc.MenuCatalogImplBase {

    private final Menu menu;

    MenuCatalogGrpcServiceImpl(Menu menu) {
        this.menu = menu;
    }

    private static String traceId() {
        String id = TraceServerInterceptor.TRACE_ID_CTX.get(Context.current());
        return id != null ? id : "no-trace";
    }

    private static void log(String traceId, String message) {
        System.out.println("[reference-service] [traceId=" + traceId + "] " + message);
    }

    @Override
    public void getMenu(GetMenuRequest request, StreamObserver<GetMenuResponse> responseObserver) {
        String tid = traceId();
        log(tid, "GetMenu");
        GetMenuResponse.Builder b = GetMenuResponse.newBuilder();
        for (Dish dish : menu.getDishList()) {
            b.addDishes(DishItem.newBuilder()
                    .setName(dish.getName())
                    .setPrice(dish.getPrice())
                    .setDescription(dish.getDescription())
                    .build());
        }
        responseObserver.onNext(b.build());
        responseObserver.onCompleted();
    }

    @Override
    public void validateDishNumber(ValidateDishRequest request, StreamObserver<ValidateDishResponse> responseObserver) {
        String tid = traceId();
        int n = request.getNumber();
        log(tid, "ValidateDishNumber number=" + n);
        ValidateDishResponse.Builder b = ValidateDishResponse.newBuilder();
        if (n < 1 || n > menu.size()) {
            b.setValid(false);
            b.setErrorMessage("Неверный номер блюда. Пожалуйста, выберите номер от 1 до " + menu.size() + ".");
        } else {
            b.setValid(true);
        }
        responseObserver.onNext(b.build());
        responseObserver.onCompleted();
    }
}
