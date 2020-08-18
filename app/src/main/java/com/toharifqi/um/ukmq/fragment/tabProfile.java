package com.toharifqi.um.ukmq.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toharifqi.um.ukmq.ProfilActivity;
import com.toharifqi.um.ukmq.R;
import com.toharifqi.um.ukmq.helpers.Config;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class tabProfile extends Fragment {
    FirebaseAuth fAuth;
    DatabaseReference userDb;
    TextView txtKodePU, txtNamaUsaha, txtNamaPemilik,
            txtNamaMerk, txtTelepon, txtPIRT, txtBPOM, txtHalal, txtSNI, txtJalan, txtRtRw, txtKecamatan,
            txtKabupaten, txtInvestorTelepon, txtInvestorJalan, txtInvestorRtRw, txtInvestorKecamatan, txtInvestorKabupaten;
    CardView cardUkmProfil, cardUkmIzin, cardUkmAlamat, cardInvestorProfil;

    public tabProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_profile, container, false);

        //firebase initialization
        fAuth = FirebaseAuth.getInstance();
        userDb = FirebaseDatabase.getInstance().getReference("users").child(fAuth.getUid());

        //initialization views
        txtKodePU = view.findViewById(R.id.profil_pu);
        txtNamaUsaha = view.findViewById(R.id.profil_usaha);
        txtNamaPemilik = view.findViewById(R.id.profil_pemilik);
        txtNamaMerk = view.findViewById(R.id.profil_merk);
        txtTelepon = view.findViewById(R.id.profil_telp);
        txtPIRT = view.findViewById(R.id.profil_pirt);
        txtBPOM = view.findViewById(R.id.profil_bpom);
        txtHalal = view.findViewById(R.id.profil_halal);
        txtSNI = view.findViewById(R.id.profil_sni);
        txtJalan = view.findViewById(R.id.profil_jalan);
        txtRtRw = view.findViewById(R.id.profil_rtrw);
        txtKecamatan = view.findViewById(R.id.profil_kecamatan);
        txtKabupaten = view.findViewById(R.id.profil_kabupaten);

        txtInvestorTelepon = view.findViewById(R.id.profilinvestor_telp);
        txtInvestorJalan = view.findViewById(R.id.profilinvestor_jalan);
        txtInvestorRtRw = view.findViewById(R.id.profilinvestor_rtrw);
        txtInvestorKecamatan = view.findViewById(R.id.profilinvestor_kecamatan);
        txtInvestorKabupaten = view.findViewById(R.id.profilinvestor_kabupaten);

        cardUkmProfil = view.findViewById(R.id.cardprofil_ukm);
        cardInvestorProfil = view.findViewById(R.id.cardprofil_investor);
        cardUkmIzin = view.findViewById(R.id.cardizin_ukm);
        cardUkmAlamat = view.findViewById(R.id.cardalamat_ukm);

        //checking what type of user
        userDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String tipeUser = dataSnapshot.child("tipe_user").getValue().toString();

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

                if (!tipeUser.equals("1")){
                    cardUkmAlamat.setVisibility(View.GONE);
                    cardUkmProfil.setVisibility(View.GONE);
                    cardUkmIzin.setVisibility(View.GONE);
                    cardInvestorProfil.setVisibility(View.VISIBLE);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}