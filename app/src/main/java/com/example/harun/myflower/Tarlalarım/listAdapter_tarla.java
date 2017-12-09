package com.example.harun.myflower.Tarlalarım;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.harun.myflower.Database;
import com.example.harun.myflower.R;

import java.util.ArrayList;

/**
 * Created by mayne on 9.12.2017.
 */

public class listAdapter_tarla extends BaseAdapter {

    public ArrayList<listModel_tarla> listModel_tarlalarım;
    Database databaseHelper;
    Activity activity;

    public listAdapter_tarla(ArrayList<listModel_tarla> listModel_tarlalarım, Activity activity) {
        super();
        databaseHelper = new Database(activity);
        this.listModel_tarlalarım = listModel_tarlalarım;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listModel_tarlalarım.size();
    }

    @Override
    public Object getItem(int position) {
        return listModel_tarlalarım.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class Listeİcerik {
        TextView tarla_adi;
        TextView urun;
        TextView urunCesit;
        TextView tvHasat;
        TextView tvEkim;
        TextView id;

    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        listAdapter_tarla.Listeİcerik liste;

        LayoutInflater inf = activity.getLayoutInflater();
        if (view == null) {
            view = inf.inflate(R.layout.list_satir, null);

            liste = new listAdapter_tarla.Listeİcerik();

            liste.tarla_adi = (TextView) view.findViewById(R.id.tarla_adi);
            liste.urun = (TextView) view.findViewById(R.id.urun);
            liste.urunCesit = (TextView) view.findViewById(R.id.urunCesit);
            liste.tvHasat = (TextView) view.findViewById(R.id.tvHasat);
            liste.tvEkim = (TextView) view.findViewById(R.id.tvEkim);
            liste.id = (TextView) view.findViewById(R.id.iddeger);


            view.setTag(liste);

        } else {

            liste = (Listeİcerik) view.getTag();

        }

/**
 *
 *TANIMLAMALARI YAPTIKTAN SONRA DA LİSTE YAPIMDAN liste2 nesnesi alıp  veritabanından gönderdiğim tarlalarım.add diye
 *listmodelimdeki constructura oraya gönderdiklerimi burada get ile geri alıp yerleştiriyorum....
 *
 */
        final listModel_tarla liste2 = listModel_tarlalarım.get(position);

        liste.tarla_adi.setText(liste2.getTarla_adi());
        liste.urun.setText(liste2.getUrun());
        liste.urunCesit.setText(liste2.getUrunCesid());
        liste.tvHasat.setText(liste2.getTvHasat());
        liste.tvEkim.setText(liste2.getTvEkim());
        liste.id.setText(liste2.getId());
        notifyDataSetChanged();






        return view;
    }


}
