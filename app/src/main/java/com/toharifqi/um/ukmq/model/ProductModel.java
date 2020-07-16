package com.toharifqi.um.ukmq.model;

import android.content.Intent;

public class ProductModel {
    String productPic, productName, productCity, productId;
    int productPrice;

    public ProductModel() {
    }

    public ProductModel(String productPic, String productName, int productPrice, String productCity, String productId) {
        this.productPic = productPic;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCity = productCity;
        this.productId = productId;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCity() {
        return productCity;
    }

    public void setProductCity(String productCity) {
        this.productCity = productCity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
