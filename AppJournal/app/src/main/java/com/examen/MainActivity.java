package com.examen;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText champDate, champResume;
    TextView header;

    InfoJournal ij;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        header = findViewById(R.id.textView);
        champDate = findViewById(R.id.champDate);
        champResume = findViewById(R.id.champMessage);

        try (FileInputStream fis = openFileInput("info.ser");
             ObjectInputStream ois = new ObjectInputStream(fis);){
            ij = (InfoJournal)ois.readObject();
            champDate.setText(ij.getDate());
            champResume.setText(ij.getMessage());
        } catch (Exception e){
            header.setText("Journal Virtuel - pas de sauvegarde exisante");
            e.printStackTrace();
        }




    }

    @Override
    protected void onStart() {
        super.onStart();
        try(FileInputStream fis = openFileInput("info.ser");
            ObjectInputStream ois =new ObjectInputStream(fis);){
            ij = (InfoJournal) ois.readObject();
            champDate.setText(ij.getDate());
            champResume.setText(ij.getMessage());
        } catch (Exception e) {
            header.setText("Journal Virtuel - pas de sauvegarde exisante");
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        ij = new InfoJournal(champDate.getText().toString(), champResume.getText().toString());
        try(FileOutputStream fos = openFileOutput("info.ser", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(ij);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ij = new InfoJournal(champDate.getText().toString(), champResume.getText().toString());
        try(FileOutputStream fos = openFileOutput("info.ser", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(ij);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}