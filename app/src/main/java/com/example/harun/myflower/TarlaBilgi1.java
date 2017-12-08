package com.example.harun.myflower;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

import java.util.HashMap;


/**
 * Created by Harun on 08-Dec-17.
 */

public class TarlaBilgi1 extends AppCompatActivity {

ImageView gun1Resim, gun2Resim,gun3Resim;
TextView gun1HavaDurumu,gun2HavaDurumu,gun3HavaDurumu;
TextView gun1,gun2,gun3;
TextView gun1Yagis,gun2Yagis,gun3Yagis;
TextView tarlaAdi,tarlaBuyukluk,tarlaUrun,tarlaUrunCesid,tarlaEkimTarih,tarlaHasatTarih,tarlaSulama,tarlaToprak,tarlaVerim;
MapView tarlaYer;
String tarlaKoordinat;
int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarla_bilgi_1);


        //hava durumu resimleri
        gun1Resim=(ImageView) findViewById(R.id.gun1Resim);
        gun2Resim=(ImageView) findViewById(R.id.gun2Resim);
        gun3Resim=(ImageView) findViewById(R.id.gun3Resim);

        //hava durumları
        gun1HavaDurumu=(TextView) findViewById(R.id.gun1HavaDurumu);
        gun2HavaDurumu=(TextView) findViewById(R.id.gun2HavaDurumu);
        gun3HavaDurumu=(TextView) findViewById(R.id.gun3HavaDurumu);

        //günlerin isimleri
        gun1=(TextView) findViewById(R.id.gun1);
        gun2=(TextView) findViewById(R.id.gun2);
        gun3=(TextView) findViewById(R.id.gun3);



        //günlerin yağış durumları

        gun1Yagis=(TextView) findViewById(R.id.gun1Yagis);
        gun2Yagis=(TextView) findViewById(R.id.gun2Yagis);
        gun3Yagis=(TextView) findViewById(R.id.gun3Yagis);

        //genel tarla bilgileri
        tarlaAdi=(TextView) findViewById(R.id.tarlaAdi);
        tarlaUrun=(TextView) findViewById(R.id.tarlaUrun);
        tarlaBuyukluk=(TextView) findViewById(R.id.tarlaBuyukluk);
        tarlaUrunCesid=(TextView) findViewById(R.id.tarlaUrunCesidi);
        tarlaEkimTarih=(TextView) findViewById(R.id.tarlaEkimTarih);
        tarlaHasatTarih=(TextView) findViewById(R.id.tarlaHasatTarih);
        tarlaSulama=(TextView) findViewById(R.id.tarlaSulama);
        tarlaToprak=(TextView) findViewById(R.id.tarlaToprak);
        tarlaVerim=(TextView) findViewById(R.id.tarlaVerim);


        //veritabanı işlemleri

        Intent intent=getIntent();
        //id=intent.getIntExtra("id",0);

        id=2;
        Database db=new Database(getApplicationContext());

        HashMap<String, String> map =db.idTarlaVeri(id);

        tarlaAdi.setText(map.get("TARLA_ADI"));

        tarlaUrun.setText(map.get("TARLA_URUN"));
        tarlaUrunCesid.setText(map.get("TARLA_URUN_CESID"));
        tarlaToprak.setText(map.get("TARLA_TOPRAK"));
        tarlaSulama.setText(map.get("TARLA_SULAMA"));
        tarlaHasatTarih.setText(map.get("HASAT_TARIH"));
        tarlaEkimTarih.setText(map.get("EKIM_TARIH"));
        tarlaKoordinat=map.get("TARLA_YER");
        tarlaVerim.setText(map.get("TARLA_VERIM").toString());
        tarlaBuyukluk.setText(map.get("TARLA_BUYUKLUK").toString());






    }
}
