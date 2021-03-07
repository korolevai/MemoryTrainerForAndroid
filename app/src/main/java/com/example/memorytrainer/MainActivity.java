package com.example.memorytrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((SeekBar) findViewById(R.id.fieldSizeSeekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((TextView) findViewById(R.id.fieldSizeText)).setText("Размер поля: " + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void btnStartClick(View view) {
        int size = ((SeekBar) findViewById(R.id.fieldSizeSeekBar)).getProgress();
        ShulteTable.ShulteTableCharset charset;
        if (((RadioButton) findViewById(R.id.rbEnglish)).isChecked()) {
            charset = ShulteTable.ShulteTableCharset.ENGLISH_CHARS;
        } else if (((RadioButton) findViewById(R.id.rbRussian)).isChecked()) {
            charset = ShulteTable.ShulteTableCharset.RUSSIAN_CHARS;
        } else {
            charset = ShulteTable.ShulteTableCharset.DIGITS;
        }
        Intent intent = new Intent(this, TrainingActivity.class);
        intent.putExtra("size", size);
        intent.putExtra("charset", charset.toString());
        startActivity(intent);
    }

    public void btnShowLogClick(View view) {
        Intent intent = new Intent(this, LogActivity.class);
        startActivity(intent);
    }

    public void btnShowChartClick(View view) {
        Intent intent = new Intent(this, ChartActivity.class);
        startActivity(intent);
    }
}