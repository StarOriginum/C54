package com.example.annexe4;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    private String prenom, nom;

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
