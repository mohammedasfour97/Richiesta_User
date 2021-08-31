package com.rkesta.richiesta.model.home.CSA;

public class M_CSA_Additional {

    String ProductID = "";
    String ID = "";
    String HexCode = "";
    String NameEN = "";
    String NameAR = "";
    String Price = "";


    public M_CSA_Additional(String productID, String ID, String hexCode, String nameEN, String nameAR, String price) {
        ProductID = productID;
        this.ID = ID;
        HexCode = hexCode;
        NameEN = nameEN;
        NameAR = nameAR;
        Price = price;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getHexCode() {
        return HexCode;
    }

    public void setHexCode(String hexCode) {
        HexCode = hexCode;
    }

    public String getNameEN() {
        return NameEN;
    }

    public void setNameEN(String nameEN) {
        NameEN = nameEN;
    }

    public String getNameAR() {
        return NameAR;
    }

    public void setNameAR(String nameAR) {
        NameAR = nameAR;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
