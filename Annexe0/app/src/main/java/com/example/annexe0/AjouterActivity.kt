package com.example.annexe0

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.*

class AjouterActivity : AppCompatActivity() {
    // Ecouteur ec;
    lateinit var ajouter: Button;
    lateinit var memoTexte: EditText;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_ajouter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ajouter = findViewById(R.id.memo)
        memoTexte = findViewById(R.id.memoTexte)

        val ec = Ecouteur()

        ajouter.setOnClickListener(ec)


        //ec = new Ecouteur();
    }

    inner class Ecouteur : View.OnClickListener{
        override fun onClick(v: View?) {

            var texteMemo = memoTexte.text.toString()

            val fos : FileOutputStream = openFileOutput("fichier.txt", MODE_APPEND) // Écrit à la fin du fichier sans écraser le contenu déjà existant
            val osw = OutputStreamWriter(fos)
            val bw = BufferedWriter(osw)

            bw.write(texteMemo)
            bw.newLine()
            bw.close()
            finish() // pour revenir au menu principal
        }

    }

    /*public class Ecouteur implements View.OnClickListener{
            String content;
            String path = "C:/travail/C54/Annexe0";
            @Override
            public void onClick(View v) {
                if (v == ajouter){
                    
                    }
                }
            }*/
}
