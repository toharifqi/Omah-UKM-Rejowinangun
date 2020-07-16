package com.toharifqi.um.ukmq.model;

public class ProductModel {
    String productPic, productName, productPrice, productCity;

    public ProductModel() {
    }

    public ProductModel(String productPic, String productName, String productPrice, String productCity) {
        this.productPic = productPic;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCity = productCity;
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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCity() {
        return productCity;
    }

    public void setProductCity(String productCity) {
        this.productCity = productCity;
    }
}
