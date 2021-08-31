package com.rkesta.richiesta.model.home;

import java.util.ArrayList;

public class M_Expandablecategory {
    String name = "" ;
    ArrayList<M_Product> product_list = new ArrayList<M_Product>() ;
    boolean isExpanded = false ;

    public M_Expandablecategory(String name, ArrayList<M_Product> product_list, boolean isExpanded) {
        this.name = name;
        this.product_list = product_list;
        this.isExpanded = isExpanded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<M_Product> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(ArrayList<M_Product> product_list) {
        this.product_list = product_list;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
