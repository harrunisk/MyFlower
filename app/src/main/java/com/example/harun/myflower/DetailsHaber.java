package com.example.harun.myflower;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsHaber extends AppCompatActivity {

    TextView detaylar,baslik;
    ImageView resim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_haber);


        detaylar=(TextView)findViewById(R.id.detaylar);
        baslik=(TextView)findViewById(R.id.haber_baslik);
        resim=(ImageView)findViewById(R.id.haber_resim);

        getSupportActionBar().setTitle("Haber Detayları");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extra=getIntent().getExtras();
        String yazı=extra.getString("detaylar");
        String haber=extra.getString("haber");
        String resim2=extra.getString("resim");

        Picasso.with(getApplicationContext()).load(resim2).into(resim);

        detaylar.setText(Html.fromHtml(yazı));
        baslik.setText(Html.fromHtml(haber));



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
