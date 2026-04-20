package ru.bmstu.iu3.service;

import ru.bmstu.iu3.exception.PaymentException;
import ru.bmstu.iu3.payment.Payment;

import java.util.List;

public class PaymentService {
    private final List<Payment> paiments;
    private final InputReader reader;

    public PaymentService(List<Payment> paiments, InputReader reader) {
        this.paiments = paiments;
        this.reader = reader;
    }

    public void addPaiment(Payment paiment) {
        paiments.add(paiment);
    }

    public void payService(int amount) {
        for (int i = 0; i < paiments.size(); i++) {
            System.out.print((i + 1) + ". ");
            paiments.get(i).description();
        }
        int choice = reader.readInt("Выберите способ оплаты (введите номер):");
        if (choice >= 1 && choice <= paiments.size()) {
            paiments.get(choice - 1).pay(amount, reader);
        } else {
            throw new PaymentException("Неверный выбор. Пожалуйста, выберите номер от 1 до " + paiments.size() + ".");
        }
    }
}
