package com.toharifqi.um.ukmq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.toharifqi.um.ukmq.adapter.ProfilTabAdapter;
import com.toharifqi.um.ukmq.helpers.Config;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilActivity extends AppCompatActivity {
    FirebaseUser fUser;
    DatabaseReference userDb;
    CircleImageView profilPhoto;
    TextView txtEmailUser, txtUserName, txtTipeAkun;

    //tab stuff
    private ViewPager viewPager;
    private TabItem tabProfil, tabProduk, tabProject;
    private ViewPager viewPagerTab;
    private TabLayout tabLayout;
    private ProfilTabAdapter profilTabAdapter;
    private Button editButton;
    private String uId;
    private String profilPic;

    StorageReference firebaseStorage;

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

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        editButton = findViewById(R.id.edit_profile_btn);

        //data from product or project activity
        Bundle intent = getIntent().getExtras();
        assert intent != null;
        uId = null;

        if (getIntent().getExtras() ==  null){
            uId = fUser.getUid();
        }else if (getIntent().getExtras() != null){
            uId = intent.getString(Config.USER_ID);
            editButton.setVisibility(View.GONE);
        }

        //firebase initialization
        userDb = FirebaseDatabase.getInstance().getReference("users").child(uId);

        //initialization views
        txtUserName = findViewById(R.id.profil_username);
        txtEmailUser = findViewById(R.id.profil_email);
        txtTipeAkun = findViewById(R.id.profil_tipeakun);

        profilPhoto = findViewById(R.id.profil_photo);

        //checking what type of user
        userDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String tipeUser = dataSnapshot.child("tipe_user").getValue().toString();
                String emailUser = dataSnapshot.child("email").getValue().toString();
                String namaPemilik = dataSnapshot.child(Config.NAMA_PEMILIK).getValue().toString();
                profilPic = dataSnapshot.child(Config.PROFIL_PIC).getValue().toString();
                txtUserName.setText(namaPemilik);
                txtEmailUser.setText(emailUser);
                if (tipeUser.equals("1")){
                    txtTipeAkun.setText("Akun UKM");
                }else {
                    txtTipeAkun.setText("Akun Investor");
                }
                if (!profilPic.isEmpty() && !ProfilActivity.this.isDestroyed()){
                    Glide.with(ProfilActivity.this).load(profilPic).into(profilPhoto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //tabLayout
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabProfil = (TabItem) findViewById(R.id.tabProfil);
        tabProduk = (TabItem) findViewById(R.id.tabProduk);
        tabProject = (TabItem) findViewById(R.id.tabProject);
        viewPager = findViewById(R.id.viewPagerProfile);

        profilTabAdapter = new ProfilTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(profilTabAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    profilTabAdapter.notifyDataSetChanged();
                }else if (tab.getPosition() == 1) {
                    profilTabAdapter.notifyDataSetChanged();
                }else if (tab.getPosition() == 2) {
                    profilTabAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public void toEditProfile (View view){
        if (Config.tipe_user.equals("1")){
            Intent intent = new Intent(ProfilActivity.this, EditUkmActivity.class);
            intent.putExtra(Config.PROFIL_PIC, profilPic);
            startActivity(intent);
        }else {
            startActivity(new Intent(ProfilActivity.this, EditInvestorActivity.class));
        }
    }
}