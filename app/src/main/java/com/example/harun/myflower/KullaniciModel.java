package com.example.harun.myflower;

/**
 * Created by mayne on 2.12.2017.
 */

public class KullaniciModel {

    public String email,username,password,isim;

    public KullaniciModel(){}

    public KullaniciModel(String email, String username, String password, String isim) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.isim = isim;
    }

    public String getEmail() {
        return email;
    }

    public String getIsim() {
        return isim;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
