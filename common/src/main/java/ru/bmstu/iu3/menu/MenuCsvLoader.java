package ru.bmstu.iu3.menu;

import ru.bmstu.iu3.exception.ValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * CSV: первая строка — заголовок (пропускается), далее строки
 * «название,стоимость,описание». Запятые в описании допустимы (берётся всё после второй запятой).
 * Блюда регистрируются в {@link MenuUnitOfWork} (создание через фабрику внутри UoW).
 */
public final class MenuCsvLoader {

    private MenuCsvLoader() {
    }

    public static void loadInto(Path path, MenuUnitOfWork unitOfWork) throws IOException {
        try (InputStream in = Files.newInputStream(path)) {
            loadInto(in, unitOfWork);
        }
    }

    public static void loadInto(InputStream in, MenuUnitOfWork unitOfWork) throws IOException {
        try {
            loadIntoUnchecked(in, unitOfWork);
        } catch (IOException e) {
            unitOfWork.rollback();
            throw e;
        } catch (RuntimeException e) {
            unitOfWork.rollback();
            throw e;
        }
    }

    private static void loadIntoUnchecked(InputStream in, MenuUnitOfWork unitOfWork) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String header = br.readLine();
        if (header != null && !header.isEmpty() && header.charAt(0) == '\uFEFF') {
            header = header.substring(1);
        }
        String line;
        int row = 1;
        while ((line = br.readLine()) != null) {
            row++;
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] p = line.split(",", 3);
            if (p.length < 3) {
                throw new ValidationException("Строка " + row + ": нужно три поля через запятую (название, цена, описание).");
            }
            String name = p[0].trim();
            String priceRaw = p[1].trim().replace(" ", "");
            int price;
            try {
                price = Integer.parseInt(priceRaw);
            } catch (NumberFormatException e) {
                throw new ValidationException("Строка " + row + ": неверная стоимость \"" + priceRaw + "\".");
            }
            String desc = p[2].trim();
            if (desc.length() >= 2 && desc.charAt(0) == '"' && desc.charAt(desc.length() - 1) == '"') {
                desc = desc.substring(1, desc.length() - 1);
            }
            unitOfWork.registerNew(name, price, desc);
        }
    }
}
