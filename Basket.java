package com.example.gimgiyun.firebase;

/**
 * Created by gimgiyun on 2017. 11. 29..
 */

public class Basket {

    public String email;
    public String item;

    public Basket() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Basket(String email, String item) {
        this.email = email;
        this.item = item;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}