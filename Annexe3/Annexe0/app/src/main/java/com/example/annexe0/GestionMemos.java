package com.example.annexe0;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GestionMemos {

    private static GestionMemos instance;

     ArrayList<Memo> listeMemos;
    private Context contexte;

    public static GestionMemos getInstance(Context contexte){
        if (instance == null){
            instance = new GestionMemos(contexte);
        }
        return instance;
    }

    private GestionMemos(Context contexte){
        listeMemos = new ArrayList<>();
        this.contexte = contexte;
    }

    public ArrayList<Memo> getListeMemos(){
        return listeMemos;
    }

    public void ajouterMemos(Memo memo){
        listeMemos.add(memo);
    }

    public void voirMemos(){
        if (listeMemos.isEmpty()){
            System.out.println("Aucun mémos dans la liste");
        } else{
            for (Memo memo: listeMemos){
                System.out.print(memo);
            }
        }
    }

    public void serialiserListe() throws Exception
    {
        // try-with - resource
        try(
        FileOutputStream fos = contexte.openFileOutput("fichier.ser", Context.MODE_APPEND);
        // beffer spécial pour les objets
        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(listeMemos);
        }


    }

    public ArrayList<Memo> deserialiserListe() throws Exception
    {

        ArrayList<Memo> temp = null;
        try(
        FileInputStream fos = contexte.openFileInput("fichier.ser");
        // beffer spécial pour les objets
        ObjectInputStream ois = new ObjectInputStream(fos))
        {
            temp = (ArrayList<Memo>) ois.readObject();
        }

        return temp;




    }
}
