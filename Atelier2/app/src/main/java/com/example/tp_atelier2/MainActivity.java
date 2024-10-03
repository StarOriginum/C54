package com.example.tp_atelier2;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    Vector<Hashtable<String, Object>> vect = new Vector<>();
    ListView liste;
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

        RequestQueue rQ = Volley.newRequestQueue(this);
        String url = "https://api.jsonbin.io/v3/b/637056232b3499323bfe110a?meta=false";

        /*StringRequest stringRequest = new StringRequest(Request.Method.GET, url, s -> Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show(), Throwable::printStackTrace);

        rQ.add(stringRequest);*/
        JsonObjectRequest jor = new JsonObjectRequest(url, jsonObject -> {
            try {
                JSONArray tab = (JSONArray) jsonObject.get("articles");
                for(int i = 0; i < tab.length(); i++){
                   JSONObject produit = tab.getJSONObject(i);
                   String nom = (String)produit.get("nom");
                   String prix = String.valueOf(produit.get("prix"));
                   Hashtable<String, Object> temp = new Hashtable<>();
                   temp.put("nom", nom);
                   temp.put("prix", prix);
                   vect.add(temp);
                }

                SimpleAdapter adapter = new SimpleAdapter(this, vect, R.layout.un_item,
                                                                    new String[]{"nom", "prix"},
                                                                    new int[]{R.id.textView, R.id.textView2});

                liste.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace){

        };
        rQ.add(jor);



    }
}