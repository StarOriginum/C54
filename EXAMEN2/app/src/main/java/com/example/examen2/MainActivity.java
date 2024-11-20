package com.example.examen2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ObjectAnimator animation1, animation2, animation3;

    ObjectAnimator[] animations;
    ImageView mongolfiere;
    Button start;

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

        animations = new ObjectAnimator[6];

        Path p = new Path();

        p.lineTo(800, 500);
        Path p2 =  new Path();
        p2.lineTo(500, 300);
        Path p3 =  new Path();
        p3.lineTo(650, 100);
        mongolfiere = findViewById(R.id.mongolfiere);
        start = findViewById(R.id.buttonStart);

        animations[0] = ObjectAnimator.ofFloat(mongolfiere, ImageView.X, 800);
        animations[0].setDuration(2000);

        animations[1] = ObjectAnimator.ofFloat(mongolfiere, ImageView.Y, 900);
        animations[1].setDuration(2000);

        animations[2] = ObjectAnimator.ofFloat(mongolfiere, ImageView.X, 400);
        animations[2].setDuration(2000);

        animations[3] = ObjectAnimator.ofFloat(mongolfiere, ImageView.Y, 300);
        animations[3].setDuration(2000);

        animations[4] = ObjectAnimator.ofFloat(mongolfiere, ImageView.X, 400);
        animations[4].setDuration(2000);

        animations[5] = ObjectAnimator.ofFloat(mongolfiere, ImageView.Y, 200);
        animations[5].setDuration(2000);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animations[0], animations[1]);
        AnimatorSet set2 = new AnimatorSet();
        set.playTogether(animations[2], animations[3]);
        AnimatorSet set3 = new AnimatorSet();
        set3.playTogether(animations[4], animations[5]);
//
//        animation1 = ObjectAnimator.ofFloat(mongolfiere, ImageView.X, 800);
//        animation2 = ObjectAnimator.ofFloat(mongolfiere, ImageView.Y, 200);
//
//        AnimatorSet set = new AnimatorSet();
//        set.setDuration(2000);
//        set.playTogether(animation1, animation2);
//
            for (int i = 0; i < animations.length; i++){
                if (i == 0) {
                    start.setOnClickListener(v -> set.start());
                }
                else if (i == 2){
                        start.setOnClickListener(v -> set2.start());
                }
                else if (i == 4) {
                    start.setOnClickListener(v -> set3.start());
                }
            }

    }
}