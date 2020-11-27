package com.pbp.gd11_x_yyyy.ui.pdf;

import java.io.Serializable;

public class Book implements Serializable {
    private int idBuku;
    private String namaBuku, pengarang, gambar;
    private Double harga;

    public Book (int idBuku, String namaBuku, String pengarang, Double harga, String gambar) {
        this.idBuku = idBuku;
        this.namaBuku = namaBuku;
        this.pengarang = pengarang;
        this.harga = harga;
        this.gambar = gambar;
    }

    public Book (String namaBuku, String pengarang, Double harga) {
        this.namaBuku = namaBuku;
        this.pengarang = pengarang;
        this.harga = harga;
    }

    public Book (String namaBuku, String pengarang, Double harga, String gambar){
        this.namaBuku = namaBuku;
        this.pengarang = pengarang;
        this.harga = harga;
        this.gambar = gambar;
    }

    public Double getHarga() {
        return harga;
    }

    public int getIdBuku() {
        return idBuku;
    }

    public String getNamaBuku() {
        return namaBuku;
    }

    public String getPengarang() {
        return pengarang;
    }

    public String getGambar() {
        return gambar;
    }
}

