package com.rkesta.richiesta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.rkesta.richiesta.Accounting.Login;
import com.rkesta.richiesta.Helper.LocalHelper;
import com.rkesta.richiesta.api.WebService;
import com.rkesta.richiesta.ui.settings.MyAddress;

import java.util.ArrayList;
import java.util.HashMap;

import static com.rkesta.richiesta.Helper.LocalHelper.SELECTED_LANGUAGE;
import static com.rkesta.richiesta.app.constant.KEY_CityID;
import static com.rkesta.richiesta.app.constant.KEY_CitynameAR;
import static com.rkesta.richiesta.app.constant.KEY_CitynameEN;
import static com.rkesta.richiesta.app.constant.KEY_CountryID;
import static com.rkesta.richiesta.app.constant.KEY_currencyAR;
import static com.rkesta.richiesta.app.constant.KEY_currencyEN;
import static com.rkesta.richiesta.app.constant.KEY_userAr;
import static com.rkesta.richiesta.app.constant.KEY_userEN;
import static com.rkesta.richiesta.app.constant.KEY_userEmail;
import static com.rkesta.richiesta.app.constant.KEY_userFirstName;
import static com.rkesta.richiesta.app.constant.KEY_userFirstNameAR;
import static com.rkesta.richiesta.app.constant.KEY_userLastName;
import static com.rkesta.richiesta.app.constant.KEY_userLastNameAR;
import static com.rkesta.richiesta.app.constant.KEY_userPhone;
import static com.rkesta.richiesta.app.constant.KEY_userProPic;
import static com.rkesta.richiesta.app.constant.KEY_userProPicName;
import static com.rkesta.richiesta.app.constant.KEY_user_ID;
import static com.rkesta.richiesta.app.constant.currencyAR;
import static com.rkesta.richiesta.app.constant.currencyEN;
import static com.rkesta.richiesta.app.sharedObjects.*;
import static com.rkesta.richiesta.util.utility.get_pref;
import static com.rkesta.richiesta.util.utility.set_pref;


public class SplashScreen extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if(get_pref(this,SELECTED_LANGUAGE).equals("") || get_pref(this,SELECTED_LANGUAGE).equals("en")){
            set_pref(this,SELECTED_LANGUAGE,"en");
        }
        LocalHelper.setLocale(this , get_pref(this,SELECTED_LANGUAGE));

//        Reload_Data();

    }


    private void Reload_Data() {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                new Async_getStoreCategories().execute();


            }
        }, SPLASH_TIME_OUT);
    }


    private void StoreCategories_exec(ArrayList<HashMap<String, String>> storeCategories) {


        Log.d("opopo", get_pref(this,KEY_CityID));
        if(get_pref(this,KEY_CityID).equals("")){

            Intent i = new Intent(SplashScreen.this, ChoiceCountry.class);
            startActivity(i);
//0,0,city,coutry
        }else{
            currencyEN  = get_pref(this,KEY_currencyEN);
            currencyAR  = get_pref(this,KEY_currencyAR);
            ID_Country  = get_pref(this,KEY_CountryID);
            ID_City     = get_pref(this,KEY_CityID);
            name_CityEN = get_pref(this,KEY_CitynameEN);
            name_CityAR = get_pref(this,KEY_CitynameAR);

            if(get_pref(this,KEY_user_ID).equals("")){
                Intent i = new Intent(SplashScreen.this, Login.class);
                startActivity(i);
            }else{

                user_ID  = get_pref(this,KEY_user_ID);
                userAr  = get_pref(this,KEY_userAr);
                userEN  = get_pref(this,KEY_userEN);
                userEmail  = get_pref(this,KEY_userEmail);
                userPhone  = get_pref(this,KEY_userPhone);
                userProPic  = get_pref(this,KEY_userProPic);
                userProPicName  = get_pref(this,KEY_userProPicName);
                userFirstName  = get_pref(this,KEY_userFirstName);
                userLastName  = get_pref(this,KEY_userLastName);
                userFirstNameAR  = get_pref(this,KEY_userFirstNameAR);
                userLastNameAR  = get_pref(this,KEY_userLastNameAR);
                IS_Guest = false;

                Intent i = new Intent(SplashScreen.this, MyAddress.class);
                i.putExtra("order", "no");
                startActivity(i);
            }

            // close this activity
            finish();
        }

    }




    private ProgressDialog pDialog;
    class Async_getStoreCategories extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SplashScreen.this);
            pDialog.setMessage(getResources().getString(R.string.loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        ArrayList<HashMap<String, String>> StoreCategories = new ArrayList<HashMap<String, String>>();
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            StoreCategories = WS.SelectRK_StoreCategories();
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            if(StoreCategories.size() == 0){
//                repeate call
//                Toast.makeText(SplashScreen.this, "لا يوجد بيانات تواصل مع الاداره", Toast.LENGTH_SHORT).show();

                Reload_Data();
                //something went wrong stay
            }else{
                StoreCategories_exec(StoreCategories);
//                prepareoder(PendingOrders);
            }
            pDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Reload_Data();
    }
}