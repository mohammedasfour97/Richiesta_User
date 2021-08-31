package com.rkesta.richiesta.custom_dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;

import com.rkesta.richiesta.MapsActivity;
import com.rkesta.richiesta.R;
import com.rkesta.richiesta.adapters.spinner_idname_adapter;
import com.rkesta.richiesta.api.WebService;
import com.rkesta.richiesta.ui.settings.ChangeArea;
import com.rkesta.richiesta.ui.settings.MyAddress;

import java.util.ArrayList;
import java.util.HashMap;

import static com.rkesta.richiesta.app.constant.KEY_CityID;
import static com.rkesta.richiesta.app.constant.KEY_CountryID;
import static com.rkesta.richiesta.app.sharedObjects.clatitude;
import static com.rkesta.richiesta.app.sharedObjects.clongitude;
import static com.rkesta.richiesta.app.sharedObjects.user_ID;
import static com.rkesta.richiesta.util.utility.EN_OR_AR;
import static com.rkesta.richiesta.util.utility.get_pref;

public class AddAddress_Dialog extends DialogFragment {
    Button submit,BTN_Setlocation;

    Dialog dialog;
    AppCompatSpinner spin_PickCountry , spin_PickCity;
    spinner_idname_adapter Adapter_PickCountry,Adapter_PickCity;
    ArrayList<HashMap<String,String>> Country_List = new ArrayList<>() ;
    ArrayList<HashMap<String,String>> City_List = new ArrayList<>() ;


