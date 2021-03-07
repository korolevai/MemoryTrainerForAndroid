package com.example.memorytrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Класс activity экрана просмотра журнала результатов тренировок
 * @author Koroleva
 */
public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        Database database = new Database(getApplicationContext());
        List<TrainingResult> results = database.getTrainingResults();

        ListView listView = (ListView) findViewById(R.id.logListView);
        ArrayAdapter<TrainingResult> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, results);
        listView.setAdapter(adapter);
    }
}