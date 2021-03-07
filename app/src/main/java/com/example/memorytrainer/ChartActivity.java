package com.example.memorytrainer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс activity для отображения результатов тренировок в виде диаграммы
 * @author Koroleva
 */
public class ChartActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Database database = new Database(getApplicationContext());
        List<TrainingResult> results = database.getTrainingResults();

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        Cartesian cartesian = AnyChart.column();
        List<DataEntry> data = new ArrayList<>();
        int n = Math.min(results.size(), 10);
        for(int i=results.size()-n; i<results.size(); i++) {
            TrainingResult item = results.get(i);
            LocalDateTime dat = item.trainingDate.toInstant().atZone(ZoneId.of("Europe/Moscow")).toLocalDateTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            data.add(new ValueDataEntry(dat.format(formatter), item.seconds));
        }
        Column column = cartesian.column(data);
        cartesian.title("Результаты (последние "+Math.min(results.size(), 10)+")");
        cartesian.xAxis(0).title("Дата");
        cartesian.yAxis(0).title("Время, сек.");
        anyChartView.setChart(cartesian);
    }
}