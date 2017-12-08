package com.example.harun.myflower;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.harun.myflower.CardFragment.CardAdapter;
import com.example.harun.myflower.CardFragment.CardItem;
import com.example.harun.myflower.CardFragment.CardPagerAdapter;
import com.example.harun.myflower.CardFragment.ShadowTransformer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TarlaEkle extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, LocationSource.OnLocationChangedListener {
    EditText tarla_name, tarla_buyuklugu, verim;
    Spinner mahsul, urun, toprak_tipi, sulama_tipi;
    Button map, hasat_tarih, ekim_tarih, sensor,tarlaEkle;
    GoogleMap googleMap;
    LatLng latLng2;
    Button iptal, yesBtn, noBtn;
    SupportMapFragment f;
    String ekimZamani, hasatZamani;
    private ViewPager mViewPager;
    CardItem card;
    public String secilen_item;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    //database'e göndereceğim değişikenler
    String tarlaAdi,tarlaUrun,tarlaUrunCesid,tarlaToprak,tarlaSulama,tarlaYer,tarlaHasatTarih,tarlaEkimTarih;
    Integer tarlaBuyukluk, tarlaVerim;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarla_ekle3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        //  toolbar.setLogo();
        toolbar.setTitle("Tarla Ekle");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayList<String> urunListe = new ArrayList<String>();

        urunListe.add("Ürün");
        urunListe.add("Arbosona");
        urunListe.add("Çeşit1");
        urunListe.add("Çeşit2");

        final ArrayList<String> mahsulListe = new ArrayList<String>();
        mahsulListe.add("Ürün Çeşidi");
        mahsulListe.add("elma");
        mahsulListe.add("armut");
        mahsulListe.add("zeytin");
        final ArrayList<String> toprakListe = new ArrayList<String>();
        toprakListe.add("Toprak Tipi");
        toprakListe.add("elma");
        toprakListe.add("armut");
        toprakListe.add("zeytin");
        final ArrayList<String> sulamaListe = new ArrayList<String>();
        sulamaListe.add("Sulama Tipi");
        sulamaListe.add("elma");
        sulamaListe.add("armut");
        sulamaListe.add("zeytin");

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mCardAdapter = new CardPagerAdapter(this);
        mCardAdapter.addCardItem(new CardItem(R.string.title_1, urunListe)); // HACI BAK LİSTEYİ BURDAN GÖNDERECEN VERİ TABANINDA CEK BURAYA KOY
        mCardAdapter.addCardItem(new CardItem(R.string.title_2, mahsulListe));
        mCardAdapter.addCardItem(new CardItem(R.string.title_3, toprakListe));
        mCardAdapter.addCardItem(new CardItem(R.string.title_4, sulamaListe));
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        mCardShadowTransformer.enableScaling(true);



        tarla_name = (EditText) findViewById(R.id.tarla_name);
        tarla_buyuklugu = (EditText) findViewById(R.id.tarla_buyuk);
        verim = (EditText) findViewById(R.id.verim);
        //mahsul = (Spinner) findViewById(R.id.mahsul_spinner);
        // urun = (Spinner) findViewById(R.id.urun_spinner);
        // toprak_tipi = (Spinner) findViewById(R.id.toprak_tipi);
        // sulama_tipi = (Spinner) findViewById(R.id.sulama_tipi);
        map = (Button) findViewById(R.id.map_ekle);
        hasat_tarih = (Button) findViewById(R.id.hasat_tarih);
        ekim_tarih = (Button) findViewById(R.id.ekim_tarih);
        sensor = (Button) findViewById(R.id.sensor);


            //burada düzeltmeler yapılacak alanlar boşsa hata veriyor kapatıyor falan
        tarlaEkle=(Button) findViewById(R.id.tarlaEkle);
        tarlaEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tarlaUrun = mCardAdapter.urun.toString();
                    tarlaUrunCesid = mCardAdapter.urunCesidi.toString();
                    tarlaSulama = mCardAdapter.sulamaTipi.toString();
                    tarlaToprak = mCardAdapter.sulamaTipi.toString();
                }
                catch(OutOfMemoryError e1){

                    Toast.makeText(getApplicationContext(), "Tüm alanları eksiksiz doldurun", Toast.LENGTH_LONG).show();

                }

                if ((tarla_name.getText()==null) || (tarla_buyuklugu.getText()==null)  ||(verim.getText() ==null)||(tarlaUrun==null) || (tarlaUrunCesid==null)
                        ||(tarlaSulama==null)||(tarlaToprak==null)||(tarlaYer==null) ||(tarlaEkimTarih==null) ||(tarlaHasatTarih==null))
                {

                    Toast.makeText(getApplicationContext(),"Tüm alanları eksiksiz doldurun",Toast.LENGTH_LONG).show();

                }
                else{

                    try {
                        Database db = new Database(getApplicationContext());
                        tarlaAdi = tarla_name.getText().toString();
                        tarlaBuyukluk = Integer.parseInt(tarla_buyuklugu.getText().toString());
                        tarlaVerim = Integer.parseInt(verim.getText().toString());

                        db.tarlaEkle(tarlaAdi, tarlaBuyukluk, tarlaVerim, tarlaUrun, tarlaUrunCesid, tarlaToprak, tarlaSulama, tarlaYer, tarlaHasatTarih, tarlaEkimTarih);

                        Toast.makeText(getApplicationContext(), "Veritabanına eklendi ama şu an görüntüleyemezsiniz.", Toast.LENGTH_LONG).show();
                    }
                    catch(OutOfMemoryError e1){

                        Toast.makeText(getApplicationContext(), "Tüm alanları eksiksiz doldurun", Toast.LENGTH_LONG).show();

                    }

                }
            }
        });
