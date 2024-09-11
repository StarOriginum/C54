package com.example.annexe0;

import android.content.Context;

import java.util.ArrayList;

public class GestionMemos {

    private static GestionMemos instance;

    private ArrayList<Memo> listeMemos;

    public static GestionMemos getInstance(){
        if (instance == null){
            instance = new GestionMemos();
        }
        return instance;
    }

    private GestionMemos(){
        listeMemos = new ArrayList<>();
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
}
