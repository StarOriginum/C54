package com.example.annexe0;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AjouterActivity extends AppCompatActivity{

    Ecouteur ec;
    Button ajouter;
    EditText memoTexte;
    PrintWriter out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ajouter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ajouter = findViewById(R.id.memo);
        memoTexte = findViewById(R.id.memoTexte);
        ec = new Ecouteur();
    }

    public class Ecouteur implements View.OnClickListener{
        String content;
        String path = "C:/travail/C54/Annexe0";
        @Override
        public void onClick(View v) {
            if (v == ajouter){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    try {
                        Files.write(Paths.get(path), content.getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}