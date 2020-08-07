package com.toharifqi.um.ukmq.model;

public class UserModel {
    String userName, email, kodePu, namaPemilik, namaMerk, noTelp, izinPirt,
    izinBpom, izinHalal, izinSni, profilJalan, profilRt, profilKecamatan, profilKabupaten;
    int tipe_user;

    public UserModel() {
    }

    public UserModel(String userName, String email, String kodePu, String namaPemilik, String namaMerk, String noTelp, String izinPirt, String izinBpom, String izinHalal, String izinSni, String profilJalan, String profilRt, String profilKecamatan, String profilKabupaten, int tipe_user) {
        this.userName = userName;
        this.email = email;
        this.kodePu = kodePu;
        this.namaPemilik = namaPemilik;
        this.namaMerk = namaMerk;
        this.noTelp = noTelp;
        this.izinPirt = izinPirt;
        this.izinBpom = izinBpom;
        this.izinHalal = izinHalal;
        this.izinSni = izinSni;
        this.profilJalan = profilJalan;
        this.profilRt = profilRt;
        this.profilKecamatan = profilKecamatan;
        this.profilKabupaten = profilKabupaten;
        this.tipe_user = tipe_user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKodePu() {
        return kodePu;
    }

    public void setKodePu(String kodePu) {
        this.kodePu = kodePu;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public void setNamaPemilik(String namaPemilik) {
        this.namaPemilik = namaPemilik;
    }

    public String getNamaMerk() {
        return namaMerk;
    }

    public void setNamaMerk(String namaMerk) {
        this.namaMerk = namaMerk;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getIzinPirt() {
        return izinPirt;
    }

    public void setIzinPirt(String izinPirt) {
        this.izinPirt = izinPirt;
    }

    public String getIzinBpom() {
        return izinBpom;
    }

    public void setIzinBpom(String izinBpom) {
        this.izinBpom = izinBpom;
    }

    public String getIzinHalal() {
        return izinHalal;
    }

    public void setIzinHalal(String izinHalal) {
        this.izinHalal = izinHalal;
    }

    public String getIzinSni() {
        return izinSni;
    }

    public void setIzinSni(String izinSni) {
        this.izinSni = izinSni;
    }

    public String getProfilJalan() {
        return profilJalan;
    }

    public void setProfilJalan(String profilJalan) {
        this.profilJalan = profilJalan;
    }

    public String getProfilRt() {
        return profilRt;
    }

    public void setProfilRt(String profilRt) {
        this.profilRt = profilRt;
    }

    public String getProfilKecamatan() {
        return profilKecamatan;
    }

    public void setProfilKecamatan(String profilKecamatan) {
        this.profilKecamatan = profilKecamatan;
    }

    public String getProfilKabupaten() {
        return profilKabupaten;
    }

    public void setProfilKabupaten(String profilKabupaten) {
        this.profilKabupaten = profilKabupaten;
    }

    public int getTipe_user() {
        return tipe_user;
    }

    public void setTipe_user(int tipe_user) {
        this.tipe_user = tipe_user;
    }
}
