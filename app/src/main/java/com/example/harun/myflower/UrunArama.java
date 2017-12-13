package com.example.harun.myflower;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UrunArama extends AppCompatActivity {
    EditText mEdit, arama;
    TextView username;
    String query;
    String JSON_URL;
    String text,resim_url;
    WebView webview;
    public JSONObject jsonObject;
    private OkHttpClient client2 = new OkHttpClient();
    DrawerLayout mDrawer;
    Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ImageView image;
    /*ORJİNAL RESİMLİ*/
    //"http://tr.wiki.admicos.cf/w/api.php?action=query&prop=extracts&format=json&text=&titles=" + query
  //  http://tr.wiki.admicos.cf/w/api.php?action=query&prop=extracts|pageimages|pageterm‌​s&piprop=original&format=json&text=&titles=+ query
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.urun_arama2);


        arama = (EditText) findViewById(R.id.arama);
        image=(ImageView) findViewById(R.id.arama_resim);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        webview = (WebView) this.findViewById(R.id.wV);
        webview.getSettings().setJavaScriptEnabled(true);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open, R.string.kapa);
        mDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_delete);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ürün Arama");


        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit(); //
        String mail = preferences.getString("email", "");
        username.setText(mail);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawer.closeDrawers();
                if (item.getItemId() == R.id.haber) {
                    Intent intent = new Intent(UrunArama.this, Haberler.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                }
                if (item.getItemId() == R.id.Anasayfa) {
                    Intent intent = new Intent(UrunArama.this, AnasayfaActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.add_tarla) {
                    Intent intent = new Intent(UrunArama.this, TarlaEkle.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                }

                if (item.getItemId() == R.id.tarla) {
                    Intent intent = new Intent(UrunArama.this, Tarla.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                }
                if (item.getItemId() == R.id.mahsül) {
                    Intent intent = new Intent(UrunArama.this, UrunArama.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // intent.putExtra("username", username2);
                    finish();
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                }


                return false;
            }
        });


        arama.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    if (motionEvent.getRawX() >= (arama.getRight() - arama.getCompoundDrawables()[DRAWABLE_RIGHT].
                            getBounds().width())) {
                        // your action here
                        query = arama.getText().toString();
                        JSON_URL = "http://tr.wiki.admicos.cf/w/api.php?action=query&prop=extracts|pageimages|pageterm‌​s&piprop=original&format=json&text=&titles="+query;

                       // if (!query.isEmpty()) {
                            // Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                            new Downloadjson().execute();
                       // }

                        return true;
                    }
                    if (motionEvent.getRawX() >= (arama.getLeft() - arama.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                        arama.setText("");
                        arama.setHint("Aramak istediniz Ürün");
                    }
                }

                return false;
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(Gravity.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //getMenuInflater().inflate(R.menu.search, menu);
       /* MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String aranan = q;
                searchView.clearFocus();
                JSON_URL = "http://tr.wiki.admicos.cf/w/api.php?action=query&prop=extracts&format=json&text=&titles=" + q;
                new Downloadjson().execute();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });*/

        return super.onCreateOptionsMenu(menu);
    }

    private class Downloadjson extends AsyncTask<String, Void, String> {

        final ProgressDialog progressDialog = new ProgressDialog(UrunArama.this,
                R.style.My_AppTheme_Dark_Dialog);
        String jsonData;
        @Override
        protected void onPreExecute() {

            if (!query.isEmpty()) {
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage(query + " Aranıyor");
                progressDialog.show();

            } else
                Toast.makeText(getApplicationContext(), "Ne Arıyorsun ??", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {


            try {
                jsonData = run(JSON_URL);
                Log.i("JSON -->", jsonData);

                try {

                    jsonObject = new JSONObject(jsonData);
                    JSONObject parse2 = jsonObject.getJSONObject("query");

                    JSONObject parse = parse2.getJSONObject("pages");
                    Iterator<String> keys = parse2.getJSONObject("pages").keys();
                    String str_Name = keys.next();
                    //System.out.println("BURASIKEYS " + str_Name);
                    JSONObject parse3 = parse.getJSONObject(str_Name);
                    text = parse3.getString("extract");

                    JSONObject resim=parse3.getJSONObject("original");
                    resim_url=resim.getString("source");


                } catch (JSONException e) {
                    e.getMessage();
                }


            } catch (IOException i) {
                i.printStackTrace();

            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();
            if(text==null){

                Toast.makeText(getApplicationContext(), "Bulunamadı", Toast.LENGTH_SHORT).show();

            }
            else {
                Picasso.with(getApplicationContext()).load(resim_url).fit().into(image);
                webview.setWebViewClient(new WebViewClient());
                webview.loadData(text, "text/html", "UTF-8");
                // webview.loadUrl("http://tr.m.wiki.admicos.cf/wiki/Elma");

            }
        }

        private String run(String url) throws IOException {
            try {
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client2.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {

                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                return e.getMessage();
            }

        }


    }
}
