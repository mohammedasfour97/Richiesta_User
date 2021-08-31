package com.rkesta.richiesta.app;

import com.rkesta.richiesta.MainActivity;
import com.rkesta.richiesta.R;
import com.rkesta.richiesta.model.home.CSA.M_CSA_Additional;
import com.rkesta.richiesta.model.home.CSA.M_CSA_Color;
import com.rkesta.richiesta.model.home.CSA.M_CSA_Size;

import java.util.ArrayList;

public class sharedObjects {

    public static ArrayList<M_CSA_Size> addonSize_List = new ArrayList<>();
    public static ArrayList<M_CSA_Additional> addonAdditional_List = new ArrayList<>();
    public static ArrayList<M_CSA_Color> addonColor_List = new ArrayList<>();


    public static String SharedSelectedStore_ID   = "";
    public static String SharedSelectedStore_name = "";
    public static String SharedSelectedStore_IMG  = "";


    public static String ID_Country = "";//62 + 178
    public static String ID_City = "";   //7  + 6
    public static String name_CityAR = "" ;
    public static String name_CityEN = "" ;

    public static  boolean is_locationaccepted = false;

    public static Double MaxDeliveryAmount = 1000.0 ;
    public static Boolean IS_Guest = false ;

    public static String user_ID = "" ;

    public static String userAr = "";
    public static String userEN = "";
    public static String userEmail = "";
    public static String userPhone = "";
    public static String userProPic = ""; //+AppConfig.WS.imageURL_UserProfile;

    public static String userProPicName = "" ;
    public static String userFirstName = "";
    public static String userLastName = "";
    public static String userFirstNameAR = "";
    public static String userLastNameAR = "" ;
    public static String clatitude = "0.0" ;
    public static String clongitude = "0.0" ;


    //region MainActivity switch between fragment with OnBackPress
    // onResume MainActivity to switch between fragment
    /**
     *
     // switch between activitys OnBackPressed -- HOW TO USE HASSAN BADAWI
     GO_TO_position = true;
     TO_position = anyint ;
     navController.navigate(R.id.navigation_home);
     TO_position = 1 ;
     navController.navigate(R.id.navigation_shoppingbasket);
     TO_position = 2 ;
     navController.navigate(R.id.navigation_settings);
     onBackPressed();
     * */
    public static boolean GO_TO_position = false;
    public static int TO_position = 0;//0,1,2
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(GO_TO_position){
//            switch (TO_position){
//                case 1:
//                    navController.navigate(R.id.navigation_shoppingbasket);
//                    break;
//                case 2:
//                    navController.navigate(R.id.navigation_settings);
//                    break;
//                default:
//                    navController.navigate(R.id.navigation_home);
//            }
//        }
//
//        GO_TO_position = false ;
//        TO_position = 0 ;
//    }

    //endregion

}
