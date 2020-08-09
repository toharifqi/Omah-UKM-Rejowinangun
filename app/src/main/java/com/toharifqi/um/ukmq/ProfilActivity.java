package com.toharifqi.um.ukmq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toharifqi.um.ukmq.helpers.Config;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    DatabaseReference userDb;
    CircleImageView profilPhoto;
    TextView txtEmailUser, txtUserName, txtTipeAkun, txtKodePU, txtNamaUsaha, txtNamaPemilik,
    txtNamaMerk, txtTelepon, txtPIRT, txtBPOM, txtHalal, txtSNI, txtJalan, txtRtRw, txtKecamatan,
    txtKabupaten, txtInvestorTelepon, txtInvestorJalan, txtInvestorRtRw, txtInvestorKecamatan, txtInvestorKabupaten;
    CardView cardUkmProfil, cardUkmIzin, cardUkmAlamat, cardInvestorProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        //fab
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //firebase initialization
        fAuth = FirebaseAuth.getInstance();
        userDb = FirebaseDatabase.getInstance().getReference("users").child(fAuth.getUid());

        //initialization views
        txtUserName = findViewById(R.id.profil_email);
        txtEmailUser = findViewById(R.id.profil_email);
        txtTipeAkun = findViewById(R.id.profil_tipeakun);
        txtKodePU = findViewById(R.id.profil_pu);
        txtNamaUsaha = findViewById(R.id.profil_usaha);
        txtNamaPemilik = findViewById(R.id.profil_pemilik);
        txtNamaMerk = findViewById(R.id.profil_merk);
        txtTelepon = findViewById(R.id.profil_telp);
        txtPIRT = findViewById(R.id.profil_pirt);
        txtBPOM = findViewById(R.id.profil_bpom);
        txtHalal = findViewById(R.id.profil_halal);
        txtSNI = findViewById(R.id.profil_sni);
        txtJalan = findViewById(R.id.profil_jalan);
        txtRtRw = findViewById(R.id.profil_rtrw);
        txtKecamatan = findViewById(R.id.profil_kecamatan);
        txtKabupaten = findViewById(R.id.profil_kabupaten);

        txtInvestorTelepon = findViewById(R.id.profilinvestor_telp);
        txtInvestorJalan = findViewById(R.id.profilinvestor_jalan);
        txtInvestorRtRw = findViewById(R.id.profilinvestor_rtrw);
        txtInvestorKecamatan = findViewById(R.id.profilinvestor_kecamatan);
        txtInvestorKabupaten = findViewById(R.id.profilinvestor_kabupaten);

        cardUkmProfil = findViewById(R.id.cardprofil_ukm);
        cardInvestorProfil = findViewById(R.id.cardprofil_investor);
        cardUkmIzin = findViewById(R.id.cardizin_ukm);
        cardUkmAlamat = findViewById(R.id.cardalamat_ukm);

        profilPhoto = findViewById(R.id.profil_photo);

        //checking what type of user
        userDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String tipeUser = dataSnapshot.child("tipe_user").getValue().toString();
                String emailUser = dataSnapshot.child("email").getValue().toString();

                String namaUserUsaha = dataSnapshot.child(Config.NAMA_USAHA).getValue().toString();
                String kodePu = dataSnapshot.child(Config.KODE_PU).getValue().toString();
                String namaPemilik = dataSnapshot.child(Config.NAMA_PEMILIK).getValue().toString();
                String namaMerk = dataSnapshot.child(Config.NAMA_MERK).getValue().toString();
                String noTelepon = dataSnapshot.child(Config.NO_TELEPON).getValue().toString();
                String izinPirt = dataSnapshot.child(Config.IZIN_PIRT).getValue().toString();
                String izinBpom = dataSnapshot.child(Config.IZIN_BPOM).getValue().toString();
                String izinHalal = dataSnapshot.child(Config.IZIN_HALAL).getValue().toString();
                String izinSni = dataSnapshot.child(Config.IZIN_SNI).getValue().toString();
                String jalan = dataSnapshot.child(Config.JALAN).getValue().toString();
                String rtRw = dataSnapshot.child(Config.RT_RW).getValue().toString();
                String kecamatan = dataSnapshot.child(Config.KECAMATAN).getValue().toString();
                String kabupaten = dataSnapshot.child(Config.KABUPATEN).getValue().toString();
                String profilPic = dataSnapshot.child(Config.PROFIL_PIC).getValue().toString();
                txtUserName.setText(namaPemilik);
                txtEmailUser.setText(emailUser);
                if (tipeUser.equals("1")){
                    txtTipeAkun.setText("Akun UKM");
                }else {
                    cardUkmAlamat.setVisibility(View.GONE);
                    cardUkmProfil.setVisibility(View.GONE);
                    cardUkmIzin.setVisibility(View.GONE);
                    cardInvestorProfil.setVisibility(View.VISIBLE);
                    txtTipeAkun.setText("Akun Investor");
                }
                if (!namaUserUsaha.isEmpty()){
                    txtNamaUsaha.setText(namaUserUsaha);
                }
                if (!kodePu.isEmpty()){
                    txtKodePU.setText(kodePu);
                }
                if (!namaPemilik.isEmpty()){
                    txtNamaPemilik.setText(namaPemilik);
                }
                if (!namaMerk.isEmpty()){
                    txtNamaMerk.setText(namaMerk);
                }
                if (!noTelepon.isEmpty()){
                    txtTelepon.setText(noTelepon);
                    txtInvestorTelepon.setText(noTelepon);
                }
                if (!izinPirt.isEmpty()){
                    txtPIRT.setText(izinPirt);
                }
                if(!izinBpom.isEmpty()){
                    txtBPOM.setText(izinBpom);
                }
                if (!izinHalal.isEmpty()){
                    txtHalal.setText(izinHalal);
                }
                if (!izinSni.isEmpty()){
                    txtSNI.setText(izinSni);
                }
                if (!jalan.isEmpty()){
                    txtJalan.setText(jalan);
                    txtInvestorJalan.setText(jalan);
                }
                if (!rtRw.isEmpty()){
                    txtRtRw.setText(rtRw);
                    txtInvestorRtRw.setText(rtRw);
                }
                if (!kecamatan.isEmpty()){
                    txtKecamatan.setText(kecamatan);
                    txtInvestorKecamatan.setText(kecamatan);
                }
                if (!kabupaten.isEmpty()){
                    txtKabupaten.setText(kabupaten);
                    txtInvestorKabupaten.setText(kabupaten);
                }
                if (!profilPic.isEmpty()){
                    Glide.with(ProfilActivity.this).load(profilPic).into(profilPhoto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void toEditProfile (View view){

        if (Config.tipe_user.equals("1")){
            startActivity(new Intent(ProfilActivity.this, EditUkmActivity.class));
        }else {
            startActivity(new Intent(ProfilActivity.this, EditInvestorActivity.class));
        }
    }
}