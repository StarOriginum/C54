package com.example.annexe8b;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TtitleAnimation extends AppCompatActivity {

    ObjectAnimator animation, animation2, animation3;
    TextView text;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ttitle_animation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        text = findViewById(R.id.textView);
        button = findViewById(R.id.button4);

        animation = ObjectAnimator.ofFloat(text, TextView.SCALE_X, 3);


        animation2 = ObjectAnimator.ofFloat(text, TextView.SCALE_Y, 3);


        animation3 = ObjectAnimator.ofFloat(text, TextView.ALPHA, 1);


        AnimatorSet set = new AnimatorSet();
        set.setDuration(3000);
        set.setInterpolator(new BounceInterpolator());
        set.playTogether(animation, animation2, animation3);

        button.setOnClickListener(v -> set.start());
    }
}