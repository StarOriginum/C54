package com.example.annexe4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    EditText champNom, champPrenom;
    Button confirm;

    Ecouteur ec;
    Utilisateur user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        champNom = findViewById(R.id.champNom);
        champPrenom = findViewById(R.id.champPrenom);
        confirm = findViewById(R.id.confirm);

        ec = new Ecouteur();
        user = new Utilisateur();

        confirm.setOnClickListener(ec);
    }

    public class Ecouteur implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v == confirm) {
                user.setNom(champNom.getText().toString());
                user.setPrenom(champPrenom.getText().toString());


                Intent i = new Intent();
                i.putExtra("Identifiant", user);

                setResult(RESULT_OK, i);
                finish();
            }

        }
    }
}

