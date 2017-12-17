package com.example.harun.myflower;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harun.myflower.Tarlalarım.listAdapter_tarla;
import com.example.harun.myflower.Tarlalarım.listModel_tarla;

import java.util.ArrayList;

public class Tarla extends AppCompatActivity {
    TextView username;
    //CoordinatorLayout rootLayout;
    SharedPreferences preferences;//preferences referansı
    SharedPreferences.Editor editor;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle toolbar;
    FloatingActionButton fab;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    private boolean FAB_Status = false;
    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Database database;
    ArrayList<listModel_tarla> tarlaArrayList;
    listAdapter_tarla listAdapterTarla;
    listModel_tarla listModelTarla;
    public ListView mlistview;
    String username2;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarla);

        database = new Database(this);
        mlistview = (ListView) findViewById(R.id.listViewTarla); /// LAYOUT LİSTESİNİ TANIMLADIM

        /*Bundle extra=getIntent().getExtras();
       =extra.getString("username");

        /**ŞİMDİ VERİTABANINDAN VERİLERİ ÇEKİP tarlaArrayLİSTESİne eklemem lazım */

        tarlaArrayList = database.tarlaListele();

        listAdapterTarla = new listAdapter_tarla(tarlaArrayList, this); // ADAPTERE LİSTEMİ GÖNDERİYORUM
        mlistview.setAdapter(listAdapterTarla); //ADAPTERİDE ONAYLADIM
        listAdapterTarla.notifyDataSetChanged();

        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                String tarlaadi = tarlaArrayList.get(i).getTarla_adi();
                String tarlaurun = tarlaArrayList.get(i).getUrun();
                String cesit = tarlaArrayList.get(i).getUrunCesid();
                String hasat = tarlaArrayList.get(i).getTvHasat();
                String ekim = tarlaArrayList.get(i).getTvEkim();
                String id = tarlaArrayList.get(i).getId();



                Intent intent = new Intent(Tarla.this, TarlaBilgi1.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("iddeger", id);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                // finish();


            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab_1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab_2);

        show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);


        // rootLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = new ActionBarDrawerToggle(this, mDrawer, R.string.open, R.string.kapa);
        mDrawer.addDrawerListener(toolbar);
        toolbar.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);

        getSupportActionBar().setTitle("Tarlalarım");

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit(); //
        String mail = preferences.getString("email", "");
        username.setText(mail);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FAB_Status == false) {
                    //Display FAB menu
                    expandFAB();
                    FAB_Status = true;
                } else {
                    //Close FAB menu
                    hideFAB();
                    FAB_Status = false;
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "TARLA EKLE ", Toast.LENGTH_SHORT).show();


                Intent i = new Intent(Tarla.this, TarlaEkle.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "HASAT GEÇMİŞİ", Toast.LENGTH_SHORT).show();
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                mDrawer.closeDrawers();

                if (item.getItemId() == R.id.haber) {
                    Intent intent = new Intent(Tarla.this, Haberler.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //intent.putExtra("username",username2);

                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                }
                if (item.getItemId() == R.id.Anasayfa) {
                    Intent intent = new Intent(Tarla.this, AnasayfaActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // intent.putExtra("username",username2);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.add_tarla) {
                    Intent intent = new Intent(Tarla.this, TarlaEkle.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // intent.putExtra("username",username2);

                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                }

                if (item.getItemId() == R.id.tarla) {
                    Intent intent = new Intent(Tarla.this, Tarla.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // intent.putExtra("username",username2);

                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                }
                if (item.getItemId() == R.id.mahsül) {
                    Intent intent = new Intent(Tarla.this, UrunArama.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // intent.putExtra("username", username2);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                }
                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toolbar.onOptionsItemSelected(item)) {


            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void expandFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);


        //Floating Action Button 2
      /*  FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);*/

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams3.rightMargin += (int) (fab2.getWidth() * 0.25);
        layoutParams3.bottomMargin += (int) (fab2.getHeight() * 1.7);
        fab2.setLayoutParams(layoutParams3);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);
    }


    private void hideFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);

        //Floating Action Button 2
       /* FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);*/

        //Floating Action Button 3

        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams3.rightMargin -= (int) (fab2.getWidth() * 0.25);
        layoutParams3.bottomMargin -= (int) (fab2.getHeight() * 1.7);
        fab2.setLayoutParams(layoutParams3);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);
    }

}

