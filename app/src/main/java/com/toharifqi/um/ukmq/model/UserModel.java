package com.toharifqi.um.ukmq.model;

public class UserModel {
    String userName, email, kodePu, namaPemilik, namaMerk, namaUsaha, noTelepon,
            izinPirt, izinBpom, izinHalal, izinSni, profilJalan, profilRt,
            profilKecamatan, profilKabupaten, profilPicture;
    int tipe_user;

    public UserModel() {
    }

    public UserModel(String userName, String email, String izinPu, String namaPemilik, String namaMerk, String namaUsaha, String noTelepon, String izinPirt, String izinBpom, String izinHalal, String kodeSni, String profilJalan, String profilRt, String profilKecamatan, String profilKabupaten, String profilPic, int tipe_user) {
        this.userName = userName;
        this.email = email;
        this.kodePu = izinPu;
        this.namaPemilik = namaPemilik;
        this.namaMerk = namaMerk;
        this.namaUsaha = namaUsaha;
        this.noTelepon = noTelepon;
        this.izinPirt = izinPirt;
        this.izinBpom = izinBpom;
        this.izinHalal = izinHalal;
        this.izinSni = kodeSni;
        this.profilJalan = profilJalan;
        this.profilRt = profilRt;
        this.profilKecamatan = profilKecamatan;
        this.profilKabupaten = profilKabupaten;
        this.profilPicture = profilPic;
        this.tipe_user = tipe_user;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getKodePu() {
        return kodePu;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public String getNamaMerk() {
        return namaMerk;
    }

    public String getNamaUsaha() {
        return namaUsaha;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public String getIzinPirt() {
        return izinPirt;
    }

    public String getIzinBpom() {
        return izinBpom;
    }

    public String getIzinHalal() {
        return izinHalal;
    }

    public String getIzinSni() {
        return izinSni;
    }

    public String getProfilJalan() {
        return profilJalan;
    }

    public String getProfilRt() {
        return profilRt;
    }

    public String getProfilKecamatan() {
        return profilKecamatan;
    }

    public String getProfilKabupaten() {
        return profilKabupaten;
    }

    public String getProfilPicture() {
        return profilPicture;
    }

    public int getTipe_user() {
        return tipe_user;
    }
}
