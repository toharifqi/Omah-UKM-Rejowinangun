package com.toharifqi.um.ukmq.model;

import android.content.Intent;

public class ProductModel {
    String productPic, productName, productCity, productId, productCat, productCode, productDesc;
    int productPrice, productStock;

    public ProductModel() {
    }

    public ProductModel(String productPic, String productName, String productCity, String productId, String productCat, String productCode, String productDesc, int productPrice, int productStock) {
        this.productPic = productPic;
        this.productName = productName;
        this.productCity = productCity;
        this.productId = productId;
        this.productCat = productCat;
        this.productCode = productCode;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.productStock = productStock;
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

    public String getProductCat() {
        return productCat;
    }

    public void setProductCat(String productCat) {
        this.productCat = productCat;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }
}
