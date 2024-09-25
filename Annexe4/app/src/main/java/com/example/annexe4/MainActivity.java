package com.example.annexe4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    TextView champInfo;
    Button btnStart;
    Ecouteur ec;
    Utilisateur user;
    ActivityResultLauncher<Intent> lanceur;
    Context contexte;

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


        champInfo = findViewById(R.id.champInfos);
        btnStart = findViewById(R.id.btnStart);

        ec = new Ecouteur();
        lanceur = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new callBackText());

        btnStart.setOnClickListener(ec);
    }


    private class callBackText implements ActivityResultCallback<ActivityResult> {

        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK){
                Intent data = result.getData();
                if (data != null){
                    user = (Utilisateur) data.getSerializableExtra("Identifiant");
                    if (user != null){
                        champInfo.setText("Bonjour " + user.getPrenom() + " " + user.getNom() +" !");
                    }
                }

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle infos) {
        super.onSaveInstanceState(infos);
        infos.putSerializable("Identifiant", user);
    }

    public class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v == btnStart){
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                lanceur.launch(i);
            }
        }
    }
}