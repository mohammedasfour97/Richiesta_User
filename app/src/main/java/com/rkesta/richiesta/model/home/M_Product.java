package com.rkesta.richiesta.model.home;

public class M_Product {

    String product_ID = "";
    String nameEN = "";
    String nameAR = "";
    String DescEn = "";
    String DescAr = "";
    String price = "";
    String imageurl = "";
    String UnitType = "";
    String UnitTypeAr = "";
    String category_ID = "";
    String branch_ID = "";

    public M_Product(String product_ID, String nameEN, String nameAR, String descEn, String descAr, String price, String imageurl, String unitType, String unitTypeAr, String category_ID, String branch_ID) {
        this.product_ID = product_ID;
        this.nameEN = nameEN;
        this.nameAR = nameAR;
        DescEn = descEn;
        DescAr = descAr;
        this.price = price;
        this.imageurl = imageurl;
        UnitType = unitType;
        UnitTypeAr = unitTypeAr;
        this.category_ID = category_ID;
        this.branch_ID = branch_ID;
    }

    public String getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public String getNameAR() {
        return nameAR;
    }

    public void setNameAR(String nameAR) {
        this.nameAR = nameAR;
    }

    public String getDescEn() {
        return DescEn;
    }

    public void setDescEn(String descEn) {
        DescEn = descEn;
    }

    public String getDescAr() {
        return DescAr;
    }

    public void setDescAr(String descAr) {
        DescAr = descAr;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getUnitType() {
        return UnitType;
    }

    public void setUnitType(String unitType) {
        UnitType = unitType;
    }

    public String getUnitTypeAr() {
        return UnitTypeAr;
    }

    public void setUnitTypeAr(String unitTypeAr) {
        UnitTypeAr = unitTypeAr;
    }

    public String getCategory_ID() {
        return category_ID;
    }

    public void setCategory_ID(String category_ID) {
        this.category_ID = category_ID;
    }

    public String getBranch_ID() {
        return branch_ID;
    }

    public void setBranch_ID(String branch_ID) {
        this.branch_ID = branch_ID;
    }
}
