package com.example.importfromcsv;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    InputStream inputStream;

    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputStream = getResources().openRawResource(R.raw.import_from_csv);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                data = csvLine.split(",");
                try {
//                    Log.e("Data ", "" + data[0]);
                    Log.e("Data ", "" + data[0] + ", " + data[1] + ", " + data[2]);
                } catch (Exception e) {
                    Log.e("Problem", e.toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error in reading CSV file: " + e);
        }
    }
}