package com.example.memorytrainer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Реализация операций сохранения и чтения журнала тренировок в базу данных
 * @author Koroleva
 */
public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "database", null, 1);
    }

    /**
     * Обработчик события создания новой базы данных
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS RESULTS (TrainingDate LONG, FieldSize INTEGER, CharacterSet TEXT, Seconds INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  }

    /**
     * Сохранение результата тренировки в базу данных
     * @param date Дата
     * @param fieldSize Размер таблицы Шульте
     * @param characterSet Вариант заполнения таблицы Шульте
     * @param seconds Время тренировки, сек.
     */
    public void saveTrainingResult(Date date, int fieldSize, ShulteTable.ShulteTableCharset characterSet, long seconds) {
        ContentValues values = new ContentValues();
        values.put("TrainingDate", date.getTime());
        values.put("FieldSize", fieldSize);
        values.put("CharacterSet", characterSet.toString());
        values.put("Seconds", seconds);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("RESULTS", null, values);
    }

    /**
     * Чтение из базы данных журнала выполнения тренировок
     */
    public ArrayList<TrainingResult> getTrainingResults() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select TrainingDate, FieldSize, CharacterSet, Seconds from RESULTS order by TrainingDate", null);
        ArrayList<TrainingResult> result = new ArrayList<TrainingResult>();
        while (cursor.moveToNext()) {
            TrainingResult item = new TrainingResult();
            item.trainingDate = new Date(cursor.getLong(0));
            item.fieldSize = cursor.getInt(1);
            item.characterSet = ShulteTable.ShulteTableCharset.valueOf(cursor.getString(2));
            item.seconds = cursor.getInt(3);
            result.add(item);
        }
        cursor.close();
        return result;
    }
}
