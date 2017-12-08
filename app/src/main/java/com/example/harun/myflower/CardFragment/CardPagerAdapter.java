package com.example.harun.myflower.CardFragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harun.myflower.R;
import com.example.harun.myflower.TarlaEkle;

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {





    private List<CardView> mViews;
    private ArrayList<CardItem> mData;
    private float mBaseElevation;
    Activity activity;
    public String secilen_item;
    public String toprakTipi;
    public String sulamaTipi;
    public String urun;
    public String urunCesidi;

    //  public TextView contentTextView;

    public CardPagerAdapter(Activity activity) {
        mData = new ArrayList<>();
        this.activity = activity;
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);

        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItem item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        final TextView contentTextView = (TextView) view.findViewById(R.id.titleTextView2);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner1);
        titleTextView.setText(item.getTitle());
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, item.getSpinnerListe());
        spinner.setAdapter(dataAdapter);



        //contentTextView.setPaintFlags(contentTextView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        //contentTextView.setText(item.getText());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position>0) {
                    String type=dataAdapter.getItem(0);
                    if (type=="Ürün"){
                        secilen_item=adapterView.getItemAtPosition(position).toString();
                        //buradaki veriyi tarla ekleye göndermek lazım
                        contentTextView.setText(secilen_item);
                        urun=secilen_item.toString();
                    }
                    else if (type=="Ürün Çeşidi"){
                        secilen_item=adapterView.getItemAtPosition(position).toString();
                        //buradaki veriyi tarla ekleye göndermek lazım
                        contentTextView.setText(secilen_item);
                        urunCesidi=secilen_item.toString();
                    }
                        else if (type=="Sulama Tipi"){
                        secilen_item=adapterView.getItemAtPosition(position).toString();
                        //buradaki veriyi tarla ekleye göndermek lazım
                        contentTextView.setText(secilen_item);
                        sulamaTipi=secilen_item.toString();
                    }
                    else if (type=="Toprak Tipi"){
                        secilen_item=adapterView.getItemAtPosition(position).toString();
                        //buradaki veriyi tarla ekleye göndermek lazım
                        contentTextView.setText(secilen_item);
                        toprakTipi=secilen_item.toString();
                    }



                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


/*

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/
}
