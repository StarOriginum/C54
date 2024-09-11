package com.example.annexe0

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.*
import java.time.LocalDate

class AjouterActivity : AppCompatActivity() {
    // Ecouteur ec;
    lateinit var ajouter: Button;
    lateinit var echeance: Button;
    lateinit var memoTexte: EditText;
    lateinit var date: TextView;
    var dateChoisie: LocalDate = LocalDate.now()


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
        echeance =findViewById(R.id.button2)
        date = findViewById(R.id.textView)

        val ec = Ecouteur()

        ajouter.setOnClickListener(ec)
        echeance.setOnClickListener(ec)


        //ec = new Ecouteur();
    }

    inner class Ecouteur : View.OnClickListener, OnDateSetListener{
        override fun onClick(v: View?) {

            var texteMemo = memoTexte.text.toString()
            var dateEcheance = date.text.toString()
            val gestionMemos = GestionMemos.getInstance()

            if (v == echeance){
                val d:DatePickerDialog = DatePickerDialog(this@AjouterActivity)
                d.setOnDateSetListener(this)
                d.show()
            }
            else
            {
                GestionMemos.getInstance().ajouterMemos(Memo(texteMemo, dateChoisie))
                finish() // pour revenir au menu principal
            }

        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            dateChoisie = LocalDate.of(year, month+1, dayOfMonth)
            date.setText(dateChoisie.toString())
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
