package com.example.harun.myflower;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Harun on 08-Dec-17.
 */

public class TarlaBilgi1 extends AppCompatActivity {


    ImageView gun1Resim, gun2Resim, gun3Resim;
    TextView gun1HavaDurumu, gun2HavaDurumu, gun3HavaDurumu;
    TextView gun1, gun2, gun3;
    TextView gun1Yagis, gun2Yagis, gun3Yagis;
    TextView tarlaAdi, tarlaBuyukluk, tarlaUrun, tarlaUrunCesid, tarlaEkimTarih, tarlaHasatTarih, tarlaSulama, tarlaToprak, tarlaVerim;
    MapView tarlaYer;
    String tarlaKoordinat;
    int id;
    Button sensorSayfa;
    public JSONObject jsonObject;
    private OkHttpClient client = new OkHttpClient();
    private MapView mView;
    String JSON_URL;
    String iconkodu;
    int sicaklik;
    double ruzgarhizi;
    int nemdegeri;
    String name, havanasil2, aciklama, ulke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarla_bilgi_1);


        //veritabanı işlemleri
        Bundle extra = getIntent().getExtras();
        String a = extra.getString("iddeger");
        int id = Integer.parseInt(a);
        Database db = new Database(getApplicationContext());
        HashMap<String, String> map = db.idTarlaVeri(id);

        double lat = Double.parseDouble(map.get("TARLA_LATITUDE"));
        double lon = Double.parseDouble(map.get("TARLA_LONGITUDE"));

        JSON_URL = "http://openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=b6907d289e10d714a6e88b30761fae22&lang=TR&units=metric";
        new DownloadJSON().execute();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        mView = (MapView) findViewById(R.id.mapView);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mapView, new MapFragment(Double.parseDouble(map.get("TARLA_LATITUDE")), Double.parseDouble(map.get("TARLA_LONGITUDE"))));
        /**** HACI BURADA GÖNDERDİĞİM LAT LONG KISMINDAN GÖNDERİCEGİMİZ DEĞERLER MAP KISMINDA GÖRÜNCECEK *///
        fragmentTransaction.commit();


        //JSON_URL = "api.openweathermap.org/data/2.5/weather?lat={"+lat+"}&lon={"+lon+"}";

        sensorSayfa = (Button) findViewById(R.id.sensorler);
        sensorSayfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TarlaBilgi1.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        //hava durumu resimleri
        gun1Resim = (ImageView) findViewById(R.id.gun1Resim);
        gun2Resim = (ImageView) findViewById(R.id.gun2Resim);
        gun3Resim = (ImageView) findViewById(R.id.gun3Resim);

        //hava durumları
        gun1HavaDurumu = (TextView) findViewById(R.id.gun1HavaDurumu);
        gun2HavaDurumu = (TextView) findViewById(R.id.gun2HavaDurumu);
        gun3HavaDurumu = (TextView) findViewById(R.id.gun3HavaDurumu);

        //günlerin isimleri
        gun1 = (TextView) findViewById(R.id.gun1);
        gun2 = (TextView) findViewById(R.id.gun2);
        gun3 = (TextView) findViewById(R.id.gun3);


        //günlerin yağış durumları

        gun1Yagis = (TextView) findViewById(R.id.gun1Yagis);
        gun2Yagis = (TextView) findViewById(R.id.gun2Yagis);
        gun3Yagis = (TextView) findViewById(R.id.gun3Yagis);

        //genel tarla bilgileri
        tarlaAdi = (TextView) findViewById(R.id.tarlaAdi);
        tarlaUrun = (TextView) findViewById(R.id.tarlaUrun);
        tarlaBuyukluk = (TextView) findViewById(R.id.tarlaBuyukluk);
        tarlaUrunCesid = (TextView) findViewById(R.id.tarlaUrunCesidi);
        tarlaEkimTarih = (TextView) findViewById(R.id.tarlaEkimTarih);
        tarlaHasatTarih = (TextView) findViewById(R.id.tarlaHasatTarih);
        tarlaSulama = (TextView) findViewById(R.id.tarlaSulama);
        tarlaToprak = (TextView) findViewById(R.id.tarlaToprak);
        tarlaVerim = (TextView) findViewById(R.id.tarlaVerim);


        tarlaAdi.setText(map.get("TARLA_ADI"));

        tarlaUrun.setText(map.get("TARLA_URUN"));
        tarlaUrunCesid.setText(map.get("TARLA_URUN_CESID"));
        tarlaToprak.setText(map.get("TARLA_TOPRAK"));
        tarlaSulama.setText(map.get("TARLA_SULAMA"));
        tarlaHasatTarih.setText(map.get("TARLA_HASAT_TARIH"));
        tarlaEkimTarih.setText(map.get("TARLA_EKIM_TARIH"));
        tarlaKoordinat = map.get("TARLA_YER");
        tarlaVerim.setText(map.get("TARLA_VERIM").toString());
        tarlaBuyukluk.setText(map.get("TARLA_BUYUKLUK").toString());


        getSupportActionBar().setTitle(map.get("TARLA_ADI") + " Detaylar");


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /////HAVA DURUMU İÇİN GEREKLİ YAZILAN GEREKLİ METODLAR


    private class DownloadJSON extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            // İNDİRME İŞİNİ BURADA YAPIYORUM
            //JSONObject jsonObject=null;

            String jsonData;
            try {
                jsonData = run(JSON_URL);
                Log.i("JSON -->", jsonData);

                try {
                    if (jsonData != null) {

                        jsonObject = new JSONObject(jsonData);

                        name = jsonObject.getString("name");
                        //System.out.println("Hava Durumu  ------->>>>" + name); ///BURASI BÖLGE İSMİ

                        JSONArray listArray = jsonObject.getJSONArray("weather");
                        //weather listesini aldım

                        JSONObject firstobje = listArray.getJSONObject(0);
                        // weather listesinin

                        havanasil2 = firstobje.getString("main");

                        aciklama = firstobje.getString("description");

                        iconkodu = firstobje.getString("icon");


                        JSONObject main = jsonObject.getJSONObject("main");
                        //  JSONObject main2 = jsonObject.getJSONObject("main");
                        sicaklik = main.getInt("temp");
                        // int basınc = main.getInt("pressure");
                        nemdegeri = main.getInt("humidity");


                        JSONObject ulke2 = jsonObject.getJSONObject("sys");
                        ulke = ulke2.getString("country");

                        JSONObject ruzgar = jsonObject.getJSONObject("wind");

                        ruzgarhizi = ruzgar.getDouble("speed");

                        /// System.out.println("BURASI BULUTLUMU YAĞMURLUMU  ------->>>>" + havanasil2); //BURASI HAVA DURUMU
                        // System.out.println("Aciklama ------->>>>" + aciklama); //BURASI HAVA DURUMU
                        // System.out.println("İKONKODU ------->>>>" + iconkodu); //BURASI HAVA DURUMU
                        // System.out.println("HAVA DEĞERLERİ   ------->>>>" + sicaklik); /////// SICAKLIK
                        //  System.out.println("HAVA DEĞERLERİ   ------->>>>" + basınc); /////BASINC DEĞERİ
                        //  System.out.println("HAVA DEĞERLERİ   ------->>>>" + nem); ///// NEM
                   /*
                    Gson gson = new Gson();
                    List<modelHava> havaList = new ArrayList<modelHava>();
                    havaList = Arrays.asList(gson.fromJson(jsonData,modelHava[].class));*/


                        //  Log.e("VERİLER", jsonObject.toString());
                    }
                } catch (JSONException e) {
                    e.getMessage();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            // JSON VERİYİ NULL DEĞİLSE JAVA NESNELERİNE DÖNÜŞTÜR


            return null;
        }

        private String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        @Override
        protected void onPostExecute(String result) {

            geticon(iconkodu);

            /**** ICONUDA INTERNETTEN CEKİYOR */
           /* String url = "http://openweathermap.org/img/w/" + iconkodu + ".png";
            Picasso.with(getApplicationContext()).load(url).fit().into(gun1Resim);
            Picasso.with(getApplicationContext()).load(url).fit().into(gun2Resim);
            Picasso.with(getApplicationContext()).load(url).fit().into(gun3Resim);*/

            /*** APININ LANG KISMINI TR YAPAMADIGIM ICIN TR YE CEVIRIYORUM :DDD*/

            switch (aciklama) {
                case "clear":
                    gun3Yagis.setText("Hava Açık");
                    break;
                case "clear sky":
                    gun3Yagis.setText("Temiz Gökyüzü");
                    break;
                case "few clouds":
                    gun3Yagis.setText("Az Bulutlu");
                    break;
                case "scattered clouds":
                    gun3Yagis.setText("Parçalı Bulutlu");
                    break;
                case "broken clouds":
                    gun3Yagis.setText("yer yer açık bulutlu");
                    break;
                case "shower rain":
                    gun3Yagis.setText("Sağnak Yağışlı");
                    break;
                case "thunderstorm":
                    gun3Yagis.setText("gök gürültülü fırtına");
                    break;
                case "snow":
                    gun3Yagis.setText("Kar Yağışlı");
                    break;
                case "mist":
                    gun3Yagis.setText("Sisli");
                    break;
                case "overcast clouds":
                    gun3Yagis.setText("Hava Kapalı/Çok bulutlu");
                    break;
                default:
                    gun3Yagis.setText("Bilgi Yok");
            }

            gun1.setText(sicaklik + " C");
            gun2.setText("" + nemdegeri + " %");
            gun3.setText("" + ruzgarhizi + " km/s");
            gun1Yagis.setText(ulke);
            // gun2Yagis.setText(havanasil2);
            gun2Yagis.setText(name);

        }

    }

    public void geticon(String iconkodu) {

        switch (iconkodu) {
            case "01d":
                Picasso.with(getApplicationContext()).load(R.drawable.a).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.a).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.a).fit().into(gun1Resim);
                break;
            case "01n":
                Picasso.with(getApplicationContext()).load(R.drawable.an).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.an).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.an).fit().into(gun1Resim);
                break;
            case "02d":
                Picasso.with(getApplicationContext()).load(R.drawable.b).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.b).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.b).fit().into(gun1Resim);
                break;
            case "02n":
                Picasso.with(getApplicationContext()).load(R.drawable.bn).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.bn).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.bn).fit().into(gun1Resim);
                break;
            case "03d":
                Picasso.with(getApplicationContext()).load(R.drawable.c).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.c).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.c).fit().into(gun1Resim);
                break;
            case "03n":
                Picasso.with(getApplicationContext()).load(R.drawable.cn).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.cn).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.cn).fit().into(gun1Resim);
                break;
            case "04d":
                Picasso.with(getApplicationContext()).load(R.drawable.c).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.c).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.c).fit().into(gun1Resim);
                break;
            case "04n":
                Picasso.with(getApplicationContext()).load(R.drawable.dn).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.dn).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.dn).fit().into(gun1Resim);
                break;
            case "09d":
                Picasso.with(getApplicationContext()).load(R.drawable.e).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.e).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.e).fit().into(gun1Resim);
                break;
            case "09n":
                Picasso.with(getApplicationContext()).load(R.drawable.en).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.en).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.en).fit().into(gun1Resim);
                break;
            case "10n":
                Picasso.with(getApplicationContext()).load(R.drawable.f).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.f).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.f).fit().into(gun1Resim);
                break;
            case "10d":
                Picasso.with(getApplicationContext()).load(R.drawable.fn).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.fn).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.fn).fit().into(gun1Resim);
                break;
            case "11d":
                Picasso.with(getApplicationContext()).load(R.drawable.g).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.g).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.g).fit().into(gun1Resim);
                break;
            case "11n":
                Picasso.with(getApplicationContext()).load(R.drawable.gn).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.gn).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.gn).fit().into(gun1Resim);
                break;
            case "13d":
                Picasso.with(getApplicationContext()).load(R.drawable.h).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.h).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.h).fit().into(gun1Resim);
                break;
            case "13n":
                Picasso.with(getApplicationContext()).load(R.drawable.hn).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.hn).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.hn).fit().into(gun1Resim);
                break;
            case "50d":
                Picasso.with(getApplicationContext()).load(R.drawable.r).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.r).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.r).fit().into(gun1Resim);
                break;
            case "50n":
                Picasso.with(getApplicationContext()).load(R.drawable.rn).fit().into(gun3Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.rn).fit().into(gun2Resim);
                Picasso.with(getApplicationContext()).load(R.drawable.rn).fit().into(gun1Resim);
                break;
            default:   Picasso.with(getApplicationContext()).load(R.drawable.default2).fit().into(gun3Resim);
              Picasso.with(getApplicationContext()).load(R.drawable.default2).fit().into(gun2Resim);
              Picasso.with(getApplicationContext()).load(R.drawable.default2).fit().into(gun1Resim);
        }


    }

}
