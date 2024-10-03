package com.example.tp_atelier2;

public class Produit {
    private String nom;
    private double prix;

    public Produit(Builder b) {
        this.nom = b.nom;
        this.prix = b.prix;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }
public static class Builder {
    private String nom;
    private double prix;


    public Builder() {
    }

    public Builder setNom(String nomB) {
        this.nom = nomB;
        return this;
    }

    public Builder setPrix(double prixB) {
        this.prix = prixB;
        return this;
    }

    public Produit build(){
        return new Produit(this);
    }

}


}
