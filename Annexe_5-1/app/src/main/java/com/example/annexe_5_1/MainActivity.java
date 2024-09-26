package com.example.annexe_5_1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Hashtable;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    ListView liste;

    Vector<Hashtable<String, Object>> vect = new Vector<>();

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

        liste = findViewById(R.id.liste);

        Hashtable<String, Object> temp = new Hashtable<>();
        temp.put("position", "3");
        temp.put("nom", "Touch Me");
        temp.put("date", "22/03/86");
        temp.put("image", R.drawable.touchme);
        vect.add(temp);

        temp = new Hashtable<>();
        temp.put("position", "8");
        temp.put("nom", "Nothing's gonna stop me now");
        temp.put("date", "30/05/86");
        temp.put("image", R.drawable.nothing);
        vect.add(temp);

        temp = new Hashtable<>();
        temp.put("position", "31");
        temp.put("nom", "Santa Maria");
        temp.put("date", "28/03/98");
        temp.put("image", R.drawable.santamaria);
        vect.add(temp);

        temp = new Hashtable<>();
        temp.put("position", "108");
        temp.put("nom", "Hot boy");
        temp.put("date", "10/04/2018");
        temp.put("image", R.drawable.hotboy);
        vect.add(temp);

        SimpleAdapter adapter = new SimpleAdapter(this, vect, R.layout.un_item,
                new String[]{"position", "nom", "date", "image"},
                new int[]{R.id.textPosition, R.id.textNom, R.id.textDate, R.id.imageView});

        liste.setAdapter(adapter);
        // Ecouteur ec = new Ecouteur();
        liste.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Hashtable<String, Object> rep = vect.get(position);
            String nom = (String) rep.get("nom");
            Toast.makeText(MainActivity.this, nom, Toast.LENGTH_LONG).show();});


        // ceci est une interface fonctionnelle = interface qui n'a qu'une seule methode
        }
}