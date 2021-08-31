package com.rkesta.richiesta.model.settings;

public class M_OrdersDetails {
    /**
     //    ID
     //    SONumber
     //    StoreId
     //    RKPrdNameAR
     //    RKPrdNameEN
     //    ProductPic
     //    RK_Branch
     //    RK_Products
     //    ProductColor
     //    ProductAdditionals
     //    ProductSize
     //    ColorUnitPrice
     //    AdditionalUnitPrice
     //    SizeUnitPrice
     //    SalesUnitPrice
     //    Qty
     //    TotalPrice
     //    OrderNotes
     //    IsComplete
     //    OrderDate
     //    RK_User
     //    RK_User_DetailID
     //    Notes
     */

    String ID = "" ;
    String         SONumber = "" ;
    String     StoreId = "" ;
    String             RKPrdNameAR = "" ;
    String     RKPrdNameEN = "" ;
    String             ProductPic = "" ;
    String     RK_Branch = "" ;
    String             RK_Products = "" ;
    String     ProductColor = "" ;
    String             ProductAdditionals = "" ;
    String     ProductSize = "" ;
    String             ColorUnitPrice = "" ;
    String     AdditionalUnitPrice = "" ;
    String             SizeUnitPrice = "" ;
    String     SalesUnitPrice = "" ;
    String             Qty = "" ;
    String     TotalPrice = "" ;
    String             OrderNotes = "" ;
    String     IsComplete = "" ;
    String             OrderDate = "" ;
    String     RK_User = "" ;
    String             RK_User_DetailID = "" ;
    String     Notes = "" ;

    public M_OrdersDetails() {
    }

    public M_OrdersDetails(String ID, String SONumber, String storeId, String RKPrdNameAR, String RKPrdNameEN, String productPic, String RK_Branch, String RK_Products, String productColor, String productAdditionals, String productSize, String colorUnitPrice, String additionalUnitPrice, String sizeUnitPrice, String salesUnitPrice, String qty, String totalPrice, String orderNotes, String isComplete, String orderDate, String RK_User, String RK_User_DetailID, String notes) {
        this.ID = ID;
        this.SONumber = SONumber;
        StoreId = storeId;
        this.RKPrdNameAR = RKPrdNameAR;
        this.RKPrdNameEN = RKPrdNameEN;
        ProductPic = productPic;
        this.RK_Branch = RK_Branch;
        this.RK_Products = RK_Products;
        ProductColor = productColor;
        ProductAdditionals = productAdditionals;
        ProductSize = productSize;
        ColorUnitPrice = colorUnitPrice;
        AdditionalUnitPrice = additionalUnitPrice;
        SizeUnitPrice = sizeUnitPrice;
        SalesUnitPrice = salesUnitPrice;
        Qty = qty;
        TotalPrice = totalPrice;
        OrderNotes = orderNotes;
        IsComplete = isComplete;
        OrderDate = orderDate;
        this.RK_User = RK_User;
        this.RK_User_DetailID = RK_User_DetailID;
        Notes = notes;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSONumber() {
        return SONumber;
    }

    public void setSONumber(String SONumber) {
        this.SONumber = SONumber;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getRKPrdNameAR() {
        return RKPrdNameAR;
    }

    public void setRKPrdNameAR(String RKPrdNameAR) {
        this.RKPrdNameAR = RKPrdNameAR;
    }

    public String getRKPrdNameEN() {
        return RKPrdNameEN;
    }

    public void setRKPrdNameEN(String RKPrdNameEN) {
        this.RKPrdNameEN = RKPrdNameEN;
    }

    public String getProductPic() {
        return ProductPic;
    }

    public void setProductPic(String productPic) {
        ProductPic = productPic;
    }

    public String getRK_Branch() {
        return RK_Branch;
    }

    public void setRK_Branch(String RK_Branch) {
        this.RK_Branch = RK_Branch;
    }

    public String getRK_Products() {
        return RK_Products;
    }

    public void setRK_Products(String RK_Products) {
        this.RK_Products = RK_Products;
    }

    public String getProductColor() {
        return ProductColor;
    }

    public void setProductColor(String productColor) {
        ProductColor = productColor;
    }

    public String getProductAdditionals() {
        return ProductAdditionals;
    }

    public void setProductAdditionals(String productAdditionals) {
        ProductAdditionals = productAdditionals;
    }

    public String getProductSize() {
        return ProductSize;
    }

    public void setProductSize(String productSize) {
        ProductSize = productSize;
    }

    public String getColorUnitPrice() {
        return ColorUnitPrice;
    }

    public void setColorUnitPrice(String colorUnitPrice) {
        ColorUnitPrice = colorUnitPrice;
    }

    public String getAdditionalUnitPrice() {
        return AdditionalUnitPrice;
    }

    public void setAdditionalUnitPrice(String additionalUnitPrice) {
        AdditionalUnitPrice = additionalUnitPrice;
    }

    public String getSizeUnitPrice() {
        return SizeUnitPrice;
    }

    public void setSizeUnitPrice(String sizeUnitPrice) {
        SizeUnitPrice = sizeUnitPrice;
    }

    public String getSalesUnitPrice() {
        return SalesUnitPrice;
    }

    public void setSalesUnitPrice(String salesUnitPrice) {
        SalesUnitPrice = salesUnitPrice;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getOrderNotes() {
        return OrderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        OrderNotes = orderNotes;
    }

    public String getIsComplete() {
        return IsComplete;
    }

    public void setIsComplete(String isComplete) {
        IsComplete = isComplete;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getRK_User() {
        return RK_User;
    }

    public void setRK_User(String RK_User) {
        this.RK_User = RK_User;
    }

    public String getRK_User_DetailID() {
        return RK_User_DetailID;
    }

    public void setRK_User_DetailID(String RK_User_DetailID) {
        this.RK_User_DetailID = RK_User_DetailID;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }
}
