package ru.bmstu.iu3.reference;

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

    @Override
    public void getMenu(GetMenuRequest request, StreamObserver<GetMenuResponse> responseObserver) {
        String tid = GrpcServiceLogger.traceId();
        GrpcServiceLogger.log(tid, "GetMenu");
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
        String tid = GrpcServiceLogger.traceId();
        int n = request.getNumber();
        GrpcServiceLogger.log(tid, "ValidateDishNumber number=" + n);
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
