package com.example.harun.myflower;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnasayfaActivity extends AppCompatActivity {


    TextView username;

  Button tarla,fiyat,urun,haber,ayarlar;


    SharedPreferences preferences;//preferences referan
    SharedPreferences.Editor editor;
private DrawerLayout mDrawer;
private ActionBarDrawerToggle toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);
        tarla=(Button) findViewById(R.id.tarla);
        fiyat=(Button) findViewById(R.id.fiyat);
        urun=(Button) findViewById(R.id.urunAra);
        haber=(Button) findViewById(R.id.haber);
        ayarlar=(Button) findViewById(R.id.ayarlar);


        mDrawer=(DrawerLayout)findViewById(R.id.drawer);
        toolbar=new ActionBarDrawerToggle(this,mDrawer,R.string.open,R.string.kapa);
        mDrawer.addDrawerListener(toolbar);
        toolbar.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        username=(TextView)navigationView.getHeaderView(0).findViewById(R.id.username);

        Bundle extra=getIntent().getExtras();
        final String username2=extra.getString("username");
        username.setText(username2);

        /*ImageView iv = (ImageView)navigationView.findViewById(R.id.nav_image);
        iv.setColorFilter(Color.argb(150, 155, 155, 155),   PorterDuff.Mode.SRC_ATOP);
*/

      /*  preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit(); //
        String mail = preferences.getString("email", "");
        username.setText(mail);*/

      haber.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(AnasayfaActivity.this, Haberler.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              startActivity(intent);
          }
      });

      tarla.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(AnasayfaActivity.this, Tarla.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              intent.putExtra("username",username2);
              startActivity(intent);
          }
      });

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    if(item.getItemId()==R.id.haber){
                        Intent intent = new Intent(AnasayfaActivity.this, Haberler.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    if(item.getItemId()==R.id.Anasayfa){
                        Intent intent = new Intent(AnasayfaActivity.this, AnasayfaActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("username",username2);

                        startActivity(intent);
                    }
                    if(item.getItemId()==R.id.add_tarla) {
                        Intent intent = new Intent(AnasayfaActivity.this, TarlaEkle.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("username",username2);

                        startActivity(intent);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                    }
                    if(item.getItemId()==R.id.tarla) {
                        Intent intent = new Intent(AnasayfaActivity.this, Tarla.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("username",username2);

                        startActivity(intent);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                    }

                    return false;
                }
            });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toolbar.onOptionsItemSelected(item)){


            return true;
        }

        return super.onOptionsItemSelected(item);

    }







}
