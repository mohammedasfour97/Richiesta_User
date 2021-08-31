package com.rkesta.richiesta.ui.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rkesta.richiesta.R;
import com.rkesta.richiesta.adapters.home.StorCateProdBaseAdapter;
import com.rkesta.richiesta.adapters.settings.MyOrdersBaseAdapter;
import com.rkesta.richiesta.api.WebService;
import com.rkesta.richiesta.model.settings.M_ExpandableOrders;
import com.rkesta.richiesta.model.settings.M_OrdersDetails;

import java.util.ArrayList;
import java.util.HashMap;

import static com.rkesta.richiesta.app.sharedObjects.user_ID;
import static com.rkesta.richiesta.util.utility.EN_OR_AR;
import static com.rkesta.richiesta.util.utility.StrToBool;
import static com.rkesta.richiesta.util.utility.roundup;

public class MyOrders extends AppCompatActivity {

    RecyclerView RV_Orders ;
    MyOrdersBaseAdapter MyOrders_Adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        initbar();
//        initRecyclerView();
        initRecyclerView();
        new Async_SalesOrderByUserID().execute();






    }

    private void initRecyclerView() {
        RV_Orders  = findViewById(R.id.My_Orders_RV_Orders);
        MyOrders_Adapter = new MyOrdersBaseAdapter(this,ExpandableOrders_list);
        RV_Orders.setLayoutManager(new LinearLayoutManager(this));
        RV_Orders.setAdapter(MyOrders_Adapter);

    }

    ArrayList<M_ExpandableOrders> ExpandableOrders_list = new ArrayList<M_ExpandableOrders>() ;

    private void PrepareOrders(ArrayList<HashMap<String, String>> result) {
        ExpandableOrders_list.clear();

        for(int i = 0 ; i < result.size() ; i++) {
            if (ExpandableOrders_list.isEmpty()) {// empty ADD MASTER
                ExpandableOrders_list.add(
                        new M_ExpandableOrders(
                                result.get(i).get("SONumber"),
                                result.get(i).get("ShippingRate"),
                                result.get(i).get("TotalPrice"),
                                result.get(i).get("OrderDate"),
                                result.get(i).get("DiscountAmount"),
                                StrToBool(result.get(i).get("IsComplete")),
                                new M_OrdersDetails(
                                         result.get(i).get("ID")
                                        , result.get(i).get("SONumber")
                                        , result.get(i).get("StoreId")
                                        , result.get(i).get("RKPrdNameAR")
                                        , result.get(i).get("RKPrdNameEN")
                                        , result.get(i).get("ProductPic")
                                        , result.get(i).get("RK_Branch")
                                        , result.get(i).get("RK_Products")
                                        , result.get(i).get("ProductColor")
                                        , result.get(i).get("ProductAdditionals")
                                        , result.get(i).get("ProductSize")
                                        , result.get(i).get("ColorUnitPrice")
                                        , result.get(i).get("AdditionalUnitPrice")
                                        , result.get(i).get("SizeUnitPrice")
                                        , result.get(i).get("SalesUnitPrice")
                                        , result.get(i).get("Qty")
                                        , result.get(i).get("TotalPrice")
                                        , result.get(i).get("OrderNotes")
                                        , result.get(i).get("IsComplete")
                                        , result.get(i).get("OrderDate")
                                        , result.get(i).get("RK_User")
                                        , result.get(i).get("RK_User_DetailID")
                                        , result.get(i).get("Notes")
                                ),
                                !StrToBool(result.get(i).get("IsComplete"))
                        )
                );
            }else{

                boolean SONumber_IS_Repeated = false;
                int Repeated_position = 0;
                for (int n = 0; n < ExpandableOrders_list.size(); n++) {
                    if(ExpandableOrders_list.get(n).getSONumber().equals(result.get(i).get("SONumber"))){
                        SONumber_IS_Repeated = true ;
                        Repeated_position = n ;
                    }
                }


                if(SONumber_IS_Repeated){// repeated ADD Details
                    ExpandableOrders_list.get(Repeated_position).AddMore_OrdersDetails(new M_OrdersDetails(
                            result.get(i).get("ID")
                            , result.get(i).get("SONumber")
                            , result.get(i).get("StoreId")
                            , result.get(i).get("RKPrdNameAR")
                            , result.get(i).get("RKPrdNameEN")
                            , result.get(i).get("ProductPic")
                            , result.get(i).get("RK_Branch")
                            , result.get(i).get("RK_Products")
                            , result.get(i).get("ProductColor")
                            , result.get(i).get("ProductAdditionals")
                            , result.get(i).get("ProductSize")
                            , result.get(i).get("ColorUnitPrice")
                            , result.get(i).get("AdditionalUnitPrice")
                            , result.get(i).get("SizeUnitPrice")
                            , result.get(i).get("SalesUnitPrice")
                            , result.get(i).get("Qty")
                            , result.get(i).get("TotalPrice")
                            , result.get(i).get("OrderNotes")
                            , result.get(i).get("IsComplete")
                            , result.get(i).get("OrderDate")
                            , result.get(i).get("RK_User")
                            , result.get(i).get("RK_User_DetailID")
                            , result.get(i).get("Notes")));
                    Double current_total = 0.0;
                    Double newPrice = 0.0;
                    try {
                        current_total = roundup(Double.valueOf(ExpandableOrders_list.get(Repeated_position).getAllTotalPrice()));
                    }catch (Exception e){
                        current_total = 0.0;
                    }
                    try {
                        newPrice = roundup(Double.valueOf(result.get(i).get("TotalPrice")));
                    }catch (Exception e){
                        newPrice = 0.0;
                    }
                        String AllTotalPrice = roundup(current_total+newPrice)+"";
                    ExpandableOrders_list.get(Repeated_position).setAllTotalPrice(AllTotalPrice);
                }else{ // not repeated ADD MASTER
//                    ~/prdPic/20200513033950Store22.png
                    ExpandableOrders_list.add(
                            new M_ExpandableOrders(
                                    result.get(i).get("SONumber"),
                                    result.get(i).get("ShippingRate"),
                                    result.get(i).get("TotalPrice"),
                                    result.get(i).get("OrderDate"),
                                    result.get(i).get("DiscountAmount"),
                                    StrToBool(result.get(i).get("IsComplete")),
                                    new M_OrdersDetails(
                                            result.get(i).get("ID")
                                            , result.get(i).get("SONumber")
                                            , result.get(i).get("StoreId")
                                            , result.get(i).get("RKPrdNameAR")
                                            , result.get(i).get("RKPrdNameEN")
                                            , result.get(i).get("ProductPic")
                                            , result.get(i).get("RK_Branch")
                                            , result.get(i).get("RK_Products")
                                            , result.get(i).get("ProductColor")
                                            , result.get(i).get("ProductAdditionals")
                                            , result.get(i).get("ProductSize")
                                            , result.get(i).get("ColorUnitPrice")
                                            , result.get(i).get("AdditionalUnitPrice")
                                            , result.get(i).get("SizeUnitPrice")
                                            , result.get(i).get("SalesUnitPrice")
                                            , result.get(i).get("Qty")
                                            , result.get(i).get("TotalPrice")
                                            , result.get(i).get("OrderNotes")
                                            , result.get(i).get("IsComplete")
                                            , result.get(i).get("OrderDate")
                                            , result.get(i).get("RK_User")
                                            , result.get(i).get("RK_User_DetailID")
                                            , result.get(i).get("Notes")
                                    ),
                                    !StrToBool(result.get(i).get("IsComplete"))
                            )
                    );
                }




            }




        }

        MyOrders_Adapter.notifyDataSetChanged();


    }

    class Async_SalesOrderByUserID extends AsyncTask<String, String, String> {

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
            result = WS.SelectRK_SalesOrderByUserID(user_ID);

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            if(!result.isEmpty()){
                if (result.get(0).get("ID").isEmpty() || result.get(0).get("ID").equals("0")){
                    //ERROR
                    Toast.makeText(MyOrders.this, EN_OR_AR(MyOrders.this, "Something went wrong, please try again later", "هناك شئ خاطئ، يرجى المحاولة فى وقت لاحق"), Toast.LENGTH_SHORT).show();

                }else{//1 Done
                    PrepareOrders(result);
                }

            }
        }
    }


    private void initbar() {

        ((androidx.appcompat.widget.AppCompatImageView) findViewById(R.id.My_Orders_IV_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ((androidx.appcompat.widget.AppCompatImageView) findViewById(R.id.My_Orders_IV_Refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Async_SalesOrderByUserID().execute();
            }
        });

    }
}