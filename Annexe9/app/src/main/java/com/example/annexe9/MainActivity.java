package com.example.annexe9;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView c1, c2, c3, c4, c5, c6;


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
        c1 = findViewById(R.id.champ1);
        c2 = findViewById(R.id.champ2);
        c3 = findViewById(R.id.champ3);
        c4 = findViewById(R.id.champ4);
        c5 = findViewById(R.id.champ5);
        c6 = findViewById(R.id.champ6);

        String url = "https://api.jsonbin.io/v3/b/6733b233ad19ca34f8c9149a?meta=false";

        RequestQueue rQ = Volley.newRequestQueue(this);

        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            if (response != null){
                c1.setText("C'est un objet");
            }
            else c1.setText("Ce n'est pas un objet");

            Log.d("Réponse", response.toString());

            try {
                JSONObject s = response.getJSONObject("menu");
                String header = s.getString("header");
                c2.setText(header);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            try {
                JSONObject s = response.getJSONObject("menu");
                JSONArray items = s.getJSONArray("items");
                int compteur = items.length();
                c3.setText(String.valueOf(compteur));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            try {
                JSONObject s = response.getJSONObject("menu");
                JSONArray items = s.getJSONArray("items");
                int compteur = 0;
                for(int i = 0; i < items.length(); i++){
                    Object o = items.get(i);
                    if (!(o instanceof JSONObject)){
                        compteur++;
                    }
                }
                c4.setText(String.valueOf(compteur));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            try {
                JSONObject s = response.getJSONObject("menu");
                JSONArray items = s.getJSONArray("items");
                int compteur = 0;
                for(int i = 0; i < items.length(); i++){
                    Object o = items.get(i);
                    if (o instanceof JSONObject){
                        try {
                            String label = (((JSONObject) o).getString("label"));
                        }
                        catch (JSONException e){
                            compteur++;
                        }

                    }
                }
                c5.setText(String.valueOf(compteur));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {
            error.getStackTrace();
        });

        rQ.add(json);
    }
}