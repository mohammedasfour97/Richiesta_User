package com.rkesta.richiesta.model.home;

import java.util.ArrayList;

public class M_Slider {

     String ID_STORE ;
     String ID_MainCategory ;
     String StoreTagsEN ;
     String StoreTagsAR ;
     String StoreNameEN ;
     String StoreNameAR ;
     String StorePic ;
     ArrayList<M_Store6Pro> Store6Products = new ArrayList<M_Store6Pro>();



     public ArrayList<M_Store6Pro> getStore6Products() {
          return Store6Products;
     }

     public void setStore6Products(ArrayList<M_Store6Pro> store6Products) {
          Store6Products = store6Products;
     }

     public void append_Store6Products(M_Store6Pro Store6Pro) {
          Store6Products.add(Store6Pro);
     }

     public M_Slider(String ID_STORE, String ID_MainCategory, String storeTagsEN, String storeTagsAR, String storeNameEN, String storeNameAR, String storePic) {
          this.ID_STORE = ID_STORE;
          this.ID_MainCategory = ID_MainCategory;
          this.StoreTagsEN = storeTagsEN;
          this.StoreTagsAR = storeTagsAR;
          this.StoreNameEN = storeNameEN;
          this.StoreNameAR = storeNameAR;
          this.StorePic = storePic;
     }

     public String getID_STORE() {
          return ID_STORE;
     }

     public String getID_MainCategory() {
          return ID_MainCategory;
     }

     public String getStoreTagsEN() {
          return StoreTagsEN;
     }

     public String getStoreTagsAR() {
          return StoreTagsAR;
     }

     public String getStoreNameEN() {
          return StoreNameEN;
     }

     public String getStoreNameAR() {
          return StoreNameAR;
     }

     public String getStorePic() {
          return StorePic;
     }


     public void setID_STORE(String ID_STORE) {
          this.ID_STORE = ID_STORE;
     }

     public void setID_MainCategory(String ID_MainCategory) {
          this.ID_MainCategory = ID_MainCategory;
     }

     public void setStoreTagsEN(String storeTagsEN) {
          StoreTagsEN = storeTagsEN;
     }

     public void setStoreTagsAR(String storeTagsAR) {
          StoreTagsAR = storeTagsAR;
     }

     public void setStoreNameEN(String storeNameEN) {
          StoreNameEN = storeNameEN;
     }

     public void setStoreNameAR(String storeNameAR) {
          StoreNameAR = storeNameAR;
     }

     public void setStorePic(String storePic) {
          StorePic = storePic;
     }

}

