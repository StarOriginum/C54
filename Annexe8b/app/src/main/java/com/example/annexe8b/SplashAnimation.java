package com.example.annexe8b;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashAnimation extends AppCompatActivity {

    ObjectAnimator scaleX, scaleY, alpha, x, y;
    TextView soleil;
    View object;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_animation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        soleil = findViewById(R.id.textView2);
        object = findViewById(R.id.view3);
        button = findViewById(R.id.button5);



        x = ObjectAnimator.ofFloat(object, View.X, 500);
        y = ObjectAnimator.ofFloat(object, View.Y, 500);
        alpha = ObjectAnimator.ofFloat(soleil, View.ALPHA, 1);
        scaleX = ObjectAnimator.ofFloat(object, View.SCALE_X, 20);
        scaleY = ObjectAnimator.ofFloat(object, View.SCALE_Y, 20);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.playSequentially(x, y, alpha);
        set.playTogether(scaleX, scaleY);

        button.setOnClickListener(v-> set.start());



    }
}