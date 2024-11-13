package com.example.annexe8b;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Intent cars, splash, title;

    Button carsAnim, splashAnim, titleAnim;

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

        carsAnim = findViewById(R.id.button);
        splashAnim = findViewById(R.id.button2);
        titleAnim = findViewById(R.id.button3);


        carsAnim.setOnClickListener(v -> {
            cars = new Intent(MainActivity.this, CarsAnimation.class);
            startActivity(cars);
        });
        titleAnim.setOnClickListener(v -> {
            title = new Intent(MainActivity.this, TtitleAnimation.class);
            startActivity(title);
        });
        splashAnim.setOnClickListener(v -> {
            splash = new Intent(MainActivity.this, SplashAnimation.class);
            startActivity(splash);
        });

    }
}