package com.example.harun.myflower;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.id;
import static android.R.attr.version;

/**
 * Created by Harun on 26-Nov-17.
 */

public class Database  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static  final String DATABASE_NAME="BITKIM";

    private static final String TABLE_NAME = "SENSORLER";
    private static String ID="ID";
    private static String SENSOR_SICAKLIK="SENSON_SICAKLIK";
    private static String SENSOR_NEM="SENSOR_NEM";
    private static String SENSOR_ISIK="SENSOR_ISIK";
    private static String SENSOR_TARIH="SENSOR_TARIH";





    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("
        +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
        +SENSOR_SICAKLIK+" TEXT,"
        +SENSOR_NEM+" TEXT,"
        +SENSOR_ISIK+" TEXT,"
        +SENSOR_TARIH+" TEXT"+")";
        db.execSQL(CREATE_TABLE);
    }

    public void sensorVeriSil(int id){

        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME ,ID+" =?",new String []{String.valueOf(id)});
        db.close();

    }
    public void sensorVeriEkle(String sensorSicaklik,String sensorNem,String sensorIsik,String sensorTarih){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(SENSOR_SICAKLIK,sensorSicaklik);
        values.put(SENSOR_NEM,sensorNem);
        values.put(SENSOR_ISIK,sensorIsik);
        values.put(SENSOR_TARIH,sensorTarih);
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public HashMap<String,String> idKullanarakSensorVerisiGetir(int id)
    {
        HashMap<String,String> sensorVeri=new HashMap<String,String>();
        String selectQuery="SELECT * FROM "+TABLE_NAME+" WHERE ID="+id;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            sensorVeri.put(SENSOR_SICAKLIK,cursor.getString(1));
            sensorVeri.put(SENSOR_NEM,cursor.getString(2));
            sensorVeri.put(SENSOR_ISIK,cursor.getString(3));
            sensorVeri.put(SENSOR_TARIH,cursor.getString(4));

        }
        cursor.close();
        db.close();

        return sensorVeri;
    }



    public HashMap<String,String> TarihKullanarakSensorVerisiGetir(String Tarih)
    {
        HashMap<String,String> sensorVeri=new HashMap<String,String>();
        String selectQuery="SELECT * FROM "+TABLE_NAME+" WHERE SENSOR_TARIH="+Tarih;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            sensorVeri.put(SENSOR_SICAKLIK,cursor.getString(1));
            sensorVeri.put(SENSOR_NEM,cursor.getString(2));
            sensorVeri.put(SENSOR_ISIK,cursor.getString(3));
            sensorVeri.put(SENSOR_TARIH,cursor.getString(4));

        }
        cursor.close();
        db.close();

        return sensorVeri;
    }


    public ArrayList<HashMap<String,String>> tumSensorVeriGetir(){
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery="SELECT *FROM "+TABLE_NAME;
        Cursor cursor=db.rawQuery(selectQuery,null);
            ArrayList<HashMap<String,String>> tumSensorVeri=new ArrayList<HashMap<String, String>>();


            if (cursor.moveToFirst()){
                do{

                    HashMap<String,String> map=new HashMap<String, String>();
                    for(int i=0; i<cursor.getColumnCount();i++)
                    {
                        map.put(cursor.getColumnName(i),cursor.getString(i));

                    }
                    tumSensorVeri.add(map);



                }while (cursor.moveToNext());




            }
            db.close();
        return tumSensorVeri;

    }

    public void sensorVeriDuzenle(String sicaklik,String nem,String isik,String tarih,int id){
        SQLiteDatabase db =this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(SENSOR_SICAKLIK,sicaklik);
        values.put(SENSOR_NEM,nem);
        values.put(SENSOR_ISIK,isik);
        values.put(SENSOR_TARIH,tarih);

        db.update(TABLE_NAME, values,ID +" = ?",
                new String []{String.valueOf(id)});

    }

    public int getRowCount(){
        String countQuery="SELECT *FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(countQuery,null);
        int rowCount=cursor.getCount();
        db.close();
        cursor.close();
        return rowCount;

    }
    public void resetTables(){
        SQLiteDatabase db=this.getReadableDatabase();
        db.delete(TABLE_NAME,null,null);

        db.close();
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
