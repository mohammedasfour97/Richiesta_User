package com.rkesta.richiesta.ui.activitymain;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rkesta.richiesta.R;
import com.rkesta.richiesta.adapters.settings.MyOrdersDetailsAdapter;
import com.rkesta.richiesta.api.WebService;
import com.rkesta.richiesta.app.constant;
import com.rkesta.richiesta.databinding.ActivityTrackOrderBinding;
import com.rkesta.richiesta.model.settings.M_OrdersDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

public class TrackOrderActivity extends AppCompatActivity {

    private ActivityTrackOrderBinding activityTrackOrderBinding;
    private String deliveryPhone;
    private ProgressDialog pDialog;

    //// vars for Map
    private LatLng delLatLng, storeLatLng, userLatLng;
    private SupportMapFragment mapFragment;
    private OnMapReadyCallback callback;

    ///vars for recyclerview
    private List<M_OrdersDetails> m_ordersDetailsList;
    private MyOrdersDetailsAdapter myOrderDetailsAdapter;

    // vars for the repeated task
    private int mInterval = 15000;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTrackOrderBinding = DataBindingUtil.setContentView(this, R.layout.activity_track_order);

        setListeners();
        initRecyclerView();

    }


    private void setListeners(){

        activityTrackOrderBinding.activityTrackOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callDelivery();
            }
        });

        activityTrackOrderBinding.activityTrackOrderIVClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activityTrackOrderBinding.activityTrackOrderIVRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDeliveryLocation();
            }
        });
    }


    private void initRecyclerView(){

        m_ordersDetailsList = new ArrayList<>();
        myOrderDetailsAdapter = new MyOrdersDetailsAdapter(m_ordersDetailsList , this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        activityTrackOrderBinding.recyclerview.setLayoutManager(mLayoutManager);
        activityTrackOrderBinding.recyclerview.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        activityTrackOrderBinding.recyclerview.setAdapter(myOrderDetailsAdapter);

    }


    private void callDelivery(){

        if (deliveryPhone!=null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ActivityCompat.checkSelfPermission(TrackOrderActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


                    TrackOrderActivity.this.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);

                    return;
                }
            }

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + deliveryPhone));
            startActivity(callIntent);
        }
    }

    private void getDeliveryLocation() {

        new Async_getDeliveryLocation(getIntent().getStringExtra("del_id")).execute();
    }


    private void setGoogleMap() {

                 /*if (currentLocation != null) {

                    latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(latLng).title("")
                            .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_car_red)));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                }
                 */

        callback = new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                googleMap.addMarker(new MarkerOptions().position(delLatLng).title(getResources().getString(R.string.del_loc))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_del_loc)));
                googleMap.addMarker(new MarkerOptions().position(userLatLng).title(getResources().getString(R.string.user_loc)).title("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user_loc)));
                googleMap.addMarker(new MarkerOptions().position(storeLatLng).title(getResources().getString(R.string.store_loc)).title("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_store_loc)));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(delLatLng, 12));


            }
        };

        if (mapFragment == null)
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(callback);
    }


    class Async_getDeliveryLocation extends AsyncTask<String, String, String> {

        private String del_id;

        public Async_getDeliveryLocation(String del_id) {
            this.del_id = del_id;
        }

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(TrackOrderActivity.this);
            pDialog.setMessage(getResources().getString(R.string.loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        ArrayList<HashMap<String, String>> pending_orders_location = new ArrayList<>();

        protected String doInBackground(String... args) {
            WebService webService = new WebService();
            pending_orders_location = webService.getDeliveryLocation(del_id);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/


        protected void onPostExecute(String file_url) {

            pDialog.hide();

            if (pending_orders_location.size() == 0) {
                // repeate call
                // Toast.makeText(SplashScreen.this, "لا يوجد بيانات تواصل مع الاداره", Toast.LENGTH_SHORT).show();
                // something went wrong stay
            } else {

                deliveryPhone = pending_orders_location.get(0).get("RK_DeliveryContact");
                delLatLng = new LatLng(Double.parseDouble(pending_orders_location.get(0).get("Latitude")),
                        Double.parseDouble(pending_orders_location.get(0).get("Longitude")));
                userLatLng = new LatLng(Double.parseDouble(constant.pendingOrderDet.get(0).get("UserLat")),
                        Double.parseDouble(constant.pendingOrderDet.get(0).get("UserLong")));
                storeLatLng = new LatLng(Double.parseDouble(constant.pendingOrderDet.get(0).get("RKBranchDetails_Latitude")),
                        Double.parseDouble(constant.pendingOrderDet.get(0).get("RKBranchDetails_Longitude")));

                m_ordersDetailsList.clear();

                M_OrdersDetails m_ordersDetails;

                for (HashMap<String,String> map : constant.pendingOrderDet){

                    m_ordersDetails = new M_OrdersDetails();
                    m_ordersDetails.setRKPrdNameAR(map.get("RKPrdNameAR"));
                    m_ordersDetails.setRKPrdNameEN(map.get("RKPrdNameEN"));
                    m_ordersDetails.setProductPic(map.get("ProductPic"));
                    m_ordersDetails.setTotalPrice(map.get("TotalPrice"));

                    m_ordersDetailsList.add(m_ordersDetails);
                }

                myOrderDetailsAdapter.notifyDataSetChanged();

                setGoogleMap();

            }

        }

    }


    private Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {

                getDeliveryLocation();

            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

        mHandler = new Handler();
        mStatusChecker.run();
    }


    @Override
    protected void onPause() {
        super.onPause();

        mHandler.removeCallbacks(mStatusChecker);
    }
}

