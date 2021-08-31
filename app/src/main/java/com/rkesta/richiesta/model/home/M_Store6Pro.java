package com.rkesta.richiesta.model.home;

public class M_Store6Pro {
     String ProNameAR ;
     String ProNameEN ;
     String Proprice ;
     String Proimage ;

    public M_Store6Pro(String proNameAR, String proNameEN, String proprice, String proimage) {
        ProNameAR = proNameAR;
        ProNameEN = proNameEN;
        Proprice = proprice;
        Proimage = proimage;
    }

    public String getProNameAR() {
        return ProNameAR;
    }

    public void setProNameAR(String proNameAR) {
        ProNameAR = proNameAR;
    }

    public String getProNameEN() {
        return ProNameEN;
    }

    public void setProNameEN(String proNameEN) {
        ProNameEN = proNameEN;
    }

    public String getProprice() {
        return Proprice;
    }

    public void setProprice(String proprice) {
        Proprice = proprice;
    }

    public String getProimage() {
        return Proimage;
    }

    public void setProimage(String proimage) {
        Proimage = proimage;
    }
}
