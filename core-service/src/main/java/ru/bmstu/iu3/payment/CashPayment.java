package ru.bmstu.iu3.payment;

import ru.bmstu.iu3.exception.PaymentException;
import ru.bmstu.iu3.service.InputReader;

public class CashPayment implements Payment {
    @Override
    public void pay(int amount, InputReader reader) {
        int cash = reader.readInt("Операция оплаты наличными. Введите сумму для оплаты: " + amount);
        if (cash >= amount) {
            System.out.println("Оплата наличными прошла успешно.");
            System.out.println("Сдача: " + (cash - amount));
        } else {
            throw new PaymentException("Недостаточно наличных средств для оплаты.");
        }
    }

    @Override
    public void description() {
        System.out.println("Оплата наличными.");
    }
}
