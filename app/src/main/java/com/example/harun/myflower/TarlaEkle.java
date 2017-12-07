package com.example.harun.myflower;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class TarlaEkle extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LocationSource.OnLocationChangedListener {
    EditText tarla_name, tarla_buyuklugu, verim;
    Spinner mahsul, urun, toprak_tipi, sulama_tipi;
    Button map, hasat_tarih, ekim_tarih, sensor,tarlaEkle;
    GoogleMap googleMap;
    MapView mMapView;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationManager locationManager;
    double lat;
    double lng;
    LatLng latLng2;
    Button iptal, yesBtn, noBtn;
    MapFragment f;

    //database'e göndereceğim değişikenler
    String tarlaAdi,tarlaUrun,tarlaUrunCesid,tarlaToprak,tarlaSulama,tarlaYer,tarlaHasatTarih,tarlaEkimTarih;
    Integer tarlaBuyukluk, tarlaVerim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarla_ekle2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        //  toolbar.setLogo();
        toolbar.setTitle("Tarla Ekle");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        tarla_name = (EditText) findViewById(R.id.tarla_name);
        tarla_buyuklugu = (EditText) findViewById(R.id.tarla_buyuk);
        verim = (EditText) findViewById(R.id.verim);
        mahsul = (Spinner) findViewById(R.id.mahsul_spinner);
        urun = (Spinner) findViewById(R.id.urun_spinner);
        toprak_tipi = (Spinner) findViewById(R.id.toprak_tipi);
        sulama_tipi = (Spinner) findViewById(R.id.sulama_tipi);
        map = (Button) findViewById(R.id.map_ekle);
        hasat_tarih = (Button) findViewById(R.id.hasat_tarih);
        ekim_tarih = (Button) findViewById(R.id.ekim_tarih);
        sensor = (Button) findViewById(R.id.sensor);
        tarlaEkle=(Button) findViewById(R.id.tarlaEkle);
        tarlaEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((tarla_name.getText()==null) || (tarla_buyuklugu.getText()==null)  ||(verim.getText() ==null)||(tarlaUrun==null) || (tarlaUrunCesid==null)
                        ||(tarlaSulama==null)||(tarlaToprak==null)||(tarlaYer==null) ||(tarlaEkimTarih==null) ||(tarlaHasatTarih==null))
                {
                    Toast.makeText(getApplicationContext(),"Tüm alanları doldurunuz!",Toast.LENGTH_LONG).show();

                }
                else{

                    Database db=new Database(getApplicationContext());
                    tarlaAdi=tarla_name.getText().toString();
                    tarlaBuyukluk=Integer.parseInt(tarla_buyuklugu.getText().toString());
                    tarlaVerim=Integer.parseInt(verim.getText().toString());
                    db.tarlaEkle(tarlaAdi,tarlaBuyukluk,tarlaVerim,tarlaUrun,tarlaUrunCesid,tarlaToprak,tarlaSulama,tarlaYer,tarlaHasatTarih,tarlaEkimTarih);



                }
            }
        });


        ///*******SPİNNER İŞLEMLERİ*****///////

        final List<String> mahsulListe = new ArrayList<String>();
        mahsulListe.add("Mahsül Seçimi");
        mahsulListe.add("elma");
        mahsulListe.add("armut");
        mahsulListe.add("zeytin");


        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mahsulListe);
        mahsul.setAdapter(dataAdapter);
        mahsul.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tarlaUrun=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ///**************************************//////


        final List<String> urunListe = new ArrayList<String>();
        urunListe.add("Ürün Çeşidi");
        urunListe.add("Arbosona");
        urunListe.add("Çeşit1");
        urunListe.add("Çeşit2");

        final ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, urunListe);
        urun.setAdapter(dataAdapter2);
        urun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tarlaUrunCesid=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        final List<String> sulamaListe = new ArrayList<String>();
        sulamaListe.add("Sulama Tipi");
        sulamaListe.add("Tip1");
        sulamaListe.add("Tip2");
        sulamaListe.add("Tip3");

        final ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sulamaListe);
        sulama_tipi.setAdapter(dataAdapter3);
        sulama_tipi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tarlaSulama=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        final List<String> toprakListe = new ArrayList<String>();
        toprakListe.add("Toprak Tipi");
        toprakListe.add("Tip1");
        toprakListe.add("Tip2");
        toprakListe.add("Tip3");

        final ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, toprakListe);
        toprak_tipi.setAdapter(dataAdapter4);
        toprak_tipi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tarlaToprak=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

                f = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView);

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

                        latLng2=latLng;
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(dialog2.getContext(),android.R.style.Theme_DeviceDefault_Dialog_Alert);
                        LayoutInflater inflater = context.getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.alert_dialog, null);
                        dialogBuilder.setView(dialogView);
                        final AlertDialog alert_dialog = dialogBuilder.create();
                        alert_dialog.show();
                        yesBtn = (Button) dialogView.findViewById(R.id.btn_yes);
                        noBtn = (Button) dialogView.findViewById(R.id.btn_no);
                        yesBtn.setOnClickListener(new View.OnClickListener() {


                            @Override
                            public void onClick(View view) {

                                googleMap.addMarker(new MarkerOptions().position(latLng).title("Seçtiğiniz Yer").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                                Toast.makeText(getApplicationContext(),
                                        "KOORDİANATLAR ::" + latLng, Toast.LENGTH_LONG).show();
                                alert_dialog.dismiss();

                            }
                        });
                        noBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alert_dialog.dismiss();
                            }
                        });

                    }
                });

            }
            ///HARİTA TAMAM BUTONU
            iptal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog2.dismiss();
                    map.setText(latLng2.toString());
                    tarlaYer=latLng2.toString();

                    getFragmentManager().beginTransaction().remove(f).commit();

                }
            });





                /* googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom   // animasyon ile yakınlaşma
                        (new LatLng(lat, lng), 15f));*/
              /*  googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .title("Marker")
                        .draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker)));
*/

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

   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.setMyLocationEnabled(true);
        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }*/

   /* private void addMarker(){
        if(null != googleMap){
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(0, 0))
                    .title("Marker")
                    .draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
            );
        }
    }*/

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

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

       /* MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("aaaa");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));*/
    }

}
