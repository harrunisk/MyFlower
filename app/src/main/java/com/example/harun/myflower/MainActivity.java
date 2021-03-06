package com.example.harun.myflower;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import helpers.ChartHelper;
import helpers.MqttHelper;

public class MainActivity extends AppCompatActivity {

    MqttHelper mqttHelper;
    ChartHelper mChart,mChart2;
    LineChart chart,chart2;
    TextView username;

    ChartHelper mChart3,mChart4;
    LineChart chart3,chart4;

    ChartHelper mChart5,mChart6;
    LineChart chart5,chart6;

    ListView lv;
    ArrayAdapter adapter;
    ArrayList<HashMap<String,String>> sensorVeriListe;
    String sicaklik_Veri[];
    String sicaklik_Veri2[];
    String nem_Veri[];
    String isik_Veri[];
    String tum_Veri[];
    int id_Veri[];
    String tarih_Veri[];
    SharedPreferences preferences;//preferences referansı
    SharedPreferences.Editor editor;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle toolbar;
    static int temp=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        chart = (LineChart) findViewById(R.id.chart);
        chart2 = (LineChart) findViewById(R.id.chart);

        chart3=(LineChart) findViewById(R.id.chart2);
        chart4=(LineChart) findViewById(R.id.chart2);

        chart5=(LineChart) findViewById(R.id.chart3);
        chart6=(LineChart) findViewById(R.id.chart3);


        mChart = new ChartHelper(chart);
        mChart3 = new ChartHelper(chart3);
        mChart5 = new ChartHelper(chart5);



        //mDrawer=(DrawerLayout)findViewById(R.id.drawer);
        ///toolbar=new ActionBarDrawerToggle(this,mDrawer,R.string.open,R.string.kapa);
       // mDrawer.addDrawerListener(toolbar);
        //toolbar.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sensör detayları");
        func();
        if(temp==0)
        {startMqtt();
        temp++;}
        else if (temp!=0){

        }


       // final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
       // username=(TextView)navigationView.getHeaderView(0).findViewById(R.id.username);

       // Bundle extra=getIntent().getExtras();
        //final String username2=extra.getString("username");
        //username.setText(username2);

        /*ImageView iv = (ImageView)navigationView.findViewById(R.id.nav_image);
        iv.setColorFilter(Color.argb(150, 155, 155, 155),   PorterDuff.Mode.SRC_ATOP);
*/

      /*  preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit(); //
        String mail = preferences.getString("email", "");
        username.setText(mail);*/

          /*  navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    mDrawer.closeDrawers();
                 toolbar.onDrawerOpened(mDrawer);
                    if(item.getItemId()==R.id.haber){
                        Intent intent = new Intent(MainActivity.this, Haberler.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                        startActivity(intent);
                        finish();

                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                    }
                        if(item.getItemId()==R.id.add_tarla) {
                            Intent intent = new Intent(MainActivity.this, Tarla.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                        }
                    if(item.getItemId()==R.id.Anasayfa){
                        Intent intent = new Intent(MainActivity.this, AnasayfaActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                       // intent.putExtra("username",username2);


                        startActivity(intent);
                        finish();
                    }
                    return false;
                }
            });
*/
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



