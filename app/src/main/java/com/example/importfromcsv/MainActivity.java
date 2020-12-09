package com.example.importfromcsv;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonImportCSV = findViewById(R.id.btn_import_csv_file);
        buttonImportCSV.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            try {
                startActivityForResult(intent, 101);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No activity can handle picking a file", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "requestCode " + requestCode + "resultCode: " + resultCode, Toast.LENGTH_SHORT).show();

        Uri filePath = data.getData();
        InputStream inputStream;
        try {
            inputStream = getContentResolver().openInputStream(filePath);
            if (requestCode == 101 && resultCode == RESULT_OK) {
                Log.e("Import", "importing file from: " + inputStream);
                importFile(inputStream);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void importFile(InputStream inputStream) {
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