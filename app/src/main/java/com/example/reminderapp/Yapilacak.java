package com.example.reminderapp;

public class Yapilacak {

    private String id;
    private String yapilacak;
    private String etiket;
    private String tarih;
    private String saat;
    private String durum;

    public Yapilacak() {
    }

    public Yapilacak(String id, String yapilacak, String etiket, String tarih, String saat, String durum) {
        this.id = id;
        this.yapilacak = yapilacak;
        this.etiket = etiket;
        this.tarih = tarih;
        this.saat = saat;
        this.durum = durum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYapilacak() {
        return yapilacak;
    }

    public void setYapilacak(String yapilacak) {
        this.yapilacak = yapilacak;
    }

    public String getEtiket() {
        return etiket;
    }

    public void setEtiket(String etiket) {
        this.etiket = etiket;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }
}
