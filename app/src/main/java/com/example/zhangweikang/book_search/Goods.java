package com.example.zhangweikang.book_search;

public class Goods {
    public String Image;
    public String good_name;
    public String price;
    public String price_now;
    public String url;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getGood_name() {
        return good_name;
    }

    public String getPrice() {
        return price;
    }


    public String getSell() {
        return price_now;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }


    public void setPrice(String price) {
        this.price = price;
    }

    public void setSell(String sell) {
        this.price_now = sell;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
