package com.example.memorytrainer;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TrainingResult {
    public Date trainingDate;
    public int fieldSize;
    public ShulteTable.ShulteTableCharset characterSet;
    public int seconds;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String toString() {
        String fieldType;
        switch (characterSet) {
            case ENGLISH_CHARS:
                fieldType = "Английские буквы";
                break;
            case RUSSIAN_CHARS:
                fieldType = "Русские буквы";
                break;
            default:
                fieldType = "Цифры";
        }
        LocalDateTime dat = trainingDate.toInstant().atZone(ZoneId.of("Europe/Moscow")).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return dat.format(formatter)
                + " | " + String.valueOf(fieldSize) + "x" + String.valueOf(fieldSize)+ " " + fieldType
                + " | " + String.valueOf(seconds) + " сек.";
    }
}
