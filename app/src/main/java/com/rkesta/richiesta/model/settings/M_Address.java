package com.rkesta.richiesta.model.settings;

public class M_Address {
    String ID = "" ;
    String RKUserId = "" ;
    String RKAddress = "" ;
    String RKBldngNum = "" ;
    String RKFloorNum = "" ;
    String RKAptNum = "" ;
    String RKCity = "" ;
    String RKCountry = "" ;
    String Createdby = "" ;
    String Notes = "" ;
    String Longitude = "" ;
    String Latitude = "" ;

    public M_Address(String ID, String RKUserId, String RKAddress, String RKBldngNum, String RKFloorNum, String RKAptNum,
                     String RKCity, String RKCountry, String createdby, String notes, String latitude, String longitude) {
        this.ID = ID;
        this.RKUserId = RKUserId;
        this.RKAddress = RKAddress;
        this.RKBldngNum = RKBldngNum;
        this.RKFloorNum = RKFloorNum;
        this.RKAptNum = RKAptNum;
        this.RKCity = RKCity;
        this.RKCountry = RKCountry;
        Createdby = createdby;
        Notes = notes;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRKUserId() {
        return RKUserId;
    }

    public void setRKUserId(String RKUserId) {
        this.RKUserId = RKUserId;
    }

    public String getRKAddress() {
        return RKAddress;
    }

    public void setRKAddress(String RKAddress) {
        this.RKAddress = RKAddress;
    }

    public String getRKBldngNum() {
        return RKBldngNum;
    }

    public void setRKBldngNum(String RKBldngNum) {
        this.RKBldngNum = RKBldngNum;
    }

    public String getRKFloorNum() {
        return RKFloorNum;
    }

    public void setRKFloorNum(String RKFloorNum) {
        this.RKFloorNum = RKFloorNum;
    }

    public String getRKAptNum() {
        return RKAptNum;
    }

    public void setRKAptNum(String RKAptNum) {
        this.RKAptNum = RKAptNum;
    }

    public String getRKCity() {
        return RKCity;
    }

    public void setRKCity(String RKCity) {
        this.RKCity = RKCity;
    }

    public String getRKCountry() {
        return RKCountry;
    }

    public void setRKCountry(String RKCountry) {
        this.RKCountry = RKCountry;
    }

    public String getCreatedby() {
        return Createdby;
    }

    public void setCreatedby(String createdby) {
        Createdby = createdby;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }
}
