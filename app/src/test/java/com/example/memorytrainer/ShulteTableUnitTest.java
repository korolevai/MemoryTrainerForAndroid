package com.example.memorytrainer;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Модульные тесты класса ShulteTable
 * @author Koroleva
 */
public class ShulteTableUnitTest {

    /**
     * Тест получения строкового представления символов таблицы Шульте
     * для варианта заполнения "Цифры"
     */
    @Test
    public void getCharacterByCode_digits() {
        ShulteTable table = new ShulteTable(3, ShulteTable.ShulteTableCharset.DIGITS);
        assertEquals("0", table.getCharacterByCode(0));
        assertEquals("267", table.getCharacterByCode(267));
    }

    /**
     * Тест получения строкового представления символов таблицы Шульте
     * для варианта заполнения "Русские буквы"
     */
    @Test
    public void getCharacterByCode_russian() {
        ShulteTable table = new ShulteTable(3, ShulteTable.ShulteTableCharset.RUSSIAN_CHARS);
        assertEquals("А", table.getCharacterByCode(0));
        assertEquals("ИУ", table.getCharacterByCode(267));
    }

    /**
     * Тест получения строкового представления символов таблицы Шульте
     * для варианта заполнения "Английские буквы"
     */
    @Test
    public void getCharacterByCode_english() {
        ShulteTable table = new ShulteTable(3, ShulteTable.ShulteTableCharset.ENGLISH_CHARS);
        assertEquals("A", table.getCharacterByCode(0));
        assertEquals("KH", table.getCharacterByCode(267));
    }

    /**
     * Тест заполнения таблицы Шульте без перемешивания
     */
    @Test
    public void fillTable_3x3() {
        ShulteTable table = new ShulteTable(3, ShulteTable.ShulteTableCharset.DIGITS);
        table.fill();
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}, table.values);
    }

    /**
     * Тест перемешивания таблицы Шульте
     */
    @Test
    public void shuffleTable_3x3() {
        ShulteTable table = new ShulteTable(3, ShulteTable.ShulteTableCharset.DIGITS);
        table.fill();
        int[] notShuffled = Arrays.copyOf(table.values, 9);
        table.shuffle();
        boolean isShuffled = false;
        for (int i = 0; i < 9; i++) {
            if (table.values[i] != notShuffled[i]) {
                isShuffled = true;
                break;
            }
        }
        assertTrue(isShuffled);
    }
}