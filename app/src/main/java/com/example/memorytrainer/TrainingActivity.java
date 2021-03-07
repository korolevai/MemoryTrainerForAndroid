package com.example.memorytrainer;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;

/**
 * Класс activity окна выполнения задания тренировки
 */
public class TrainingActivity extends AppCompatActivity implements View.OnClickListener {

    ShulteTable shulteTable;
    Date trainingStartDate;
    int currentCharCode;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        database = new Database(getApplicationContext());
        Bundle arguments = getIntent().getExtras();
        int size = arguments.getInt("size");
        ShulteTable.ShulteTableCharset charset = ShulteTable.ShulteTableCharset.valueOf(arguments.getString("charset"));
        shulteTable = new ShulteTable(size, charset);
        shulteTable.fill();
        shulteTable.shuffle();
        currentCharCode = 0;
        displayTable();
        displayHint();
        trainingStartDate = Calendar.getInstance().getTime();
    }

    /**
     * Отображение таблицы Шульте
     */
    void displayTable() {
        TableLayout layout = (TableLayout)findViewById(R.id.tblShulteLayout);
        layout.removeAllViews();
        for (int i = 0; i < shulteTable.size; i++) {
            TableRow tableRow = new TableRow(this);
            for (int j = 0; j < shulteTable.size; j++) {
                Button btn = new Button(this);
                String text = shulteTable.getCharacterByCode(shulteTable.values[i * shulteTable.size + j]);
                btn.setText(text);
                tableRow.addView(btn, j);
                btn.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f));
                btn.setTag(i*shulteTable.size+j);
                btn.setOnClickListener(this);
            }
            layout.addView(tableRow, i);
            tableRow.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1.0f));
        }
    }

    /**
     * Отображение текста символа который необходимо найти пользователю
     */
    void displayHint() {
        String currentChar = shulteTable.getCharacterByCode(currentCharCode);
        ((TextView) findViewById(R.id.txbTrainingHint)).setText("Нажмите '" + currentChar + "'");
    }

    /**
     * Контроль выполнения тренировки: обработчик события нажатия кнопки символа в таблице Шульте
     */
    @Override
    public void onClick(View v) {
        int charIndex = (int)(v.getTag());
        if(shulteTable.values[charIndex]==currentCharCode) {
            currentCharCode++;
            if (currentCharCode < shulteTable.values.length) {
                displayHint();
            } else {
                Date trainingEndDate = Calendar.getInstance().getTime();
                long seconds = (trainingEndDate.getTime() - trainingStartDate.getTime()) / 1000;
                database.saveTrainingResult(trainingStartDate, shulteTable.size, shulteTable.characterSet, seconds);
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Задание выполнено за " + String.valueOf(seconds) + " секунд.");
                dlgAlert.setTitle("Тренажер памяти");
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                dlgAlert.create().show();
            }
        }
    }
}