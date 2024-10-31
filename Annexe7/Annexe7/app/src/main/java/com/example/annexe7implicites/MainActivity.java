package com.example.annexe7implicites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button appel, ville, photo, message;
    ImageView image;
    ActivityResultLauncher<Intent> launcher;

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
        image = findViewById(R.id.imageView);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Bundle extras = result.getData().getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        });

        appel = findViewById(R.id.boutonAppel);
        ville = findViewById(R.id.boutonVille);
        photo = findViewById(R.id.boutonPhoto);
        message = findViewById(R.id.boutonMessage);


        appel.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+789789555"));
            startActivity(i);
        });
        ville.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Hawkesbury,+Ontario,+Canada "));
            startActivity(i);
        });
        photo.setOnClickListener(v -> {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            launcher.launch(i);
        });
        message.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            startActivity(i);
        });

    }
}