package com.example.harun.myflower;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Harun on 27-Nov-17.
 */

public class SensorVeriDuzenleActivity  extends Activity {
    Button b1;
    EditText e1,e2,e3,e4;
    int id;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensorveriduzenle);
        b1 = (Button)findViewById(R.id.button1);
        e1 = (EditText)findViewById(R.id.editText1);
        e2 = (EditText)findViewById(R.id.editText2);
        e3 = (EditText)findViewById(R.id.editText3);
        e4 = (EditText)findViewById(R.id.editText4);
        final Intent intent=getIntent();
        id = intent.getIntExtra("id", 0);

        Database db = new Database(getApplicationContext());
        HashMap<String, String> map = db.idKullanarakSensorVerisiGetir(id);

        e1.setText(map.get("SENSOR_TARIH"));
        e2.setText(map.get("SENSON_SICAKLIK"));
        e3.setText(map.get("SENSOR_NEM"));
        e4.setText(map.get("SENSOR_ISIK"));
        b1.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                String tarih,sicaklik,nem,isik;
                tarih=e1.getText().toString();
                sicaklik=e2.getText().toString();
                nem=e3.getText().toString();
                isik=e4.getText().toString();
                if(tarih.matches("") ||sicaklik.matches("")||nem.matches("")||isik.matches("")){

                    Toast.makeText(getApplicationContext(),"Tüm bilgileri eksiksiz doldurunuz",Toast.LENGTH_LONG).show();

                }
                else{
                    Database db =new Database(getApplicationContext());
                    db.sensorVeriDuzenle(sicaklik,nem,isik,tarih,id);
                    db.close();
                    Toast.makeText(getApplicationContext(), "Sensör verisi düzenlendi", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();




                }

            }
        });
    }
}
