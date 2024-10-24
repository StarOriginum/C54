package com.example.tp1_musique;

public interface Sujet {

    public void ajouterObservateur(ObservateurChangement obs);
    public void enleverObservateur(ObservateurChangement obs);
    public void avertirObservateurs();
}
