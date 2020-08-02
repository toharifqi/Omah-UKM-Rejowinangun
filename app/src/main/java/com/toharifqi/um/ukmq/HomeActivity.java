package com.toharifqi.um.ukmq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.nio.file.attribute.GroupPrincipal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth fAuth;
    DatabaseReference userDb, carouselDb;
    Toolbar toolbar;
    ImageView menuIcon;
    CoordinatorLayout contentView;

    String tipeUser;

    //Drawer menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    //constant
    static final float END_SCALE =  0.7f;

    ImageSlider imageSlider, contentSlider;
    FloatingActionButton fab, fabProduct, fabProject;
    TextView textProduct, textProject;
    private boolean isFabOpen;
    private Animation fabOpenAnim, fabCloseAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("UMKMQ");

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);

        collapsingToolbarLayout.setTitleEnabled(false);

        //calendar stuffs
        TextView txtDate = findViewById(R.id.dateText);
        final TextView txtGreeting = findViewById(R.id.greetingText);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time =>" + c);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
        TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");
        tz.getDisplayName(false, TimeZone.SHORT, Locale.ENGLISH);
        df.setTimeZone(tz);
        String formattedDate = df.format(c);

        //firebase initialization
        fAuth = FirebaseAuth.getInstance();
        carouselDb = FirebaseDatabase.getInstance().getReference("homeCarousel");

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("k");
        final int greeting = Integer.parseInt(String.valueOf(dateFormat.format(c)));


        //drawer menu stuff
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        TextView userEmail = headerView.findViewById(R.id.user_email);
        final TextView authCond = findViewById(R.id.auth_condition);
        final TextView userNameD = headerView.findViewById(R.id.user_name);
        fab = findViewById(R.id.fab);
        fabProduct = findViewById(R.id.fab_product);
        fabProject = findViewById(R.id.fab_project);
        textProduct = findViewById(R.id.product_text);
        textProject = findViewById(R.id.project_text);
        isFabOpen = false;

        //fab animation
        fabOpenAnim = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.fab_open);
        fabCloseAnim = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.fab_close);



        if (fAuth.getCurrentUser()!=null){
            userDb = FirebaseDatabase.getInstance().getReference("users").child(fAuth.getUid());
            userEmail.setText(fAuth.getCurrentUser().getEmail());
            userDb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String userName = dataSnapshot.child("userName").getValue().toString();
                    userNameD.setText(userName);
                    tipeUser = dataSnapshot.child("tipe_user").getValue().toString();
                    if (greeting > 0 && greeting <= 11) {
                        txtGreeting.setText("Selamat Pagi, " + userName);
                    } else if (greeting > 11 && greeting <= 15) {
                        txtGreeting.setText("Selamat Siang, " + userName);
                    } else if (greeting > 15 && greeting <= 19) {
                        txtGreeting.setText("Selamat Sore, " + userName);
                    } else if (greeting > 19 && greeting <= 24) {
                        txtGreeting.setText("Selamat Malam, " + userName);
                    } else {
                        txtGreeting.setText("gagal_memuat_data");
                    }
                    if (tipeUser.equals("1")){
                        authCond.setText("Anda masuk sebagai UKM");
                    }else {
                        authCond.setText("Anda masuk sebagai Investor");
                        fab.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else {
            if (greeting > 0 && greeting <= 11) {
                txtGreeting.setText("Selamat Pagi!");
            } else if (greeting > 11 && greeting <= 15) {
                txtGreeting.setText("Selamat Siang!");
            } else if (greeting > 15 && greeting <= 19) {
                txtGreeting.setText("Selamat Sore!");
            } else if (greeting > 19 && greeting <= 24) {
                txtGreeting.setText("Selamat Malam!");
            } else {
                txtGreeting.setText("gagal_memuat_data");
            }
            userNameD.setVisibility(View.GONE);
            userEmail.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.loggedout_menu);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFabOpen){
                    fabProject.startAnimation(fabCloseAnim);
                    fabProduct.startAnimation(fabCloseAnim);
                    textProduct.setVisibility(View.INVISIBLE);
                    textProject.setVisibility(View.INVISIBLE);
                    isFabOpen = false;
                }else {
                    fabProject.startAnimation(fabOpenAnim);
                    fabProduct.startAnimation(fabOpenAnim);
                    textProduct.setVisibility(View.VISIBLE);
                    textProject.setVisibility(View.VISIBLE);

                    isFabOpen = true;
                }
            }
        });

        fabProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AddProduct.class));
            }
        });

        fabProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AddProject.class));
            }
        });


        txtDate.setText(formattedDate);

        imageSlider = findViewById(R.id.image_slider);
        contentSlider = findViewById(R.id.content_slider);
        final List<SlideModel> slideList = new ArrayList<>();
        final List<SlideModel> contentList = new ArrayList<>();
        carouselDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //this method to retrieve url from firebase
                String url0 = dataSnapshot.child("background").child("0").getValue().toString();
                String url1 = dataSnapshot.child("background").child("1").getValue().toString();
                String url2 = dataSnapshot.child("background").child("2").getValue().toString();
                String url3 = dataSnapshot.child("background").child("3").getValue().toString();
                String urlContent0 = dataSnapshot.child("content").child("0").getValue().toString();
                String urlContent1 = dataSnapshot.child("content").child("1").getValue().toString();
                String urlContent2 = dataSnapshot.child("content").child("2").getValue().toString();
                String urlContent3 = dataSnapshot.child("content").child("3").getValue().toString();
                slideList.add(new SlideModel(url0, ScaleTypes.CENTER_CROP));
                slideList.add(new SlideModel(url1, ScaleTypes.CENTER_CROP));
                slideList.add(new SlideModel(url2, ScaleTypes.CENTER_CROP));
                slideList.add(new SlideModel(url3, ScaleTypes.CENTER_CROP));
                contentList.add(new SlideModel(urlContent0, ScaleTypes.FIT));
                contentList.add(new SlideModel(urlContent1, ScaleTypes.FIT));
                contentList.add(new SlideModel(urlContent2, ScaleTypes.FIT));
                contentList.add(new SlideModel(urlContent3, ScaleTypes.FIT));
                imageSlider.setImageList(slideList, ScaleTypes.CENTER_CROP);
                contentSlider.setImageList(contentList, ScaleTypes.FIT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imageSlider.setImageList(slideList, ScaleTypes.CENTER_CROP);
        contentSlider.setImageList(slideList, ScaleTypes.CENTER_CROP);

        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content_view);



        navigationDrawer();
    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //scale the view
                final float diffScaldedOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaldedOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                //translate the view
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff  = contentView.getWidth() * diffScaldedOffset/2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_log_out:
                fAuth.signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.nav_category:
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.menu_cat);
                break;
            case R.id.nav_back:
                navigationView.getMenu().clear();
                if (fAuth.getCurrentUser()!=null){
                    navigationView.inflateMenu(R.menu.main_menu);
                }else{
                    navigationView.inflateMenu(R.menu.main_menu);
                }
                break;
        }

//        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loginFirst(View view){
        fAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
