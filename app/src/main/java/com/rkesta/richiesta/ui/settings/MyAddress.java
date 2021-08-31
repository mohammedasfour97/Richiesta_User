package com.rkesta.richiesta.ui.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.rkesta.richiesta.MainActivity;
import com.rkesta.richiesta.R;
import com.rkesta.richiesta.adapters.settings.MyAddressAdapter;
import com.rkesta.richiesta.api.WebService;
import com.rkesta.richiesta.app.constant;
import com.rkesta.richiesta.custom_dialog.AddAddress_Dialog;
import com.rkesta.richiesta.model.settings.M_Address;

import java.util.ArrayList;
import java.util.HashMap;

import static com.rkesta.richiesta.app.sharedObjects.user_ID;
import static com.rkesta.richiesta.util.utility.EN_OR_AR;

public class MyAddress extends AppCompatActivity {

    RecyclerView RV_address ;
    MyAddressAdapter Myaddress_Adapter ;
    ArrayList<M_Address> address_list = new ArrayList<M_Address>() ;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    Intent intent;

    private void On_Delete(M_Address selectedAddress, int position) {
//        Toast.makeText(MyAddress.this, "HI Delete", Toast.LENGTH_SHORT).show();
        new Async_DeleteRK_User_Details(selectedAddress.getID()).execute();
    }
    private void On_Edit(M_Address selectedAddress, int position) {
        Toast.makeText(MyAddress.this, "HI Edit", Toast.LENGTH_SHORT).show();
    }
    private void Addnew() {

        AddAddress_Dialog customDialogFragment = new AddAddress_Dialog(this);
        customDialogFragment.show(getSupportFragmentManager(), "");
    }
    public void Refresh() {
        new Async_SelectRK_User_DetailsByUserID().execute();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        initbar();
        initRecyclerView();
        setListeners();

        if (getIntent().getStringExtra("order").equals("no"))
            findViewById(R.id.My_address_IV_close).setVisibility(View.GONE);

        new Async_SelectRK_User_DetailsByUserID().execute();
    }

    class Async_SelectRK_User_DetailsByUserID extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            result = WS.SelectRK_User_DetailsByUserID(user_ID);

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            if (!result.isEmpty()) {
                if (result.get(0).get("ID").isEmpty() || result.get(0).get("ID").equals("0")) {
//                //ERROR
//                Toast.makeText(MyAddress.this, EN_OR_AR(MyAddress.this, "Something went wrong, please try again later", "هناك شئ خاطئ، يرجى المحاولة فى وقت لاحق"), Toast.LENGTH_SHORT).show();

                } else {//1 Done

                    PrepareAddresses(result);
                }

            }

            if (getIntent().getStringExtra("order").equals("no"))
                findViewById(R.id.activity_my_address_skip_btn).setVisibility(View.VISIBLE);
        }
    }

    private ProgressDialog pDialog;
    class Async_DeleteRK_User_Details extends AsyncTask<String, String, String> {
        String MyAddressID;

        public Async_DeleteRK_User_Details( String MyAddressID) {
            this.MyAddressID = MyAddressID ;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MyAddress.this);
            pDialog.setMessage("برجاء الانتظار ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        String result = "";
        protected String doInBackground(String... args) {
            WebService WS = new WebService();
            result = WS.DeleteRK_User_Details(MyAddressID);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

            if(result.isEmpty() || result.equals("0") || result.equals("ERROR")){
                //ERROR
                Toast.makeText(MyAddress.this, EN_OR_AR(MyAddress.this, "Something went wrong, please try again later", "هناك شئ خاطئ، يرجى المحاولة فى وقت لاحق"), Toast.LENGTH_SHORT).show();

            }

            pDialog.dismiss();
            Refresh();
        }
    }



    private void PrepareAddresses(ArrayList<HashMap<String, String>> result) {
        address_list.clear();
        for(int i = 0 ; i < result.size() ; i++) {
            address_list.add(new M_Address(
                    result.get(i).get("ID")
                    ,result.get(i).get("RKUserId")
                    ,result.get(i).get("RKAddress")
                    ,result.get(i).get("RKBldngNum")
                    ,result.get(i).get("RKFloorNum")
                    ,result.get(i).get("RKAptNum")
                    ,result.get(i).get("RKCity")
                    ,result.get(i).get("RKCountry")
                    ,result.get(i).get("Createdby")
                    ,result.get(i).get("Notes")
                    ,result.get(i).get("Latitude")
                    ,result.get(i).get("Longitude")
            ));
        }
        Myaddress_Adapter.notifyDataSetChanged();

    }
    private void initRecyclerView() {
        RV_address  = findViewById(R.id.My_address_RV_address);
        Myaddress_Adapter = new MyAddressAdapter(this,this,address_list);
        RV_address.setLayoutManager(new LinearLayoutManager(this));
        RV_address.setAdapter(Myaddress_Adapter);

        Myaddress_Adapter.setOnItemDeleteListener(new MyAddressAdapter.OnItemDelete() {
            @Override
            public void onItemClick(View view, M_Address selectedAddress, int position) {
                On_Delete(selectedAddress,position);
            }
        });


        Myaddress_Adapter.setOnItemEditListener(new MyAddressAdapter.OnItemEdit() {
            @Override
            public void onItemClick(View view, M_Address selectedAddress, int position) {
                On_Edit(selectedAddress,position);
            }
        });

    }

    private void initbar() {

        ((androidx.appcompat.widget.AppCompatImageView) findViewById(R.id.My_address_IV_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ((androidx.appcompat.widget.AppCompatImageView) findViewById(R.id.My_address_IV_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Addnew();
            }
        });
        ((androidx.appcompat.widget.AppCompatImageView) findViewById(R.id.My_address_IV_Refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Refresh();
            }
        });
    }

    private void setListeners(){

        findViewById(R.id.activity_my_address_skip_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchCurrentLocation();
            }
        });
    }

    private void fetchCurrentLocation() {


        intent = new Intent(MyAddress.this, MainActivity.class);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MyAddress.this);

        if (!checkPermission()) {return;}
        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,
                new CancellationToken() {
                    @Override
                    public boolean isCancellationRequested() {
                        return false;
                    }

                    @Override
                    public CancellationToken onCanceledRequested(OnTokenCanceledListener onTokenCanceledListener) {
                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;

                    intent.putExtra("lat", String.valueOf(currentLocation.getLatitude()));
                    intent.putExtra("long", String.valueOf(currentLocation.getLongitude()));

                    startActivity(intent);

                }
            }
        });
    }

    private boolean checkPermission() {

        if (!constant.checkLocationPermission(MyAddress.this)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
            return false;
        } else return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    fetchCurrentLocation();
                }
                else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){

                    intent.putExtra("lat", "0");
                    intent.putExtra("long", "0");
                    startActivity(intent);
                    finish();
                    break;
                }


        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Refresh();
    }
}