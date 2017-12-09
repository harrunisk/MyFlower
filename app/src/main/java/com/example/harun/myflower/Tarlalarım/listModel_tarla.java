package com.example.harun.myflower.Tarlalarım;

/**
 * Created by mayne on 9.12.2017.
 */

public class listModel_tarla {
    String tarla_adi;
    String urun;
    String urunCesid;
    String tvHasat;
    String tvEkim;
    String id;
    public listModel_tarla(String tarla_adi, String urun, String urunCesid, String tvHasat, String tvEkim, String id, String string, String cursorString) {
        this.tarla_adi = tarla_adi;
        this.urun = urun;
        this.urunCesid = urunCesid;
        this.tvHasat = tvHasat;
        this.tvEkim = tvEkim;
        this.id=id;
    }

    public String getTarla_adi() {
        return tarla_adi;
    }

    public void setTarla_adi(String tarla_adi) {
        this.tarla_adi = tarla_adi;
    }

    public String getUrun() {
        return urun;
    }

    public void setUrun(String urun) {
        this.urun = urun;
    }

    public String getUrunCesid() {
        return urunCesid;
    }

    public void setUrunCesid(String urunCesid) {
        this.urunCesid = urunCesid;
    }

    public String getTvHasat() {
        return tvHasat;
    }

    public void setTvHasat(String tvHasat) {
        this.tvHasat = tvHasat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTvEkim() {
        return tvEkim;
    }

    public void setTvEkim(String tvEkim) {
        this.tvEkim = tvEkim;
    }
}
