package com.example.harun.myflower;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class TarlaEkle extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
EditText tarla_name,tarla_buyuklugu,verim;
Spinner mahsul,urun,toprak_tipi,sulama_tipi;
Button map,hasat_tarih,ekim_tarih,sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarla_ekle2);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_actionbar);
        //  toolbar.setLogo();
        toolbar.setTitle("Tarla Ekle");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tarla_name=(EditText)findViewById(R.id.tarla_name);
        tarla_buyuklugu=(EditText)findViewById(R.id.tarla_buyuk);
        verim=(EditText)findViewById(R.id.verim);
        mahsul=(Spinner)findViewById(R.id.mahsul_spinner);
        urun=(Spinner)findViewById(R.id.urun_spinner);
        toprak_tipi=(Spinner)findViewById(R.id.toprak_tipi);
        sulama_tipi=(Spinner)findViewById(R.id.sulama_tipi);
        map=(Button)findViewById(R.id.map_ekle);
        hasat_tarih=(Button)findViewById(R.id.hasat_tarih);
        ekim_tarih=(Button)findViewById(R.id.ekim_tarih);
        sensor=(Button)findViewById(R.id.sensor);


        ///*******SPİNNER İŞLEMLERİ*****///////
        mahsul.setOnItemSelectedListener(this);
        final List<String> mahsulListe=new ArrayList<String>();
        mahsulListe.add("Mahsül Seçimi");
        mahsulListe.add("elma");
        mahsulListe.add("armut");
        mahsulListe.add("zeytin");


        final ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,mahsulListe);
        mahsul.setAdapter(dataAdapter);
        ///**************************************//////

        urun.setOnItemSelectedListener(this);
        final List<String> urunListe=new ArrayList<String>();
        urunListe.add("Ürün Çeşidi");
        urunListe.add("Arbosona");
        urunListe.add("Çeşit1");
        urunListe.add("Çeşit2");

        final ArrayAdapter<String> dataAdapter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,urunListe);
        urun.setAdapter(dataAdapter2);


        sulama_tipi.setOnItemSelectedListener(this);
        final List<String> sulamaListe=new ArrayList<String>();
        sulamaListe.add("Sulama Tipi");
        sulamaListe.add("Tip1");
        sulamaListe.add("Tip2");
        sulamaListe.add("Tip3");

        final ArrayAdapter<String> dataAdapter3=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,sulamaListe);
        sulama_tipi.setAdapter(dataAdapter3);


        toprak_tipi.setOnItemSelectedListener(this);
        final List<String> toprakListe=new ArrayList<String>();
        toprakListe.add("Toprak Tipi");
        toprakListe.add("Tip1");
        toprakListe.add("Tip2");
        toprakListe.add("Tip3");

        final ArrayAdapter<String> dataAdapter4=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,toprakListe);
        toprak_tipi.setAdapter(dataAdapter4);


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              /*  Dialog dialog = new Dialog(getApplicationContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.mapview_dialog);
                dialog.show();

                */



              /*  MapView mMapView = (MapView) dialog.findViewById(R.id.mapView);
                MapsInitializer.initialize(getActivity());

                mMapView = (MapView) dialog.findViewById(R.id.mapView);
                mMapView.onCreate(dialog.onSaveInstanceState());
                mMapView.onResume();// needed to get the map to display immediately
                googleMap = mMapView.getMap();*/
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
