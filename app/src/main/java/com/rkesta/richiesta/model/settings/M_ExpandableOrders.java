package com.rkesta.richiesta.model.settings;


import java.util.ArrayList;

public class M_ExpandableOrders {

//    struct SONumber_model {
//        let SONumber : String
//        var ShippingRate: String
//        var AllTotalPrice : String
//        let OrderDate : String
//        let IsComplete : String
//        var SO_Details_list : [MyOrders_model] = [];
//        var isExpanded: Bool == !IsComplete
//    }

    String SONumber  = "" ;
    String ShippingRate = "" ;
    String AllTotalPrice  = "" ;
    String OrderDate  = "" ;
    String discount;
    boolean IsComplete = false ;
    ArrayList<M_OrdersDetails> OrdersDetails_list = new ArrayList<M_OrdersDetails>() ;
    boolean isExpanded = false ;

    public M_ExpandableOrders(String SONumber, String shippingRate, String allTotalPrice, String orderDate, String discount,
                              boolean isComplete, M_OrdersDetails ordersDetails_list, boolean isExpanded) {
        this.SONumber = SONumber;
        ShippingRate = shippingRate;
        AllTotalPrice = allTotalPrice;
        OrderDate = orderDate;
        IsComplete = isComplete;
        this.discount = discount;
        OrdersDetails_list.add(ordersDetails_list);
        this.isExpanded = isExpanded;
    }

    public String getSONumber() {
        return SONumber;
    }

    public void setSONumber(String SONumber) {
        this.SONumber = SONumber;
    }

    public String getShippingRate() {
        return ShippingRate;
    }

    public void setShippingRate(String shippingRate) {
        ShippingRate = shippingRate;
    }

    public String getAllTotalPrice() {
        return AllTotalPrice;
    }

    public void setAllTotalPrice(String allTotalPrice) {
        AllTotalPrice = allTotalPrice;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public boolean isComplete() {
        return IsComplete;
    }

    public void setComplete(boolean complete) {
        IsComplete = complete;
    }

    public ArrayList<M_OrdersDetails> getOrdersDetails_list() {
        return OrdersDetails_list;
    }

    public void AddMore_OrdersDetails(M_OrdersDetails ordersDetails) {
        OrdersDetails_list.add(ordersDetails);
    }
    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
