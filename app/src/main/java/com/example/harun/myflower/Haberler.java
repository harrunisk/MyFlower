package com.example.harun.myflower;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.harun.myflower.adapters.PostAdapter;
import com.example.harun.myflower.adapters.PullAndParseXML;
import com.example.harun.myflower.adapters.post_model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Haberler extends AppCompatActivity {
    TextView username;
    private String TAG = getClass().getSimpleName();

    //private String RSS_URL = "http://webrazzi.com/feed/";
   // private String RSS_URL ="https://etarim.net/feed";
   // private String RSS_URL ="http://gidatarim.com/feed"
   // private String RSS_URL ="http://www.gthbhaber.com/feed/";
    //private String RSS_URL ="http://www.haberturk.com/rss";
    //private String RSS_URL ="https://www.canlihaber.com/rss/";
  //  private String RSS_URL ="http://www.dipnot.tv/feed/";
   // private String RSS_URL ="http://www.kampushaber.com/rss.xml";
    private String RSS_URL ="http://www.turkiyehaberajansi.com/rss.xml";




    // XML İ / RSS İ ÇEKTİRİP AYRIŞTIRACAĞIMIZ SINIFIMIZ
    private PullAndParseXML pullAndParseXML;

    // YAZILARA/POSTLARA AİT DATALARI NESNELER HALİNDE TUTACAĞIMIZ LİSTEMİZ
    private List<post_model> posts;

    // LISTEMİZ, RECYCLERVIEW
    private RecyclerView recyclerView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    // LISTE VE VERİ ARASINDAKİ BAĞLANTI - ADAPTER
    private PostAdapter adapter;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_haberler2);
        mDrawer=(DrawerLayout)findViewById(R.id.drawer);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit(); //




        toolbar=new ActionBarDrawerToggle(this,mDrawer,R.string.open,R.string.kapa);
        mDrawer.addDrawerListener(toolbar);
        toolbar.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Haberler");

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        username=(TextView)navigationView.getHeaderView(0).findViewById(R.id.username);

        final String mail = preferences.getString("email", "");
        username.setText(mail);

       /* Bundle extra=getIntent().getExtras();
        String username2=extra.getString("username");
        username.setText(username2);*/


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.haber){
                    Intent intent = new Intent(Haberler.this, Haberler.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
                if(item.getItemId()==R.id.Anasayfa){
                    Intent intent = new Intent(Haberler.this, AnasayfaActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("username",mail);

                    startActivity(intent);
                }

                if(item.getItemId()==R.id.add_tarla) {
                Intent intent = new Intent(Haberler.this, Tarla.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }

                return false;
            }
        });




        pullAndParseXML = new PullAndParseXML(RSS_URL);
        pullAndParseXML.downloadXML();

        while (pullAndParseXML.parsingComplete) ;
        posts = pullAndParseXML.getPostList().subList(1, pullAndParseXML.getPostList().size());

            for (int i = 0; i < posts.size(); i++) {
              Log.i(TAG, "title #" + (i + 1) + " > : " + posts.get(i).getTitle());
             Log.i(TAG, "image #" + (i + 1) + " > : " + posts.get(i).getImageUrl());
             }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_haberler);
        adapter=new PostAdapter(this,posts);

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toolbar.onOptionsItemSelected(item)){


            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    /**
     * GET IMAGE URL
     *
     * @param text
     * @return
     */
    private ArrayList getImageUrls(String text) {

        ArrayList links = new ArrayList();
        String patternString = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String urlStr = matcher.group();
            if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }
            if (urlStr.endsWith(".jpg") || urlStr.endsWith(".png") || urlStr.endsWith(".gif"))
                links.add(urlStr);
        }
        return links;
    }


}
