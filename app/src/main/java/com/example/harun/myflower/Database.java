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


    //Sensör verisi
    private static final String TABLE_NAME = "SENSORLER";
    private static String ID="ID";
    private static String SENSOR_SICAKLIK="SENSON_SICAKLIK";
    private static String SENSOR_NEM="SENSOR_NEM";
    private static String SENSOR_ISIK="SENSOR_ISIK";
    private static String SENSOR_TARIH="SENSOR_TARIH";
    //Tarla tablosu
    private static final String TARLA="TARLA";
    private static String TARLA_ID="ID";
    private static String TARLA_ADI="TARLA_ADI";
    private static String TARLA_BUYUKLUK="TARLA_BUYUKLUK";
    private static String TARLA_VERIM="TARLA_VERIM";
    private static String TARLA_URUN="TARLA_URUN";
    private static String TARLA_URUN_CESID="TARLA_URUN_CESID";
    private static String TARLA_TOPRAK="TARLA_TOPRAK";
    private static String TARLA_SULAMA="TARLA_SULAMA";
    private static String TARLA_YER="TARLA_YER";
    private static String TARLA_HASAT_TARIH="TARLA_HASAT_TARIH";
    private static String TARLA_EKIM_TARIH="TARLA_EKIM_TARIH";












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

        //TARLA SQL code
        String CREATE_TARLA_TABLE="CREATE TABLE "+TARLA+"("
                +TARLA_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +TARLA_ADI+" TEXT,"
                +TARLA_BUYUKLUK+" INTEGER,"
                +TARLA_VERIM+" INTEGER,"
                +TARLA_URUN+" TEXT,"
                +TARLA_URUN_CESID+" TEXT,"
                +TARLA_TOPRAK+" TEXT,"
                +TARLA_SULAMA+" TEXT,"
                +TARLA_YER+" TEXT,"
                +TARLA_HASAT_TARIH+" TEXT,"
                +TARLA_EKIM_TARIH+" TEXT"+")";


        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TARLA_TABLE);
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
    public HashMap<String,String> TarihKullanarakSensorVerisiGetir(String Tarih){
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



    public void tarlaEkle(String tarlaAdi,int tarlaBuyukluk,int tarlaVerim,String tarlaUrun,String tarlaUrunCesid,String tarlaToprak
            ,String tarlaSulama,String tarlaYer,String tarlaHasatTarih,String tarlaEkimTarih){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(TARLA_ADI,tarlaAdi);
        values.put(TARLA_BUYUKLUK,tarlaBuyukluk);
        values.put(TARLA_VERIM,tarlaVerim);
        values.put(TARLA_URUN,tarlaUrun);
        values.put(TARLA_URUN_CESID,tarlaUrunCesid);
        values.put(TARLA_TOPRAK,tarlaToprak);
        values.put(TARLA_SULAMA,tarlaSulama);
        values.put(TARLA_YER,tarlaYer);
        values.put(TARLA_HASAT_TARIH,tarlaHasatTarih);
        values.put(TARLA_EKIM_TARIH,TARLA_EKIM_TARIH);
        db.insert(TARLA,null,values);
        db.close();




    }

    public HashMap<String,String> idTarlaVeri(int id){
        HashMap<String,String> tarlaVeri=new HashMap<String, String>();
        String selectQuery="SELECT * FROM "+TARLA+" WHERE ID="+id;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            tarlaVeri.put(TARLA_ADI,cursor.getString(1));
            tarlaVeri.put(TARLA_BUYUKLUK,cursor.getString(2));
            tarlaVeri.put(TARLA_VERIM,cursor.getString(3));
            tarlaVeri.put(TARLA_URUN,cursor.getString(4));
            tarlaVeri.put(TARLA_URUN_CESID,cursor.getString(5));
            tarlaVeri.put(TARLA_TOPRAK,cursor.getString(6));
            tarlaVeri.put(TARLA_SULAMA,cursor.getString(7));
            tarlaVeri.put(TARLA_YER,cursor.getString(8));
            tarlaVeri.put(TARLA_HASAT_TARIH,cursor.getString(9));
            tarlaVeri.put(TARLA_EKIM_TARIH,cursor.getString(10));
        }
        cursor.close();
        db.close();
        return tarlaVeri;

    }
    public ArrayList<HashMap<String,String>> tumTarlalarıGetir(){
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery="SELECT *FROM "+TARLA;
        Cursor cursor=db.rawQuery(selectQuery,null);
        ArrayList<HashMap<String,String>> tumTarlalar=new ArrayList<HashMap<String,String>>();
        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> map=new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i),cursor.getString(i));

                }
                tumTarlalar.add(map);

            }while (cursor.moveToNext());


        }
        db.close();
        return tumTarlalar;


    }







    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
