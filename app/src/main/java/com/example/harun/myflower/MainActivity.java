package com.example.harun.myflower;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    ChartHelper mChart;
    LineChart chart;


    ListView lv;
    ArrayAdapter adapter;
    ArrayList<HashMap<String,String>> sensorVeriListe;
    String sicaklik_Veri[];
    String nem_Veri[];
    String isik_Veri[];
    String tum_Veri[];
    int k = 88;
    int id_Veri[];
    String tarih_Veri[];






    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = (LineChart) findViewById(R.id.chart);
        mChart = new ChartHelper(chart);






        startMqtt();



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
                func();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    public void onResume(){

super.onResume();
        func();


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

            }

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){


                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent=new Intent(getApplicationContext(),SensorDetayActivity.class);
                    intent.putExtra("id",(int)id_Veri[position]);
                    startActivity(intent);

                }
            });



        }
    }






}
