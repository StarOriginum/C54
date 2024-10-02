package com.example.examen1_kt

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.time.LocalDateTime
import java.util.Date
import java.util.Vector

class MainActivity : AppCompatActivity() {
    lateinit var langages: Langages
    lateinit var nbChamp: TextView
    lateinit var classement2012:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lectureFichierLangage()

        nbChamp = findViewById(R.id.champClass2012)
        classement2012 = findViewById(R.id.Class2012)







    }
    fun lectureFichierLangage(){
        var classements:Vector<Langages> = Vector()
        var compteur:Int = 0

        val fis: FileInputStream = openFileInput("langage.txt")
        val isw = InputStreamReader(fis)
        val br = BufferedReader(isw)

        while (br.readLine() != null){
            classements.add(langages)

            if(langages.classement2012 == 99)
                compteur++
        }
        classement2012.text = compteur.toString()
    }

    fun afficherHeures(){
        val date =

        val fos = openFileOutput("date.ser", MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.use {
            it.writeObject(date)
        }
    }
}