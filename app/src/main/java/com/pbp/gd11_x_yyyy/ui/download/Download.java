package com.pbp.gd11_x_yyyy.ui.download;

import java.io.Serializable;

public class Download implements Serializable {
    private String judul, namaFile, link;
    private int id;

    public Download(String judul, String namaFile, String link, int id) {
        this.judul = judul;
        this.namaFile = namaFile;
        this.link = link;
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getNamaFile() {
        return namaFile;
    }

    public void setNamaFile(String namaFile) {
        this.namaFile = namaFile;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
