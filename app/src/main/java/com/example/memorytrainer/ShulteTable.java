package com.example.memorytrainer;

import java.util.Random;

/**
 * Реализация логики формирования таблицы Шульте
 * @author Koroleva
 */
public class ShulteTable {

    /**
     * Перечисление возможных вариантов заполнения таблицы Шульте
     */
    public enum ShulteTableCharset{
        /**
         * Цифры
         */
        DIGITS,

        /**
         * Английские буквы
         */
        ENGLISH_CHARS,

        /**
         * Русские буквы
         */
        RUSSIAN_CHARS
    }

    /**
     * Таблица Шульте - коды символов в виде линейного массива
     */
    public int[] values;

    /**
     * Вариант заполнения таблицы Шульте
     */
    public ShulteTableCharset characterSet;

    /**
     * Размер таблицы Шульте
     */
    public int size;

    /**
     * Конструктор
     * @param size Размер таблицы Шульте
     * @param characterSet Вариант заполнения таблицы Шульте
     */
    public ShulteTable(int size, ShulteTableCharset characterSet) {
        this.size = size;
        this.characterSet = characterSet;
        this.values = new int[size * size];
    }

    /**
     * Заполнение таблицы Шульте без перемешивания
     */
    public void fill() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int c = i * size + j;
                values[c] = c;
            }
        }
    }

    /**
     * Перемешивание символов в таблице Шульте
     */
    public void shuffle() {
        Random rnd = new Random();
        for (int i = 0; i < values.length; i++) {
            int t = rnd.nextInt(values.length - i) + i;
            int tmp = values[t];
            values[t] = values[i];
            values[i] = tmp;
        }
    }

    /**
     * Получение строкового представления символа по его номеру.
     * @param code Номер символа
     * @return Строковое представление символа в соответствии с текущим characterSet
     */
    public String getCharacterByCode(int code) {
        String alphabet;
        switch (characterSet) {
            case DIGITS:
                alphabet = "0123456789";
                break;
            case ENGLISH_CHARS:
                alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;
            case RUSSIAN_CHARS:
                alphabet = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЫЭЮЯ";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + characterSet);
        }
        String result = "";
        while (code >= alphabet.length()) {
            int charCode = code % alphabet.length();
            result = alphabet.charAt(charCode) + result;
            code = code / alphabet.length();
        }
        result = alphabet.charAt(code) + result;
        return result;
    }
}
