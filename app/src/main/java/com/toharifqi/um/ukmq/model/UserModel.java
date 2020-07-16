package com.toharifqi.um.ukmq.model;

public class UserModel {
    String userName, email, no_telp, jalan_desa, rt_rw, kecamatan, kab_kota;
    int tipe_user;

    public UserModel() {
    }


    public UserModel(String userName, String email, String no_telp, String jalan_desa, String rt_rw, String kecamatan, String kab_kota, int tipe_user) {
        this.userName = userName;
        this.email = email;
        this.no_telp = no_telp;
        this.jalan_desa = jalan_desa;
        this.rt_rw = rt_rw;
        this.kecamatan = kecamatan;
        this.kab_kota = kab_kota;
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

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getJalan_desa() {
        return jalan_desa;
    }

    public void setJalan_desa(String jalan_desa) {
        this.jalan_desa = jalan_desa;
    }

    public String getRt_rw() {
        return rt_rw;
    }

    public void setRt_rw(String rt_rw) {
        this.rt_rw = rt_rw;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKab_kota() {
        return kab_kota;
    }

    public void setKab_kota(String kab_kota) {
        this.kab_kota = kab_kota;
    }

    public int getTipe_user() {
        return tipe_user;
    }

    public void setTipe_user(int tipe_user) {
        this.tipe_user = tipe_user;
    }
}
