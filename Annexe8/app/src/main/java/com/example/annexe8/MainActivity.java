package com.example.annexe8;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;



public class MainActivity extends AppCompatActivity {

    ObjectAnimator animation, animation2;
    View objet;
    Button bouton;
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

        objet = findViewById(R.id.view);
        bouton = findViewById(R.id.button);

        Path p = new Path();
        p.moveTo(50, 200);
        p.lineTo(900, 200);
        p.lineTo(900, 650);
        p.arcTo(400, 600, 900, 1000, 0, 359, false);



        animation = ObjectAnimator.ofFloat(objet, "x", "y", p);
//        animation.setDuration(2500);
        // changer l'interpolation
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.setRepeatMode(ValueAnimator.REVERSE);

        animation2 = ObjectAnimator.ofArgb(objet, "backgroundColor", Color.RED, Color.rgb(63, 160, 13));
        animation2.setRepeatCount(ValueAnimator.INFINITE);
        animation2.setRepeatMode(ValueAnimator.REVERSE);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(3000);
        set.play(animation, animation2);
        set.

        bouton.setOnClickListener(v -> {
            set.start();
        });

    }
}