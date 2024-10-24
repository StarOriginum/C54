package com.example.tp1_musique;

public class Chansons implements Sujet {
    private String id;
    private String title;
    private String album;
    private String artist;
    private String genre;
    private String source;
    private String image;
    private int trackNumber;
    private int TotalTrackCount;
    private long duration;
    private String site;

    private ObservateurChangement obs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getTotalTrackCount() {
        return TotalTrackCount;
    }

    public void setTotalTrackCount(int totalTrackCount) {
        TotalTrackCount = totalTrackCount;
    }

    public long getDuration() {
        avertirObservateurs();
        return duration;

    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public void ajouterObservateur(ObservateurChangement obs) {

    }

    @Override
    public void enleverObservateur(ObservateurChangement obs) {

    }

    @Override
    public void avertirObservateurs() {

    }
}