    EditText ET_address;
    EditText ET_bldngnum;
    EditText ET_floornum;
    EditText ET_aptnum;
    EditText ET_note;
    MyAddress myAddressActivity;
    public AddAddress_Dialog(MyAddress myAddress) {
        this.myAddressActivity = myAddress;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    String selected_ID_City = "" ;
    String selected_ID_Country = "" ;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        // set the layout for the dialog
        dialog.setContentView(R.layout.dialog_add_address);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


        ET_address = (EditText) dialog.findViewById(R.id.dialog_add_address_ET_address);
        ET_bldngnum = (EditText) dialog.findViewById(R.id.dialog_add_address_ET_bldngnum);
        ET_floornum = (EditText) dialog.findViewById(R.id.dialog_add_address_ET_floornum);
        ET_aptnum = (EditText) dialog.findViewById(R.id.dialog_add_address_ET_aptnum);
        ET_note = (EditText) dialog.findViewById(R.id.dialog_add_address_ET_note);

        initSpinner(dialog);


        BTN_Setlocation = (Button) dialog.findViewById(R.id.dialog_add_address_BTN_Setlocation);
        submit = (Button) dialog.findViewById(R.id.dialog_add_address_BTN_submit);
        BTN_Setlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MapsActivity.class);
                startActivity(i);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ET_address.getText().toString().isEmpty() || ET_note.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), getResources().getString(R.string.Blank_field), Toast.LENGTH_SHORT).show();
                }else if(clatitude.equals("0.0")){
                    Toast.makeText(getActivity(), EN_OR_AR(getActivity(),"Please select the desired location","الرجاء تحديد الموقع المطلوب"), Toast.LENGTH_SHORT).show();
                }else{
                    new Async_InsertRK_User_Details(user_ID,ET_address.getText().toString(),ET_bldngnum.getText().toString(),ET_floornum.getText().toString(),ET_aptnum.getText().toString(),selected_ID_City,selected_ID_Country,clongitude,clatitude,"AndroidCreator",ET_note.getText().toString()).execute();
                }
            }
        });


        // Close
        dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // User cancelled the dialog
                dismiss();
            }

        });

        new Async_getCountries().execute();

        return dialog;
    }

    private ProgressDialog pDialog;
    class Async_InsertRK_User_Details extends AsyncTask<String, String, String> {
        String RKUserId = "";
        String RKAddress = "";
        String RKBldngNum = "";
        String RKFloorNum = "";
        String RKAptNum = "";
        String RKCity = "";
        String RKCountry = "";
        String longitude  = "";
        String latitude = "";
        String Createdby = "";
        String Notes = "";
        public Async_InsertRK_User_Details(String RKUserId , String RKAddress , String  RKBldngNum , String  RKFloorNum
                , String RKAptNum , String  RKCity , String RKCountry , String longitude, String latitude , String Createdby , String Notes ) {

            this.RKUserId = RKUserId ;
            this.RKAddress = RKAddress ;
            this.RKBldngNum = RKBldngNum ;
            this.RKFloorNum = RKFloorNum ;
            this.RKAptNum = RKAptNum ;
            this. RKCity =  RKCity ;
            this.RKCountry = RKCountry ;
            this.longitude  = longitude  ;
            this.latitude = latitude ;
            this.Createdby = Createdby ;
            this.Notes = Notes ;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            result = WS.InsertRK_User_Details( RKUserId ,  RKAddress ,   RKBldngNum ,   RKFloorNum
                    ,  RKAptNum ,   RKCity ,  RKCountry ,  longitude,  latitude ,  Createdby ,  Notes  );

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            if (!result.isEmpty()) {
                if (result.get(0).get("ID").isEmpty() || result.get(0).get("ID").equals("0")) {
                    Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                }
//            if(StoreCategories.size() == 0){
////                repeate call
////                Toast.makeText(SplashScreen.this, "لا يوجد بيانات تواصل مع الاداره", Toast.LENGTH_SHORT).show();
//
//                Reload_Data();
//                //something went wrong stay
//            }else{
//                StoreCategories_exec(StoreCategories);
////                prepareoder(PendingOrders);
//            }
            }


            pDialog.dismiss();
            dismiss();
        }
    }


    /**
     * initSpinner
     * **/
    private void initSpinner(Dialog dialog) {

        spin_PickCountry = (AppCompatSpinner) dialog.findViewById(R.id.dialog_add_address_spin_PickCountry);
        spin_PickCity = (AppCompatSpinner) dialog.findViewById(R.id.dialog_add_address_spin_PickCity);

        //Creating the ArrayAdapter instance having the country list
        Adapter_PickCountry = new spinner_idname_adapter(getActivity(), Country_List);
        spin_PickCountry.setAdapter(Adapter_PickCountry);

        spin_PickCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        //Creating the ArrayAdapter instance having the country list
        Adapter_PickCity = new spinner_idname_adapter(getActivity(), City_List);
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void FillSpinner(int position) {
        HashMap<String,String> map = new HashMap<>();
        City_List.clear();

        for (HashMap<String,String> City : MainCity) {
            if(MainCountry.get(position).get("ID").equals(City.get("CountryId"))){
                map = new HashMap<>();
                map = City;
                map.put("id",City.get("ID"));
                map.put("name",EN_OR_AR(getActivity(),City.get("CityEN"),City.get("CityAR")));
                City_List.add(map);
            }
        }


        selected_ID_Country = Country_List.get(position).get("ID");
        selected_ID_City    = City_List.get(0).get("ID") ;

        Adapter_PickCity.notifyDataSetChanged();
        Adapter_PickCountry.notifyDataSetChanged();

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        clatitude = "0.0";
        clongitude = "0.0";
        myAddressActivity.Refresh();
    }


    private ArrayList<HashMap<String, String>> MainCountry;
    private ArrayList<HashMap<String, String>> MainCity;
    class Async_getCountries extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        String result = "";
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            MainCountry = WS.SelectCountryV2();
            MainCity = WS.SelectCityV2();

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            if(MainCountry.size() == 0){
//                repeate call
//                Toast.makeText(SplashScreen.this, "لا يوجد بيانات تواصل مع الاداره", Toast.LENGTH_SHORT).show();
//                Reload_Data();
                //something went wrong stay

            }else{

                HashMap<String,String> map = new HashMap<>();
                for (HashMap<String,String> country : MainCountry) {
                    map = new HashMap<>();
                    map = country;
                    map.put("id",country.get("ID"));
                    map.put("name",EN_OR_AR(getActivity(),country.get("ENCountry"),country.get("ARCountry")));
                    Country_List.add(map);
                }
//                StoreCategories_exec(StoreCategories);
                int Country_Postion = 0 ;
                for (int i = 0; i < Country_List.size(); i++) {
                    if(get_pref(getActivity(), KEY_CountryID).equals(Country_List.get(i).get("ID"))){
                        Country_Postion = i ;
                    }
                }
                spin_PickCountry.setSelection(Country_Postion);

                FillSpinner(Country_Postion);


                int City_Postion = 0 ;
                for (int i = 0; i < City_List.size(); i++) {
                    if(get_pref(getActivity(), KEY_CityID).equals(City_List.get(i).get("ID"))){
                        City_Postion = i ;
                    }
                }
                spin_PickCity.setSelection(City_Postion);

            }



            pDialog.dismiss();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        clatitude = "0.0";
        clongitude = "0.0";
        myAddressActivity.Refresh();

    }
}
