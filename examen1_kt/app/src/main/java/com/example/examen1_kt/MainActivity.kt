package com.example.examen1_kt

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Vector

class MainActivity : AppCompatActivity() {
    lateinit var langages: Vector<Langages>
    lateinit var nbChamp: TextView
    lateinit var classement2012:TextView
    lateinit var texteDate:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        langages = Vector()
        texteDate = findViewById(R.id.texteDate)

        try {
            val derniereFois = deserialisation()
            val formatter = DateTimeFormatter.ofPattern("hh:mm dd MM yyyy")
            texteDate.setText("derniere utilisation: " + derniereFois.format(formatter))
        } catch (e: FileNotFoundException){
            texteDate.setText("1ere utilisation")
        }

        nbChamp = findViewById(R.id.champClass2012)
        classement2012 = findViewById(R.id.Class2012)


        lectureFichierLangage()

        nbChamp.setText(trouver99())




    }


    override fun onStop() {
        super.onStop()
        serialisation()
    }
    fun lectureFichierLangage() {



        val fis: FileInputStream = openFileInput("langage.txt")
        val obj = InputStreamReader(fis)
        val br = BufferedReader(obj)
        br.readLine()
        fis.bufferedReader().use {
            var line = br.readLine()

            while (line != null) {
                var tab = line.split(",")
                langages.add(Langages(tab[0], tab[1].toInt(), tab[2].toInt(), tab[3].toInt()))
                line = br.readLine()
            }
        }
    }

    fun trouver99():Int{
        var compteur:Int = 0
        for(l:Langages in langages){
            if (l.classement2012 == 99)
                compteur++
        }
        return compteur
    }

    fun serialisation(){

        val fos = openFileOutput("date.ser", MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)

        oos.use {oos ->
            oos.writeObject(LocalDateTime.now())
        }
    }

    fun deserialisation(): LocalDateTime {
        openFileInput("date.ser").use { fos ->
            ObjectInputStream(fos).use { oos ->
                return oos.readObject() as LocalDateTime
            }
        }
    }
}