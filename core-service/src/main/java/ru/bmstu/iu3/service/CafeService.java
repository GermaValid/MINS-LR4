package ru.bmstu.iu3.service;

import ru.bmstu.iu3.core.grpc.TraceContext;
import ru.bmstu.iu3.exception.PaymentException;
import ru.bmstu.iu3.exception.ValidationException;
import ru.bmstu.iu3.service.action.Action;
import ru.bmstu.iu3.service.action.MakeOrderAction;
import ru.bmstu.iu3.service.action.PayOrderAction;
import ru.bmstu.iu3.service.action.ShowBillAction;
import ru.bmstu.iu3.service.action.ShowMenuAction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CafeService {

    private final MenuService menuService;
    private final OrderService orderService;
    private final PaymentService paimentService;
    private final InputReader reader;
    private final List<Action> actions = new ArrayList<>();

    public CafeService(MenuService menuService, OrderService orderService, PaymentService paimentService, InputReader reader) {
        this.menuService = menuService;
        this.orderService = orderService;
        this.paimentService = paimentService;
        this.reader = reader;
        initializeActions();
    }

    private void initializeActions() {
        actions.add(new ShowMenuAction(menuService));
        actions.add(new MakeOrderAction(orderService));
        actions.add(new ShowBillAction(orderService));
        actions.add(new PayOrderAction(orderService, paimentService));
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.println("====================================");
            for (int i = 0; i < actions.size(); i++) {
                System.out.println((i + 1) + ". " + actions.get(i).description());
            }
            System.out.println("0. Выход");
            System.out.println("====================================");

            int choice;
            try {
                choice = reader.readInt("Выберите действие:");
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (choice == 0) {
                if (orderService.getBill() > 0) {
                    System.out.println("У вас есть неоплаченный заказ. Пожалуйста, оплатите его перед выходом.");
                } else {
                    exit = true;
                }
                continue;
            }

            if (choice < 1 || choice > actions.size()) {
                System.out.println("Неизвестная команда. Пожалуйста, выберите пункт из меню.");
                continue;
            }

            TraceContext.set(UUID.randomUUID().toString());
            try {
                actions.get(choice - 1).execute();
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
                System.out.println("Попробуйте ещё раз.\n");
            } catch (PaymentException e) {
                System.out.println(e.getMessage());
                System.out.println("Попробуйте выбрать способ оплаты ещё раз.\n");
            } finally {
                TraceContext.clear();
            }
        }
    }
}
