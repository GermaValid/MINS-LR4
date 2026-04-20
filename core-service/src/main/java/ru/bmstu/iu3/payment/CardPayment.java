package ru.bmstu.iu3.payment;

import ru.bmstu.iu3.exception.PaymentException;
import ru.bmstu.iu3.service.InputReader;

public class CardPayment implements Payment {
    int cardMoney = 10000;

    @Override
    public void pay(int amount, InputReader reader) {
        System.out.println("Операция оплаты картой");
        if (cardMoney >= amount) {
            System.out.println("Оплата картой прошла успешно.");
        } else {
            throw new PaymentException("Недостаточно средств для оплаты картой.");
        }
    }

    @Override
    public void description() {
        System.out.println("Оплата с помощью банковской карты.");
    }
}
