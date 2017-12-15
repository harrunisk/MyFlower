package com.example.harun.myflower;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Harun on 27-Nov-17.
 */

public class SensorDetayActivity extends Activity{

    Button b1,b2;
    TextView t1,t2,t3,t4;
    int id;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensordetay);

        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);

        t1 = (TextView)findViewById(R.id.tarih);
        t2 = (TextView)findViewById(R.id.sicaklik);
        t3 = (TextView)findViewById(R.id.nem);
        t4 = (TextView)findViewById(R.id.isik);

        Intent intent=getIntent();
        id = intent.getIntExtra("id", 0);
        Database db = new Database(getApplicationContext());
        HashMap<String, String> map = db.idKullanarakSensorVerisiGetir(id);

        t2.setText(map.get("SENSON_SICAKLIK"));
        t3.setText(map.get("SENSOR_NEM").toString());
        t4.setText(map.get("SENSOR_ISIK").toString());
        t1.setText(map.get("SENSOR_TARIH").toString());


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SensorVeriDuzenleActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(SensorDetayActivity.this);
                alertDialog.setTitle("Uyarı");
                alertDialog.setMessage(t1.getText()+" tarihli sensör verisi silinsin mi?");
                alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Database db=new Database(getApplicationContext());
                        db.sensorVeriSil(id);
                        Toast.makeText(getApplicationContext(),"Sensör verisi silindi",Toast.LENGTH_LONG).show();

                        finish();
                    }
                });
                alertDialog.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();



            }
        });





    }
}