    private void startMqtt() {

        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", mqttMessage.toString());


                DateFormat df=new SimpleDateFormat( " dd.MM.YYYY--HH:MM:ss");
                String date=df.format(Calendar.getInstance().getTime());


                String temp=mqttMessage.toString();
                String [] seperated=temp.split(" ");

                Database db=new Database(getApplicationContext());

                db.sensorVeriEkle(seperated[0],seperated[1],seperated[2],date.toString() );
                func2();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    public void onResume(){

super.onResume();

func3();

    }
    public void func(){
        Database db=new Database(getApplicationContext());

        sensorVeriListe=db.tumSensorVeriGetir();


        if (sensorVeriListe.size()==0){
            Toast.makeText(getApplicationContext(),"Sensor verisi yok ",Toast.LENGTH_LONG).show();
        }
        else{
            sicaklik_Veri=new String[sensorVeriListe.size()];
            nem_Veri=new String[sensorVeriListe.size()];
            isik_Veri=new String[sensorVeriListe.size()];
            tarih_Veri=new String[sensorVeriListe.size()];
            id_Veri= new int[sensorVeriListe.size()];
            tum_Veri=new String[sensorVeriListe.size()];
            for(int i=0; i<sensorVeriListe.size();i++)
            {
                sicaklik_Veri[i]=sensorVeriListe.get(i).get("SENSON_SICAKLIK");
                nem_Veri[i]=sensorVeriListe.get(i).get("SENSOR_NEM");
                isik_Veri[i]=sensorVeriListe.get(i).get("SENSOR_ISIK");
                tarih_Veri[i]=sensorVeriListe.get(i).get("SENSOR_TARIH");
                id_Veri[i]=Integer.parseInt(sensorVeriListe.get(i).get("ID"));

                tum_Veri[i]=tarih_Veri[i]+"    "+sicaklik_Veri[i]+"°C     "+" %"+nem_Veri[i]+"       "+isik_Veri[i]+" Lux";




            }

            lv=(ListView)findViewById(R.id.listView);
            adapter=new ArrayAdapter(this,R.layout.list_item,R.id.list_item_text ,tum_Veri);
            lv.setAdapter(adapter);
            for (int i=0;i<sensorVeriListe.size();i++)
            {

                mChart.addEntry(Float.valueOf(sicaklik_Veri[i].toString()));
                mChart3.addEntry(Float.valueOf(nem_Veri[i].toString()));
                mChart5.addEntry(Float.valueOf(isik_Veri[i].toString()));

            }

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){


                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent=new Intent(getApplicationContext(),SensorDetayActivity.class);
                    intent.putExtra("id", id_Veri[position]);
                    startActivity(intent);

                }
            });



        }
    }
    public void func2(){
        Database db=new Database(getApplicationContext());

        sensorVeriListe=db.tumSensorVeriGetir();


        if (sensorVeriListe.size()==0){
            Toast.makeText(getApplicationContext(),"Sensor verisi yok ",Toast.LENGTH_LONG).show();
        }
        else{
            sicaklik_Veri=new String[sensorVeriListe.size()];
            nem_Veri=new String[sensorVeriListe.size()];
            isik_Veri=new String[sensorVeriListe.size()];
            tarih_Veri=new String[sensorVeriListe.size()];
            id_Veri= new int[sensorVeriListe.size()];
            tum_Veri=new String[sensorVeriListe.size()];
            for(int i=0; i<sensorVeriListe.size();i++)
            {
                sicaklik_Veri[i]=sensorVeriListe.get(i).get("SENSON_SICAKLIK");
                nem_Veri[i]=sensorVeriListe.get(i).get("SENSOR_NEM");
                isik_Veri[i]=sensorVeriListe.get(i).get("SENSOR_ISIK");
                tarih_Veri[i]=sensorVeriListe.get(i).get("SENSOR_TARIH");
                id_Veri[i]=Integer.parseInt(sensorVeriListe.get(i).get("ID"));

                tum_Veri[i]=tarih_Veri[i]+"    "+sicaklik_Veri[i]+"°C     "+" %"+nem_Veri[i]+"       "+isik_Veri[i]+" Lux";




            }

            lv=(ListView)findViewById(R.id.listView);
            adapter=new ArrayAdapter(this,R.layout.list_item,R.id.list_item_text ,tum_Veri);
            lv.setAdapter(adapter);



                mChart.addEntry(Float.valueOf(sicaklik_Veri[sensorVeriListe.size()-1].toString()));
            mChart3.addEntry(Float.valueOf(nem_Veri[sensorVeriListe.size()-1].toString()));
            mChart5.addEntry(Float.valueOf(isik_Veri[sensorVeriListe.size()-1].toString()));




            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){


                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent=new Intent(getApplicationContext(),SensorDetayActivity.class);
                    intent.putExtra("id", id_Veri[position]);
                    startActivity(intent);

                }
            });



        }
    }
    public void func3(){
        Database db=new Database(getApplicationContext());

        sensorVeriListe=db.tumSensorVeriGetir();


        if (sensorVeriListe.size()==0){
            Toast.makeText(getApplicationContext(),"Sensor verisi yok ",Toast.LENGTH_LONG).show();
        }
        else{
            sicaklik_Veri=new String[sensorVeriListe.size()];
            nem_Veri=new String[sensorVeriListe.size()];
            isik_Veri=new String[sensorVeriListe.size()];
            tarih_Veri=new String[sensorVeriListe.size()];
            id_Veri= new int[sensorVeriListe.size()];
            tum_Veri=new String[sensorVeriListe.size()];
            for(int i=0; i<sensorVeriListe.size();i++)
            {
                sicaklik_Veri[i]=sensorVeriListe.get(i).get("SENSON_SICAKLIK");
                nem_Veri[i]=sensorVeriListe.get(i).get("SENSOR_NEM");
                isik_Veri[i]=sensorVeriListe.get(i).get("SENSOR_ISIK");
                tarih_Veri[i]=sensorVeriListe.get(i).get("SENSOR_TARIH");
                id_Veri[i]=Integer.parseInt(sensorVeriListe.get(i).get("ID"));

                tum_Veri[i]=tarih_Veri[i]+"    "+sicaklik_Veri[i]+"°C     "+" %"+nem_Veri[i]+"       "+isik_Veri[i]+" Lux";




            }

            lv=(ListView)findViewById(R.id.listView);
            adapter=new ArrayAdapter(this,R.layout.list_item,R.id.list_item_text ,tum_Veri);
            lv.setAdapter(adapter);
            mChart2=new ChartHelper(chart2);
            mChart4=new ChartHelper(chart4);
            mChart6=new ChartHelper(chart6);
            for (int i=0;i<sensorVeriListe.size();i++)
            {

                mChart2.addEntry(Float.valueOf(sicaklik_Veri[i].toString()));
                mChart4.addEntry(Float.valueOf(nem_Veri[i].toString()));
                mChart6.addEntry(Float.valueOf(isik_Veri[i].toString()));


            }

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){


                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent=new Intent(getApplicationContext(),SensorDetayActivity.class);
                    intent.putExtra("id", id_Veri[position]);
                    startActivity(intent);

                }
            });



        }
    }






}
