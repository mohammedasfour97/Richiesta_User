package com.rkesta.richiesta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.rkesta.richiesta.adapters.spinner_idname_adapter;
import com.rkesta.richiesta.api.WebService;

import java.util.ArrayList;
import java.util.HashMap;

import static com.rkesta.richiesta.app.constant.KEY_CityID;
import static com.rkesta.richiesta.app.constant.KEY_CitynameAR;
import static com.rkesta.richiesta.app.constant.KEY_CitynameEN;
import static com.rkesta.richiesta.app.constant.KEY_CountryID;
import static com.rkesta.richiesta.app.constant.KEY_currencyAR;
import static com.rkesta.richiesta.app.constant.KEY_currencyEN;
import static com.rkesta.richiesta.app.constant.currencyAR;
import static com.rkesta.richiesta.app.constant.currencyEN;
import static com.rkesta.richiesta.app.sharedObjects.ID_City;
import static com.rkesta.richiesta.app.sharedObjects.ID_Country;
import static com.rkesta.richiesta.app.sharedObjects.name_CityAR;
import static com.rkesta.richiesta.app.sharedObjects.name_CityEN;
import static com.rkesta.richiesta.util.utility.EN_OR_AR;
import static com.rkesta.richiesta.util.utility.set_pref;

public class ChoiceCountry extends AppCompatActivity {
    AppCompatSpinner spin_PickCountry , spin_PickCity;
    spinner_idname_adapter Adapter_PickCountry,Adapter_PickCity;
    Button BTN_Save;

    //    String[] country = { "India", "USA", "China", "Japan", "Other"};
//    String[] ciy = { "India", "USA", "China", "Japan", "Other"};
    ArrayList<HashMap<String,String>> Country_List = new ArrayList<>() ;
    ArrayList<HashMap<String,String>> City_List = new ArrayList<>() ;

    private void FillSpinner(int position) {
        HashMap<String,String> map = new HashMap<>();
        City_List.clear();
        if(position == -1){
            for (HashMap<String,String> country : MainCountry) {
                map = new HashMap<>();
                map = country;
                map.put("id",country.get("ID"));
                map.put("name",EN_OR_AR(ChoiceCountry.this,country.get("ENCountry"),country.get("ARCountry")));
                Country_List.add(map);
            }
            position = 0;
        }
        for (HashMap<String,String> City : MainCity) {
            if(MainCountry.get(position).get("ID").equals(City.get("CountryId"))){
                map = new HashMap<>();
                map = City;
                map.put("id",City.get("ID"));
                map.put("name",EN_OR_AR(ChoiceCountry.this,City.get("CityEN"),City.get("CityAR")));
                City_List.add(map);
            }
        }

        selected_currencyEN = Country_List.get(position).get("CurrencyCode");
//        if(Country_List.get(position).get("CurrencyCode").equals("EGP")){
            selected_currencyAR = Country_List.get(position).get("CurrencySymbol");
//        }else{
//            selected_currencyAR = "ر.س";
//        }

        selected_ID_Country = Country_List.get(position).get("ID");

        selected_ID_City    = City_List.get(0).get("ID") ;
        selected_name_CityEN = City_List.get(0).get("CityEN");
        selected_name_CityAR = City_List.get(0).get("CityAR");

        Adapter_PickCity.notifyDataSetChanged();
        Adapter_PickCountry.notifyDataSetChanged();

    }
    String selected_ID_City = "" ;
    String selected_name_CityEN = "" ;
    String selected_name_CityAR = "" ;
    String selected_ID_Country = "" ;
    String selected_currencyEN = "" ;
    String selected_currencyAR = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_country);

        spin_PickCountry = (AppCompatSpinner) findViewById(R.id.choice_country_spin_PickCountry);
        spin_PickCity = (AppCompatSpinner) findViewById(R.id.choice_country_spin_PickCity);
        BTN_Save = (Button) findViewById(R.id.choice_country_BTN_Save);

        //Creating the ArrayAdapter instance having the country list
        Adapter_PickCountry = new spinner_idname_adapter(this, Country_List);
        spin_PickCountry.setAdapter(Adapter_PickCountry);

        /*spin_PickCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                Country_List.get(position).get("id") // id // name
//                ID
//                Code
//                CountryCodeAbbr3
//                ARCountry
//                ENCountry
//                CurrencyCode
//                DialCode
//                Toast.makeText(getApplicationContext(), Country_List.get(position).get("CurrencyCode") , Toast.LENGTH_LONG).show();

                FillSpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/
        //Creating the ArrayAdapter instance having the country list
        Adapter_PickCity = new spinner_idname_adapter(this, City_List);
        spin_PickCity.setAdapter(Adapter_PickCity);

        spin_PickCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                City_List.get(position).get("id") // id // name
//                ID
//                CountryId
//                Code
//                CityAR
//                CityEN
//                Longitude
//                Latitude
//                Toast.makeText(getApplicationContext(), ID_City , Toast.LENGTH_LONG).show();
                selected_ID_City = City_List.get(position).get("ID") ;
                selected_name_CityEN = City_List.get(position).get("CityEN");
                selected_name_CityAR = City_List.get(position).get("CityAR");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        BTN_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currencyEN = selected_currencyEN;
                currencyAR = selected_currencyAR;
                set_pref(ChoiceCountry.this,KEY_currencyEN,currencyEN);
                set_pref(ChoiceCountry.this,KEY_currencyAR,currencyAR);

                ID_Country = selected_ID_Country ;
                set_pref(ChoiceCountry.this,KEY_CountryID,ID_Country);

                ID_City = selected_ID_City ;
                name_CityEN = selected_name_CityEN;
                name_CityAR = selected_name_CityAR;
                set_pref(ChoiceCountry.this,KEY_CityID,ID_City);
                set_pref(ChoiceCountry.this,KEY_CitynameEN,name_CityEN);
                set_pref(ChoiceCountry.this,KEY_CitynameAR,name_CityAR);

                onBackPressed();

            }
        });



        new Async_getCountries().execute();

    }

    private ArrayList<HashMap<String, String>> MainCountry;
    private ArrayList<HashMap<String, String>> MainCity;
    private ProgressDialog pDialog;

    class Async_getCountries extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChoiceCountry.this);
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        String result = "";
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            MainCountry = WS.SelectCountryV2();

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

            if(MainCountry.size() == 0){
//                repeate call
//                Toast.makeText(SplashScreen.this, "لا يوجد بيانات تواصل مع الاداره", Toast.LENGTH_SHORT).show();
//                Reload_Data();
                //something went wrong stay

            }else{
//                StoreCategories_exec(StoreCategories);
                new Async_getCities().execute();
            }


        }
    }

    class Async_getCities extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChoiceCountry.this);
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        String result = "";
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            MainCity = WS.SelectCityV2();

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

            if(MainCity.size() == 0){
//                repeate call
//                Toast.makeText(SplashScreen.this, "لا يوجد بيانات تواصل مع الاداره", Toast.LENGTH_SHORT).show();
//                Reload_Data();
                //something went wrong stay

            }else{
//                StoreCategories_exec(StoreCategories);
                FillSpinner(-1);
            }


        }
    }


}