package com.toharifqi.um.ukmq.model;

import android.content.Intent;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ProductModel {
    String productCode, productName, productDesc, productCat, productCity, productId, productPic;
    int productPrice, productStock;

    public ProductModel() {
    }

    public ProductModel(String productCode, String productName, String productDesc, String productCat, String productCity, String productId, String productPic, int productPrice, int productStock) {
        this.productCode = productCode;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productCat = productCat;
        this.productCity = productCity;
        this.productId = productId;
        this.productPic = productPic;
        this.productPrice = productPrice;
        this.productStock = productStock;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductCat() {
        return productCat;
    }

    public void setProductCat(String productCat) {
        this.productCat = productCat;
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

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
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

    public Map<String, Object> addProduct() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("productCode", productCode);
        result.put("productName", productName);
        result.put("productDesc", productDesc);
        result.put("productCat", productCat);
        result.put("productCity", productCity);
        result.put("productId", productId);
        result.put("productPic", productPic);
        result.put("productPrice", productPrice);
        result.put("productStock", productStock);
        return  result;
    }
}