/*/*/
//
//        ///*******SPİNNER İŞLEMLERİ*****///////
//
//        mahsul.setOnItemSelectedListener(this);
//        final List<String> mahsulListe = new ArrayList<String>();
//        mahsulListe.add("Mahsül Seçimi");
//        mahsulListe.add("elma");
//        mahsulListe.add("armut");
//        mahsulListe.add("zeytin");
//
//
//        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mahsulListe);
//        mahsul.setAdapter(dataAdapter);
//        ///**************************************//////
//
//        urun.setOnItemSelectedListener(this);
//        final List<String> urunListe = new ArrayList<String>();
//        urunListe.add("Ürün Çeşidi");
//        urunListe.add("Arbosona");
//        urunListe.add("Çeşit1");
//        urunListe.add("Çeşit2");
//
//        final ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, urunListe);
//        urun.setAdapter(dataAdapter2);
//
//
//        sulama_tipi.setOnItemSelectedListener(this);
//        final List<String> sulamaListe = new ArrayList<String>();
//        sulamaListe.add("Sulama Tipi");
//        sulamaListe.add("Tip1");
//        sulamaListe.add("Tip2");
//        sulamaListe.add("Tip3");
//
//        final ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sulamaListe);
//        sulama_tipi.setAdapter(dataAdapter3);
//
//
//        toprak_tipi.setOnItemSelectedListener(this);
//        final List<String> toprakListe = new ArrayList<String>();
//        toprakListe.add("Toprak Tipi");
//        toprakListe.add("Tip1");
//        toprakListe.add("Tip2");
//        toprakListe.add("Tip3");
//
//        final ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, toprakListe);
//        toprak_tipi.setAdapter(dataAdapter4);
//
//
//
//


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkLocationPermission();

                }
                //Check if Google Play Services Available or not
                if (!CheckGooglePlayServices()) {

                    finish();
                } else

                    createMapView(TarlaEkle.this);


            }
        });

        ekim_tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(TarlaEkle.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                datePickerDialog.setMinDate(Calendar.getInstance());
                datePickerDialog.setAccentColor(Color.parseColor("#FF8C00"));
                datePickerDialog.setThemeDark(false); //set dark them for dialog?
                datePickerDialog.vibrate(true); //vibrate on choosing date?
                datePickerDialog.dismissOnPause(true); //dismiss dialog when onPause() called?
                datePickerDialog.showYearPickerFirst(false); //choose year first?
                //  datePickerDialog.setAccentColor(Color.parseColor("#9C27A0")); // custom accent color
                datePickerDialog.setTitle("Ekim Tarihi Seç"); //dialog title
                datePickerDialog.show(getFragmentManager(), "Ekim Tarihi "); //show dialog
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        ekimZamani = dayOfMonth + "/" + (++monthOfYear) + "/" + year;

                        ekim_tarih.setText("Ekim Tarihi: " + ekimZamani);
                        tarlaEkimTarih=ekimZamani;
                    }
                });

            }
        });
        hasat_tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(TarlaEkle.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                datePickerDialog.setMinDate(Calendar.getInstance());
                datePickerDialog.setAccentColor(Color.parseColor("#FF8C00"));
                datePickerDialog.setThemeDark(false); //set dark them for dialog?
                datePickerDialog.vibrate(true); //vibrate on choosing date?
                datePickerDialog.dismissOnPause(true); //dismiss dialog when onPause() called?
                datePickerDialog.showYearPickerFirst(false); //choose year first?
                //  datePickerDialog.setAccentColor(Color.parseColor("#9C27A0")); // custom accent color
                datePickerDialog.setTitle("Hasat Tarihi Seç"); //dialog title
                datePickerDialog.show(getFragmentManager(), "Hasat Tarihi "); //show dialog
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        hasatZamani = dayOfMonth + "/" + (++monthOfYear) + "/" + year;

                        hasat_tarih.setText("Hasat Tarihi:" + hasatZamani);
                        tarlaHasatTarih=hasatZamani;
                    }
                });


            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void createMapView(final Activity context) {

        RelativeLayout viewGroup2 = (RelativeLayout) context.findViewById(R.id.map2);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.mapview_dialog, viewGroup2);
        final Dialog dialog2 = new Dialog(context);
        dialog2.setContentView(layout);

        iptal = (Button) dialog2.findViewById(R.id.btn_cancel);
        dialog2.show();


        //addMarker();
        try {
            if (null == googleMap) {

                f = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);

                googleMap = f.getMap();
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.setMyLocationEnabled(true);
                googleMap.setTrafficEnabled(true);
                googleMap.setIndoorEnabled(true);
                googleMap.setBuildingsEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getCameraPosition();

               /* googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
                        googleMap.animateCamera(zoom);
                        return true;
                    }
                });*/

                googleMap.setIndoorEnabled(true);
                ////////HARİTA TIKLAMA İŞARETLEME
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(final LatLng latLng) {

                        latLng2 = latLng;
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(dialog2.getContext(), android.R.style.Theme_DeviceDefault_Dialog_Alert);
                        LayoutInflater inflater = context.getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.alert_dialog, null);
                        dialogBuilder.setView(dialogView);
                        final AlertDialog alert_dialog = dialogBuilder.create();
                        alert_dialog.show();
                        yesBtn = (Button) dialogView.findViewById(R.id.btn_yes);
                        noBtn = (Button) dialogView.findViewById(R.id.btn_no);


                        //***ALERT DİALOG YES NO ***///
                        yesBtn.setOnClickListener(new View.OnClickListener() {


                            @Override
                            public void onClick(View view) {

                                googleMap.addMarker(new MarkerOptions().position(latLng).title("Seçtiğiniz Yer").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                                Toast.makeText(getApplicationContext(),
                                        "KOORDİANATLAR ::" + latLng, Toast.LENGTH_LONG).show();
                                alert_dialog.dismiss();
                                map.setClickable(false);

                            }
                        });
                        noBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alert_dialog.dismiss();
                            }
                        });

                        //*****ALER DİALOG İSİ BİTTİ **////

                    }
                });

            }
            ///HARİTA TAMAM BUTONU
            iptal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!map.isClickable()) {
                        dialog2.dismiss();
                        map.setText(latLng2.toString());
                        tarlaYer=latLng2.toString();

                        //getFragmentManager().beginTransaction().remove(f).commit();
                        f.onStop();
                    } else
                        Toast.makeText(getApplicationContext(), "LÜTFEN BİR YER İŞARETLEYİNİZ", Toast.LENGTH_SHORT).show();


                }
            });

            if (null == googleMap) {
                Toast.makeText(getApplicationContext(),
                        "Harita oluşturma hatası", Toast.LENGTH_SHORT).show();
            }

                /*if (f != null) {
                    getFragmentManager().beginTransaction().remove(f).commit();
                }*/

        } catch (NullPointerException exception) {
            Log.e("mapApp", exception.toString());
        }

    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {

        //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }
}
