package com.example.annexe3b

import android.content.Context
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    lateinit var seek1: SeekBar
    lateinit var seek2: SeekBar
    lateinit var seek3: SeekBar
    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        seek1 = findViewById(R.id.seek1)
        seek2 = findViewById(R.id.seek2)
        seek3 = findViewById(R.id.seek3)

        loadSeekBarProgress()

    }

    override fun onStop() {
        super.onStop()
        val fis = openFileOutput("texte.ser", MODE_PRIVATE)
        val oos = ObjectOutputStream(fis)
        oos.use {
            it.writeObject(seek1.progress)
            it.writeObject(seek2.progress)
            it.writeObject(seek3.progress)
        }
    }

    fun loadSeekBarProgress(){
        try{
            val fis = openFileInput("texte.ser")
            val oos = ObjectInputStream(fis)
            oos.use {
                seek1.setProgress(oos.readObject() as Int)
                seek2.setProgress(oos.readObject() as Int)
                seek3.setProgress(oos.readObject() as Int)
            }
        }
        catch(fnfe: FileNotFoundException)
        {
            fnfe.printStackTrace()
            Toast.makeText(context, "No file found, first use the app",
                            Toast.LENGTH_SHORT).show()
        }

    }
}