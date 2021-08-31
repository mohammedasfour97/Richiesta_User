package com.rkesta.richiesta.model.home;

public class M_maincat {
     String RKStoreCategoryID = "";
     String StoreCategoryPic = "";
     String StoreCategoryNameAr = "";
     String StoreCategoryNameEn = "";
     Boolean IS_Selected = false;

    public M_maincat(String RKStoreCategoryID, String StoreCategoryPic, String StoreCategoryNameAr, String StoreCategoryNameEn, Boolean IS_Selected) {
        this.RKStoreCategoryID = RKStoreCategoryID;
        this.StoreCategoryPic = StoreCategoryPic;
        this.StoreCategoryNameAr = StoreCategoryNameAr;
        this.StoreCategoryNameEn = StoreCategoryNameEn;
        this.IS_Selected = IS_Selected;
    }

    public void setIS_Selected(Boolean IS_Selected) {
        this.IS_Selected = IS_Selected;
    }

    public String getRKStoreCategoryID() {
        return RKStoreCategoryID;
    }

    public String getStoreCategoryPic() {
        return StoreCategoryPic;
    }

    public String getStoreCategoryNameAr() {
        return StoreCategoryNameAr;
    }

    public String getStoreCategoryNameEn() {
        return StoreCategoryNameEn;
    }

    public Boolean getIS_Selected() {
        return IS_Selected;
    }
}
