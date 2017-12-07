package com.example.harun.myflower.CardFragment;


import java.util.ArrayList;

public class CardItem {
   // public String secilen_item;
    public ArrayList<String> SpinnerListe;
    private int mTitleResource;





    public CardItem(int title,ArrayList<String> spinnerListe) {
        mTitleResource = title;
        SpinnerListe = spinnerListe;
       // this.secilen_item=secilen_item2;
    }

    public ArrayList<String> getSpinnerListe() {
        return SpinnerListe;
    }

    public int getTitle() {
        return mTitleResource;
    }


    public int getCount() {
        return SpinnerListe.size();
    }
   /* public String getSecilen_item() {
        return secilen_item;
    }*/

   /* public void setSecilen_item(String secilen_item) {
        this.secilen_item = secilen_item;
    }*/

   /* @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        //if(position>0) {

            setSecilen_item(adapterView.getItemAtPosition(position).toString());
            //contentTextView.setText("aaa" +item.getItemId(position));
        //}
    }*/

   /* @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/

    public Object getItem(int position) {
        return SpinnerListe.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

}
