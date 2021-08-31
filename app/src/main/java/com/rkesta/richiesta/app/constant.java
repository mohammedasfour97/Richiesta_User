package com.rkesta.richiesta.app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.rkesta.richiesta.util.utility.EN_OR_AR;

public class constant {

    public static final String ImageURl = "http://cp.rkesta.info/prdPic/";

    public static final String KEY_CountryID = "CountryID" ;

    public static final String KEY_CityID = "CityID" ;
    public static final String KEY_CitynameEN = "CitynameEN" ;
    public static final String KEY_CitynameAR = "CitynameAR" ;

    public static final String KEY_currencyEN = "currencyEN" ;
    public static final String KEY_currencyAR = "currencyAR" ;

    public static final String KEY_user_ID = "user_ID";
    public static final String KEY_userAr = "userAr";
    public static final String KEY_userEN = "userEN";
    public static final String KEY_userEmail = "userEmail";
    public static final String KEY_userPhone = "userPhone";
    public static final String KEY_userProPic = "userProPic";
    public static final String KEY_userProPicName = "userProPicName";
    public static final String KEY_userFirstName = "userFirstName";
    public static final String KEY_userLastName = "userLastName";
    public static final String KEY_userFirstNameAR = "userFirstNameAR";
    public static final String KEY_userLastNameAR = "userLastNameAR";

    public static String getcurrency(Context ctx) {
        return EN_OR_AR(ctx,currencyEN,currencyAR);
    }

    public static String currencyEN = "SAR";
    public static String currencyAR = "ر.س";

    /* Locale.US        -> 2,365.12
     * Locale.GERMANY   -> 2.365,12
     */
    public static final Locale PRICE_LOCAL_FORMAT = Locale.US;

    /* true     -> 2.365,12
     * false    -> 2.365
     */
    public static final boolean PRICE_WITH_DECIMAL = true;

    /* true     -> 2.365,12 USD
     * false    -> USD 2.365
     */
    public static final boolean PRICE_CURRENCY_IN_END = true;

    public static ArrayList<HashMap<String,String>> pendingOrderDet = new ArrayList<>();

    static String GoogleMapKey = "AIzaSyDzO289NRtxK_qxRJ1eaJJanhGqQtyU0AU" ;

    public static boolean checkLocationPermission(Activity activity) {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED)
            return true;

        else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION))
            return false;

        else return false;


    }


}
